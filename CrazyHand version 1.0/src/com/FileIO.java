package com;

import isotool.filesystem.ISO;
import isotool.filesystem.ISOFile;
import isotool.filesystem.ISOFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.MeleeEdit.SpecialAttributeTable;
import com.SpecialAttributeIndex.SpecialMoveAttribute;
import com.scripts.BodyStateScript;
import com.scripts.ComboSFXGFXScript;
import com.scripts.GraphicScript;
import com.scripts.HitboxScript;
import com.scripts.LoopScript;
import com.scripts.Script;
import com.scripts.SelfDamageScript;
import com.scripts.SmashChargeScript;
import com.scripts.SoundScript;
import com.scripts.SynchronousScript;
import com.scripts.ThrowScript;


;

public class FileIO {
	
	public static ByteBuffer f;

	public static ISOFileSystem isoFileSystem;

	public static ISO loadedISOFile;
	
	public static void init(int character) {
		byte[] isoFileData;
		isoFileData = loadedISOFile.getFileSystem()
				.getFileData(
						"Pl" + Character.characters[character].id
								+ ".dat");

		f = ByteBuffer.wrap(isoFileData);
		
		System.out.println("Pl" + Character.characters[character].id
								+ ".dat");

		f.position(Character.characters[character].offset);
		System.out.println("File is 0x" +Integer.toHexString(f.capacity())+" bytes in length.");
	}
	
	public static void init() {
		init(MeleeEdit.selected);
	}
	
	public static void initPlCo(){
		byte[] isoFileData;
		isoFileData = loadedISOFile.getFileSystem()
				.getFileData(
						"PlCo.dat");

		f = ByteBuffer.wrap(isoFileData);

		f.position(0);
	}
	
	public static void initDOL() {
		byte[] isoFileData;
		isoFileData = loadedISOFile.getFileSystem()
				.getFileData(
						"Start.dol");

		f = ByteBuffer.wrap(isoFileData);

		f.position(Character.characters[MeleeEdit.selected].offset);

	}

	/*
	 * public static void init(int k) {// initializes character of index "k"
	 * byte[] isoFileData; try { isoFileData =
	 * loadedISOFile.getFileSystem().openFile( "Pl" + Character.characters[k].id
	 * + ".dat");
	 * 
	 * f = ByteBuffer.wrap(isoFileData); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * f.position(Character.characters[MeleeEdit.selected].offset);
	 * 
	 * }
	 */

	public static void loadISOFile() {
		try {
			FileNameExtensionFilter isoFilter = new FileNameExtensionFilter(
					"ISO Files", "iso");
			final JFileChooser fc = new JFileChooser();

			fc.setCurrentDirectory(Options.hasLastIso ? new File(
					Options.isoPath) : new File(System.getProperty("user.dir")));
			fc.addChoosableFileFilter(isoFilter);
			fc.setFileFilter(isoFilter);

			int returnVal = fc.showOpenDialog(MeleeEdit.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				loadedISOFile = new ISO(selectedFile);
				isoFileSystem = loadedISOFile.getFileSystem();
				if (MeleeEdit.frame != null)
					MeleeEdit.updateTitle(fc.getSelectedFile().getName());

				Options.isoPath = fc.getCurrentDirectory().getPath();
				Options.hasLastIso=true;
				Options.saveOptions();
			} else {
				if (loadedISOFile == null)
					throw new RuntimeException("You must select a ISO file!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public static float readFloat() {
		float val = 0.9f;
		val = f.getFloat();
		return val;
	}

	public static int readByte() {
		int val = 0;
		val = f.get();
		return ((short) (val & 0xff));

	}

	public static void writeByte(int b) {
		f.put((byte) (b & 0xff));
	}

	public static void writeInt(int b) {
		f.putInt(b);
	}
	
	public static void writeFloat(float b) {
		f.putFloat(b);
	}

	public static int readInt() {
		int val = 0;
		val = f.getInt();
		return val;
	}

	public static void setPosition(int pos) {
		f.position(pos);
	}
	
	
	//Same code that was in ReadScripts to get the offset for a subaction.
	//Moved it to its own method to make ReadScripts a little cleaner.
	public static int convertSubactionMenuSelection(int charId, int subaction, boolean all){
		
		int result;
		
		if (subaction == -1) {
			subaction = 0;
		}
		
		boolean specialFlag = subaction > SubAction.subActions.length-1;//Used for reading special moves once finished reading normal attacks
		if (!all&&(charId<30)) {
			if (subaction >= SubAction.subActions.length+SpecialMovesList.getListForCharacter(charId).length) {
				subaction = SubAction.subActions.length+SpecialMovesList.getListForCharacter(charId).length - 1;
			}
			result =specialFlag ? SpecialMovesList.getListForCharacter(charId)[subaction-SubAction.subActions.length].offset * 6 * 4
								: SubAction.subActions[subaction].offset * 6 * 4;
		}
		else {
			result = subaction * 6 * 4;
			/*
			if (subaction >= SubAction.subActions.length) {
				subaction = SubAction.subActions.length - 1;
				result = SubAction.subActions[subaction].offset * 6 * 4;// defaults
			}*/
		}
		return result;
	}
	
	public static void readScripts(){
		readScripts(Script.scripts);
	}
	
	public static void readScripts(ArrayList<Script> scripts){
		if (MeleeEdit.selectedSubaction == -1) {
			MeleeEdit.selectedSubaction = 0;
		}
		readScripts(scripts,MeleeEdit.selected,MeleeEdit.selectedSubaction);
	}

	public static void readScripts(ArrayList<Script> scripts, int charId, int subactionIndex) {
		Script.number = 1;
		
		ScriptEditWindow editor = MeleeEdit.scriptEditor;
		scripts.clear();
		MeleeEdit.scriptInner.removeAll();
		
		//-1 means that loopScript should be set to 0(Used after 10 00 00 00)
		//0 means that a script is NOT inside a loop
		//1 means that a script is inside a loop
		int loopScript=0;

		int pointerLoc = Character.characters[charId].subOffset
				+ 0x20 + 4 * 3 + convertSubactionMenuSelection(charId, subactionIndex, MeleeEdit.selectedMenu == MeleeEdit.MENU_ALL);

		
		
		setPosition(pointerLoc);
		int offset = readInt();
		
		int id;
		int bytesDown = 0;
		
		while (true) {
			setPosition(offset + 0x20 + bytesDown);
			id = readByte();
			id &= ~0b1;
			id &= ~0b10;

			Event e = Event.getEvent(id);
			if (e.id == 0) {
				if (readByte() == 0 && readByte() == 0 && readByte() == 0) {// check
																							   // three
																							   // times
																							   // for
																							   // three
																							   // more
																							   // bytes
					if (bytesDown == 0) {
						scripts.add(new Script("NO DATA FOUND",
								new int[4], 4));
					}
						break;
				}

			}

			setPosition(offset + 0x20 + bytesDown);
			int[] d = new int[e.length];
			for (int i = 0; i < e.length; i++) {
				d[i] = readByte();
			}
			Script temp;

			// Ignore this, just a quick way to toggle raw data showing up
			// instead of scripts.
			if (!MeleeEdit.restorePane.rawBox.isSelected()) {
				if (e.id == 0x2c) {
					temp = new HitboxScript(e.name, d, offset + 0x20
							+ bytesDown);
				} else if (e.id == 0x04 || e.id == 0x08) {
					temp = new SynchronousScript(e.name, d, offset + 0x20 + bytesDown);
				} else if (e.id == 0xe0) {
					temp = new SmashChargeScript(e.name, d, offset + 0x20
							+ bytesDown);
				} else if (e.id == 0x88) {
					temp = new ThrowScript(e.name, d, offset + 0x20 + bytesDown);
				} else if (e.id == 0x68) {
					temp = new BodyStateScript(e.name, d, offset + 0x20
							+ bytesDown);
				} else if (e.id == 0x0C) {
					temp = new LoopScript(e.name, d, offset + 0x20 + bytesDown);
					
					loopScript=1;
				}
				//else if(e.id == 0xCC){
				//	temp = new SelfDamageScript(e.name, d, offset + 0x20 + bytesDown);
				//}
			
				else if (e.id == 0x28) {
					temp = new GraphicScript(e.name, d, offset + 0x20+ bytesDown);
				}
				else if (e.id == 0x44) {
					temp = new SoundScript(e.name, d, offset + 0x20 + bytesDown);
				}
				else if (e.id == 0xDC){
					temp = new ComboSFXGFXScript(e.name, d, offset + 0x20 + bytesDown);
				}
				else {
					if(e.id==0x10)
						loopScript=-1;
					
					temp = new Script(e.name, d, offset + 0x20 + bytesDown);
				}
			}

			else {
				temp = new Script(e.name, d, offset + 0x20 + bytesDown);
			}
			temp.isScriptInsideLoop=loopScript!=0;
			temp.updateScriptBoxInfo();
			
			if(loopScript==-1)loopScript=0;
			scripts.add(temp);
			Script.number++;
			temp.setLinkedToCharacterSubaction(Character.characters[charId], pointerLoc);
			
			if(editor != null)temp.editWindow = editor;
			// System.out.println(e.name + " " + Integer.toHexString(id));
			bytesDown += e.length;

		}

		// for(int i = 0; i<20; i++)
		// System.out.println(Integer.toHexString(readByte()));

		MeleeEdit.setScripts();
	}

	public static void copy(String source, String dest) {// nabbed this function
															// from the
															// interwebs
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(new File(source)).getChannel();
			outputChannel = new FileOutputStream(new File(dest)).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			inputChannel.close();
			outputChannel.close();

		} catch (IOException e) {

		}
	}
	public static String[] getDefaultSubactions(){
		return getDefaultSubactions(MeleeEdit.selected);
	}

	public static String[] getDefaultSubactions(int charID) {
		RandomAccessFile file = null;
		try {
			//charID = 0;
			file = new RandomAccessFile("def/102/Pl"
					+ Character.characters[charID].id + ".dat",
					"rw");

			int numSubactions = SubAction.getNum(charID);
			String[] subactions = new String[numSubactions];
			for (int i = 0; i < numSubactions; i++) {
				int offTmp = i * 6 * 4;
				int pointerLoc = Character.characters[charID].subOffset
						+ 0x20 + 4 * 0 + offTmp;
				file.seek(pointerLoc);
				int pointer = file.readInt();
				file.seek(pointer + 0x20);
				String name = "";
				char tmp2;
				int counter = 4;
				while (true) {
					tmp2 = (char) file.readByte();

					if (tmp2 == 00)
						break;
					if (tmp2 == '_') {
						counter--;
					} else if (counter == 1) {
						name = name + tmp2;
					}
					if (counter == 0) {
						break;
					}

				}
				if (name == "") {
					name = "[No Name]";
				}
				
				name+="(0x"+Integer.toHexString(i).toUpperCase()+")";
				
				subactions[i] = name;
			}
			
			int c = 0;
			for(String s : subactions){
				if(s.startsWith("[No Name]"))
				c++;
			}
			
			System.out.println("[No name] = "+c);
			
			file.close();
			return subactions;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void declareAnims() {// one time run to generate a list of all
										// animation offsets/names
		try {

			// writer.println("The first line");
			// writer.println("The second line");
			int numSubactions = 0;// DEFINE THIS!
			// int tmp = 0;
			for (int k = 0; k < Character.characters.length; k++) {
				numSubactions = (Character.characters[k].subEnd - Character.characters[k].subOffset) / 6 / 4;

				MeleeEdit.selected = k;
				init();
				String[] names = getDefaultSubactions();

				PrintWriter writer = new PrintWriter("anm/"
						+ Character.characters[k].id + ".anm", "UTF-8");
				for (int i = 0; i < numSubactions; i++) {
					int offTmp = i * 6 * 4;
					int pointerLoc = Character.characters[k].subOffset + 0x20
							+ 4 * 0 + offTmp;
					setPosition(pointerLoc);
					int p1 = readInt(), p2 = readInt(), p3 = readInt(), dummy = readInt(), p4 = readByte();
					if (p2 != 0)
						writer.println(names[i] + " " + Integer.toString(p1)
								+ " " + Integer.toString(p2) + " "
								+ Integer.toString(p3) + " "
								+ Integer.toString(p4));
					// tmp++;
					// System.out.println(tmp);
				}
				writer.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void randoTize() {
		boolean hasSeed = false;
		randomizingScripts = true;
		if(MeleeEdit.restorePane.randomSeed.getText().length()>0){
			long seed;
			
			seed = MeleeEdit.restorePane.getRandomSeed();
			//System.out.println(MeleeEdit.restorePane.randomSeed.getText());
			System.out.println("Character randomization seed:"+seed);
			
			rand = new Random(seed);
			hasSeed = true;
		}else
		{
			rand = new Random();
				Options.writeRandomSeed(SeedGetter.getSeed(rand));
			
		}
		
		int maxIts = Character.characters.length
				* (SubAction.subActions.length + Attribute.attributes.length);
		int its = 0;

		int ints[] = { 14, 34, 58, 59, 60, 61, 62 };
		int dos[] = { 2, 6, 10, 14, 15, 16, 19, 20, 23, 24, 25, 26, 29, 34, 35,
				36, 42, 43, 58, 59, 60, 61, 62, 65, 66 };
		for (int i = 0; i < Character.characters.length - 1; i++) {
			byte[] isoFileData;
			isoFileData = loadedISOFile.getFileSystem().getFileData(
					"Pl" + Character.characters[i].id + ".dat");

			f = ByteBuffer.wrap(isoFileData);
			for (int k = 0; k < Attribute.attributes.length; k++) {
				boolean hold = false;
				for (int p = 0; p < dos.length; p++) {
					if (dos[p] == k)
						hold = true;
				}
				if (Attribute.attributes[k].name != "????" && hold) {
					f.position(Character.characters[i].offset + k * 4);
					// float tmp = f.readFloat();
					// 35
					float tmp = (float) Attribute.avg[k];
					if (k == 35)
						tmp *= randInt(80, 120) / 100.f;
					else
						tmp *= randInt(50, 200) / 100.f;

					for (int o = 0; o < ints.length; o++) {// worst variable
															// name I've
															// ever used
						if (ints[o] == k) {
							tmp = (int) tmp;
						}
					}

					f.position(Character.characters[i].offset + k * 4);
					f.putFloat(tmp);
					its++;
					// System.out.println(its / (float) maxIts * 100 + "%");
				}
			}
			
			SpecialMoveAttribute[] specialAtts = SpecialMovesList.getSpecialAttributesForCharacter(i);
			
			for(int k = 0; k < specialAtts.length; k ++){
				SpecialMoveAttribute att = specialAtts[k];
				f.position(att.loc);
				
				float base = f.getFloat();
				float res = 0;
				
				for(int c = 0; c < rand.nextInt(5)+1; c ++){
					res += rand.nextBoolean() ? base/2F : base/4F;
				}
				
				
				f.position(att.loc);
				if(att.isInt){
				//	f.putInt((int)res);
				}
				else{
				//	f.putFloat(res);
				}
				
			}
			
			System.out.println("Starting hitbox rando for character "+Character.characters[i].name);
			SubAction[] subactions = SubAction.subActions;
			for (int k = 0; k < SubAction.subActions.length; k++) {
				// /MeleeEdit.selected=i;
				// MeleeEdit.selectedSubaction=k;
				// MeleeEdit.selectedMenu=1;

				randoScripts(k, i);

				for (Script s : Script.scripts) {
					if (s.id == 0x2C || s.id==0x0C || s.id == 0x04) {
						s.scramble();
						s.save();
						s=null;
					}

				}
				its++;
				//System.gc();
				// System.out.println(its / (float) maxIts * 100 + "%");
			}
			try {
				FileIO.loadedISOFile.reload();
				FileIO.isoFileSystem
						.replaceFile(FileIO.isoFileSystem.getCurrentFile(),
								FileIO.f.array());

				System.out.println("Saved: "
						+ FileIO.isoFileSystem.getCurrentFile().getName());
				FileIO.loadedISOFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//System.gc();
			its++;
			// System.out.println(its / (float) maxIts * 100 + "%");
		}
		randomizingScripts = false;
		FileIO.loadedISOFile.close();
	}

	public static void randoScripts(int sub, int chara) {
		Script.scripts.clear();

		int offTmp = SubAction.subActions[sub].offset * 6 * 4;
		int pointerLoc = Character.characters[chara].subOffset
				+ 0x20 + 4 * 3 + offTmp;

		setPosition(pointerLoc);
		int offset = readInt();
		
		int id;
		int bytesDown = 0;

		while (true) {
			setPosition(offset + 0x20 + bytesDown);
			id = readByte();
			id &= ~0b1;
			id &= ~0b10;

			Event e = Event.getEvent(id);

			if (e.id == 0) {
				if (bytesDown == 0) {
					Script.scripts.add(new Script("NO DATA FOUND", new int[4],
							4));
				}
				break;
			}

			setPosition(offset + 0x20 + bytesDown);
			int[] d = new int[e.length];
			for (int i = 0; i < e.length; i++) {
				d[i] = readByte();
			}
			Script temp;

			if (e.id == 0x2c) {
				temp = new HitboxScript(e.name, d, offset + 0x20 + bytesDown);
			} else if (e.id == 0x04 || e.id == 0x08) {
				temp = new SynchronousScript(e.name, d, offset + 0x20 + bytesDown);
			} else if (e.id == 0xe0) {
				temp = new SmashChargeScript(e.name, d, offset + 0x20
						+ bytesDown);
			} else {
				temp = new Script(e.name, d, offset + 0x20 + bytesDown);
			}
			//temp.setLinkedToCharacterSubaction(Character.characters[chara], pointerLoc);
			Script.scripts.add(temp);

			bytesDown += e.length;

		}
		//ScriptUtils.updateScripts(Script.scripts);
	}
	
	public static boolean randomizingScripts=false;
	
	static Random rand = new Random();

	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static int sign() {// just returns a random sign (-,+)
		if (rand.nextInt(2) == 1)
			return 1;
		else
			return -1;
	}

	public static void save() {
		if(MeleeEdit.selectedMenu==MeleeEdit.MENU_ATTRIBUTES){
			f.position(Character.characters[MeleeEdit.selected].offset);
			for (int i = 0; i < Attribute.attributes.length; i++) {
				f.putFloat((float) MeleeEdit.attributeTable.getValueAt(i, 1));
			}
		}//This is a bit messy at the moment, but it's fully functional.
		 //Basically, this just goes through the list of special attributes and
		 //points f to wherever that attributes offset is. Then it checks if it is supposed to write an integer or not.
		 //Math.round((float)val)); is pretty weird, but it was the only way I could get the value from the special attributes menu
		 //while still having that value A) be the correct value and B) be an integer. Trying to use (int)val just threw a NumberFormatException(Or ClassCastException... It's been a while.)
		else if(MeleeEdit.selectedMenu==MeleeEdit.MENU_SPECIAL_ATTRIBUTES){
			SpecialMoveAttribute[] list = SpecialMovesList.getSpecialAttributesForCharacter(MeleeEdit.selected);
			for(int i = 0; i < list.length; i ++)
			{
				if(list[i].loc != -1 && list[i].name!="" && !(MeleeEdit.attributeTable2.getValueAt(i, 1) instanceof String)){
					f.position(list[i].loc);
					if(list[i].isInt){
						
						Object val = MeleeEdit.attributeTable2.getValueAt(i, 1);
						f.putInt(Math.round((float)val));
					}
					else{
						f.putFloat((float) MeleeEdit.attributeTable2.getValueAt(i, 1));
					}
				}
			}
		}
		
		
		try {
			FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), f.array());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileIO.init();
		
	}

	public static void shuffle() {
		f.position(Character.characters[MeleeEdit.selected + 1].offset);
		for (int i = 0; i < Attribute.attributes.length; i++) {
			f.putFloat((float) MeleeEdit.attributeTable.getValueAt(i, 1));
		}
	}
	
	
	
	
	
	
	
	public static JFileChooser characterChooser, subactionChooser;
	
	//TODO read me.
	//This was inspired by a post I saw on smashboards recently
	//http://smashboards.com/threads/sd-remix-20xx.396699/
	//Different mods that edit characters/dol conflict with each other due to the fact that the files overwrite each other.
	//Consider adding a feature that saves only CHANGED values in a character/dol file that crazy hand can read, then make the appropriate changes to said file.
	//I.E saving all character changes as a .CHC(CrazyHandChange) file would work as follows:
	//
	//1. Crazy Hand creates a ByteBuffer/byte array for the selected character file located in def/
	//2. Crazy Hand creates a ByteBuffer/byte array for the selected character file inside the currently loaded ISO
	//3. Crazy Hand compares each byte within both buffers/arrays, and adds any differing values(Along with their location within the file) to an "output" string.
	//4. Crazy Hand saves the "output" string as a .CHC file.
	//
	//The same principle applies to .DOL edits, which are the edits that would most commonly use .CHC instead of .subact
	//Benefits of this include:
	//
	//Maximized compatibility for multiple .DOL edits -- The only conflict would be when both .CHC files edit the same section of .DOL(I.E both edit pointer 0x12345 to different values), in which case the most recently loaded one would overwrite the others.
	//Convenient packaging and importing of multiple edited subactions(Can already be done with multiple .subact files, but it's always a nice option)
	//
	public static void saveCharacter(int selected) {
			characterChooser = new JFileChooser();
			System.out.println("SAVE:"+selected);
			FileNameExtensionFilter datFilter = new FileNameExtensionFilter(
					"DAT Files", "dat");
			characterChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			characterChooser.addChoosableFileFilter(datFilter);
			characterChooser.setFileFilter(datFilter);
		characterChooser.setDialogType(JFileChooser.SAVE_DIALOG);

		
		int returnVal = characterChooser.showSaveDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if(characterChooser.getSelectedFile() != null){
				try{
					File fi = characterChooser.getSelectedFile();
					
					String filePath = fi.getAbsolutePath();
					if(!filePath.endsWith(".dat")) {
					    fi = new File(filePath + ".dat");
					}
					
					if(!fi.exists()){
							fi.createNewFile();
					}
					
					RandomAccessFile raf = new RandomAccessFile(fi, "rw");
					
					FileIO.init();
					
					f.position(0);
					raf.write(f.array());
					System.out.println("Saving character file 0x"+Integer.toHexString(f.capacity())+" bytes in length.");
					raf.close();
				}
				catch(Exception e){}
			}
		}
	}

	public static void loadCharacter(int selected) {
			characterChooser = new JFileChooser();
			System.out.println("LOAD:"+selected);
			FileNameExtensionFilter datFilter = new FileNameExtensionFilter(
					"DAT Files", "dat");
			characterChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			characterChooser.addChoosableFileFilter(datFilter);
			characterChooser.setFileFilter(datFilter);
		characterChooser.setDialogType(JFileChooser.OPEN_DIALOG);

		
		int returnVal = characterChooser.showOpenDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try{
				FileIO.init();
				File fi = characterChooser.getSelectedFile();
				if(!fi.exists()){System.out.println("Tried to load a file that didn't exist!");return;}
				
				RandomAccessFile raf = new RandomAccessFile(fi, "r");
				
				byte[] characterFileData = new byte[(int)raf.length()];
				raf.readFully(characterFileData);
				
				ByteBuffer charData = ByteBuffer.wrap(characterFileData);
				charData.position(0);
				f.position(Character.characters[selected].offset);
				f = charData;
				/*Used for checking if there were ANY differences between the two files
				 * Only used for debugging purposes and is not needed.
				String diffs = "";
				int place = 0;
				System.out.println("Starting file comparison");
				while(charData.hasRemaining()){
					byte b1 = charData.get();
					byte b2 = f.get();
					if(b1!=b2){
						diffs += ("@"+place+":"+b1+":"+b2+"\n");
					}
					place ++;
				}
				System.out.println("Finished file comparison");
				FileIO.init();
				charData.position(0);
				System.out.println("Starting to write debug");
				Options.writeDebug(diffs);
				System.out.println("Finished writing debug");
				*/
				
				FileIO.loadedISOFile.reload();
				isoFileSystem.replaceFile(isoFileSystem.getCurrentFile(), charData.array());
				
				
				FileIO.loadedISOFile.close();
				raf.close();
				//isoFileSystem.clearCachedISOFiles();
				
				FileIO.loadedISOFile.reload();
				FileIO.init();
				
			}
			catch(Exception e){}
		}
	}
	
	public static String stageFile="PlCaAJ.dat";
	
	//TODO remove when finished!
	public static void runDebugStageSwap() throws IOException {
		
		if(1==1)return;
		
		FileIO.loadedISOFile.reload();
		
		ISOFile ifo = FileIO.loadedISOFile.getFileSystem().getISOFile(stageFile);
		
		RandomAccessFile raf = new RandomAccessFile(stageFile, "r");
		
		byte[] byt = new byte[(int) raf.length()];
		
		raf.readFully(byt);
		
		System.out.println("Replacing file " +stageFile+"....");
		
		FileIO.isoFileSystem.replaceFile(ifo,byt);
		
		System.out.println("Done replacing file " +stageFile);
		
		FileIO.loadedISOFile.close();
		
	}
	
	
	
}