package com;

import static com.MeleeEdit.MENU_ALL;
import static com.MeleeEdit.MENU_ATTACKS;
import static com.MeleeEdit.MENU_SPECIAL_MOVES;
import isotool.filesystem.ISO;
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
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.SpecialMovesList.SpecialMoveAttribute;
import com.scripts.*;


;

public class FileIO {
	// TEST TEST TEST

	public static ByteBuffer f;

	public static ISOFileSystem isoFileSystem;

	public static ISO loadedISOFile;

	// TEST!
	public static void init() {
		byte[] isoFileData;
		isoFileData = loadedISOFile.getFileSystem()
				.getFileData(
						"Pl" + Character.characters[MeleeEdit.selected].id
								+ ".dat");

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
				Options.writeDolphinRunFile();
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

	public static int readInt() {
		int val = 0;
		val = f.getInt();
		return val;
	}

	public static void setPosition(int pos) {
		f.position(pos);
	}

	public static void readScripts() {
		Script.number = 1;
		Script.scripts.clear();
		MeleeEdit.scriptInner.removeAll();
		
		//-1 means that loopScript should be set to 0(Used after 10 00 00 00)
		//0 means that a script is NOT inside a loop
		//1 means that a script is inside a loop
		int loopScript=0;
		
		if (MeleeEdit.selectedSubaction == -1) {
			MeleeEdit.selectedSubaction = 0;
		}
		
		boolean specialFlag = MeleeEdit.selectedSubaction > SubAction.subActions.length-1;//Used for reading special moves once finished reading normal attacks
		
		int offTmp;
		if (MeleeEdit.selectedMenu == MENU_ATTACKS) {
			if (MeleeEdit.selectedSubaction >= SubAction.subActions.length+SpecialMovesList.getListForCharacter(MeleeEdit.selected).length) {
				MeleeEdit.selectedSubaction = SubAction.subActions.length+SpecialMovesList.getListForCharacter(MeleeEdit.selected).length - 1;
			}
			offTmp =specialFlag ? SpecialMovesList.getListForCharacter(MeleeEdit.selected)[MeleeEdit.selectedSubaction-SubAction.subActions.length].offset * 6 * 4
								: SubAction.subActions[MeleeEdit.selectedSubaction].offset * 6 * 4;
		} else if (MeleeEdit.selectedMenu == MENU_SPECIAL_MOVES) {

			offTmp = SpecialMovesList.getListForCharacter(MeleeEdit.selected)[MeleeEdit.selectedSubaction].offset * 6 * 4;
		} else if (MeleeEdit.selectedMenu == MENU_ALL) {
			offTmp = MeleeEdit.selectedSubaction * 6 * 4;
		} else {
			if (MeleeEdit.selectedSubaction >= SubAction.subActions.length) {
				MeleeEdit.selectedSubaction = SubAction.subActions.length - 1;
			}
			offTmp = SubAction.subActions[MeleeEdit.selectedSubaction].offset * 6 * 4;// defaults
																						// to
																						// attacks
																						// only
		}
		
		

		int pointerLoc = Character.characters[MeleeEdit.selected].subOffset
				+ 0x20 + 4 * 3 + offTmp;
		// SubActionsStart
		// + (4 bytes)*(3rd position)
		// + SubActionNumber*(6 sets)*(4 bytes)

		// System.out.println("Pointer location: " +
		// Integer.toHexString(pointerLoc));
		setPosition(pointerLoc);
		int offset = readInt();
		// System.out.println("Pointer: " + Integer.toHexString(offset));
		
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
						Script.scripts.add(new Script("NO DATA FOUND",
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
					for(int i = 0; i < d.length; i ++)
					{
						System.out.println(d[i]);
					}
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
			
				else if (e.id == 0x28) {
					temp = new GraphicScript(e.name, d, offset + 0x20+ bytesDown);
				}
				else if (e.id == 0x44) {
					temp = new SoundScript(e.name, d, offset + 0x20 + bytesDown);
				}else {
					if(e.id==0x10)
						loopScript=-1;
					
					temp = new Script(e.name, d, offset + 0x20 + bytesDown);
				}
			}

			else {
				temp = new Script(e.name, d, offset + 0x20 + bytesDown);
			}
			
			System.out.println("loopscript:"+loopScript);
			temp.isScriptInsideLoop=loopScript!=0;
			temp.updateScriptBoxInfo();
			
			if(loopScript==-1)loopScript=0;
			
			System.out.println("script:"+temp.isScriptInsideLoop);
			
			System.out.println(e.name + ":" + offset + 0x20 + bytesDown);
			Script.scripts.add(temp);
			Script.number++;

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

	public static String[] getDefaultSubactions() {
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile("def/102/Pl"
					+ Character.characters[MeleeEdit.selected].id + ".dat",
					"rw");

			int numSubactions = SubAction.getNum();

			String[] subactions = new String[numSubactions];
			int tmp = 0;
			for (int i = 0; i < numSubactions; i++) {
				int offTmp = i * 6 * 4;
				int pointerLoc = Character.characters[MeleeEdit.selected].subOffset
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
				// System.out.println(name);
				subactions[i] = name;

			}
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
			for (int k = 0; k < SubAction.subActions.length; k++) {
				// /MeleeEdit.selected=i;
				// MeleeEdit.selectedSubaction=k;
				// MeleeEdit.selectedMenu=1;

				randoScripts(k, i);

				for (Script s : Script.scripts) {
					if (s.id == 0x2c) {
						s.scramble();
						s.save();
					}

				}
				its++;
				// System.out.println(its / (float) maxIts * 100 + "%");
			}
			try {
				FileIO.loadedISOFile.reload();
				FileIO.isoFileSystem
						.replaceFile(FileIO.isoFileSystem.getCurrentFile(),
								FileIO.f.array());

				System.out.println("Saved: "
						+ FileIO.isoFileSystem.getCurrentFile().getName());

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			its++;
			// System.out.println(its / (float) maxIts * 100 + "%");
		}

		FileIO.loadedISOFile.close();

	}

	public static void randoScripts(int sub, int chara) {
		Script.scripts.clear();

		int offTmp = SubAction.subActions[sub].offset * 6 * 4;
		int pointerLoc = Character.characters[chara].subOffset + 0x20 + 4 * 3
				+ offTmp;

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
			Script.scripts.add(temp);

			bytesDown += e.length;

		}
	}

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
		}
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
	}

	public static void shuffle() {
		f.position(Character.characters[MeleeEdit.selected + 1].offset);
		for (int i = 0; i < Attribute.attributes.length; i++) {
			f.putFloat((float) MeleeEdit.attributeTable.getValueAt(i, 1));
		}
	}
	
	
	
	
	
	
	
	public static JFileChooser subactionChooser;
	
	//TODO read me.
	//Saving and loading subactions doesn't function properly at the moment.
	//The main reason why is for whatever reason the first X(X being 2 or 4 from what I've fiddled with) amount of values either aren't saved to or read from .subact files.
	//If anyone wants to play around with this, feel free. Tell me if you are though because I plan to fix this ASAP.
	
	public static void saveSubaction() {
		if(subactionChooser == null){
			subactionChooser = new JFileChooser();
			FileNameExtensionFilter datFilter = new FileNameExtensionFilter(
					"Subaction Files", ".subact");
			subactionChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			subactionChooser.addChoosableFileFilter(datFilter);
			subactionChooser.setFileFilter(datFilter);
		}
		subactionChooser.setDialogType(JFileChooser.SAVE_DIALOG);

		
		int returnVal = subactionChooser.showSaveDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File fi = subactionChooser.getSelectedFile();
				String filePath = fi.getAbsolutePath();
				if(!filePath.endsWith(".subact")) {
				    fi = new File(filePath + ".subact");
				}
			
			RandomAccessFile raf = new RandomAccessFile(fi, "rw");
			
			boolean specialFlag = MeleeEdit.selectedSubaction > SubAction.subActions.length-1;//Used for reading special moves once finished reading normal attacks
			
			int offTmp;
			if (MeleeEdit.selectedMenu == MENU_ATTACKS) {
				if (MeleeEdit.selectedSubaction >= SubAction.subActions.length+SpecialMovesList.getListForCharacter(MeleeEdit.selected).length) {
					MeleeEdit.selectedSubaction = SubAction.subActions.length+SpecialMovesList.getListForCharacter(MeleeEdit.selected).length - 1;
				}
				offTmp =specialFlag ? SpecialMovesList.getListForCharacter(MeleeEdit.selected)[MeleeEdit.selectedSubaction-SubAction.subActions.length].offset * 6 * 4
									: SubAction.subActions[MeleeEdit.selectedSubaction].offset * 6 * 4;
			} else if (MeleeEdit.selectedMenu == MENU_SPECIAL_MOVES) {
	
				offTmp = SpecialMovesList.getListForCharacter(MeleeEdit.selected)[MeleeEdit.selectedSubaction].offset * 6 * 4;
			} else if (MeleeEdit.selectedMenu == MENU_ALL) {
				offTmp = MeleeEdit.selectedSubaction * 6 * 4;
			} else {
				if (MeleeEdit.selectedSubaction >= SubAction.subActions.length) {
					MeleeEdit.selectedSubaction = SubAction.subActions.length - 1;
				}
				offTmp = SubAction.subActions[MeleeEdit.selectedSubaction].offset * 6 * 4;// defaults
																							// to
																							// attacks
																							// only
			}
			
			
	
			int pointerLoc = Character.characters[MeleeEdit.selected].subOffset
					+ 0x20 + 4 * 3 + offTmp;
			
			setPosition(pointerLoc);
			//int offset = readInt();
			
			int id;
			int bytesDown = 0;
			

			int c=0;
			raf.seek(0);
			while (c < Script.scripts.size()) {
				Script s = Script.scripts.get(c);
				for(int i = 0; i < s.data.length; i ++){
					raf.writeByte(((byte) (s.data[i] & 0xff)));
				}
				System.out.println(c);
				c++;
			}
			

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static int readByte(RandomAccessFile fr) {
		int val = 0;
		try {
			val = fr.readByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ((short) (val & 0xff));

	}
	

	
	public static void loadSubaction() {
		if(subactionChooser == null){
			subactionChooser = new JFileChooser();
			FileNameExtensionFilter datFilter = new FileNameExtensionFilter(
					"Subaction Files", ".subact");
			subactionChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			subactionChooser.addChoosableFileFilter(datFilter);
			subactionChooser.setFileFilter(datFilter);
		}
		
		subactionChooser.setDialogType(JFileChooser.OPEN_DIALOG);

		
		int returnVal = subactionChooser.showOpenDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if(subactionChooser.getSelectedFile() != null){
				try {
					RandomAccessFile raf = new RandomAccessFile(subactionChooser.getSelectedFile(), "r");
					Script.number = 1;
					Script.scripts.clear();
					MeleeEdit.scriptInner.removeAll();
					
					//-1 means that loopScript should be set to 0(Used after 10 00 00 00)
					//0 means that a script is NOT inside a loop
					//1 means that a script is inside a loop
					int loopScript=0;
					
					if (MeleeEdit.selectedSubaction == -1) {
						MeleeEdit.selectedSubaction = 0;
					}
					
					boolean specialFlag = MeleeEdit.selectedSubaction > SubAction.subActions.length-1;//Used for reading special moves once finished reading normal attacks
					
					int offTmp;
					if (MeleeEdit.selectedMenu == MENU_ATTACKS) {
						if (MeleeEdit.selectedSubaction >= SubAction.subActions.length+SpecialMovesList.getListForCharacter(MeleeEdit.selected).length) {
							MeleeEdit.selectedSubaction = SubAction.subActions.length+SpecialMovesList.getListForCharacter(MeleeEdit.selected).length - 1;
						}
						offTmp =specialFlag ? SpecialMovesList.getListForCharacter(MeleeEdit.selected)[MeleeEdit.selectedSubaction-SubAction.subActions.length].offset * 6 * 4
											: SubAction.subActions[MeleeEdit.selectedSubaction].offset * 6 * 4;
					} else if (MeleeEdit.selectedMenu == MENU_SPECIAL_MOVES) {

						offTmp = SpecialMovesList.getListForCharacter(MeleeEdit.selected)[MeleeEdit.selectedSubaction].offset * 6 * 4;
					} else if (MeleeEdit.selectedMenu == MENU_ALL) {
						offTmp = MeleeEdit.selectedSubaction * 6 * 4;
					} else {
						if (MeleeEdit.selectedSubaction >= SubAction.subActions.length) {
							MeleeEdit.selectedSubaction = SubAction.subActions.length - 1;
						}
						offTmp = SubAction.subActions[MeleeEdit.selectedSubaction].offset * 6 * 4;// defaults
																									// to
																									// attacks
																									// only
					}
					
					

					int pointerLoc = Character.characters[MeleeEdit.selected].subOffset
							+ 0x20 + 4 * 3 + offTmp;

					setPosition(pointerLoc);
					int offset = readInt();
					
					int id;
					int bytesDown = 0;
					
					raf.seek(0);
					boolean b = false;
					while (raf.read() != -1) {
						if(b){raf.seek(0);b=true;}
						setPosition(offset + 0x20 + bytesDown);
						id = raf.readByte();
						id &= ~0b1;
						id &= ~0b10;
						
						//System.out.println(id);

						setPosition(offset + 0x20 + bytesDown);
						
						writeByte(readByte(raf));

						bytesDown += 1;

					}

					MeleeEdit.setScripts();

				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
	//Used for the File Explorer menu.
	//Somewhat buggy at the moment.
	public static void readScriptsWithinRange(int start, int end) {
		
		//if(1==1)return;
		
		Script.number = 1;
		Script.scripts.clear();
		MeleeEdit.scriptInner.removeAll();
		
		//-1 means that loopScript should be set to 0(Used after 10 00 00 00)
		//0 means that a script is NOT inside a loop
		//1 means that a script is inside a loop
		int loopScript=0;
		
		if (MeleeEdit.selectedSubaction == -1) {
			MeleeEdit.selectedSubaction = 0;
		}
		
		boolean specialFlag = MeleeEdit.selectedSubaction > SubAction.subActions.length-1;//Used for reading special moves once finished reading normal attacks
		
		
		int offTmp = start* 6 * 4;
		
		end = (end*6*4);

		setPosition(offTmp);
		int offset = readInt();
		
		
		int id;
		int bytesDown = 0;
		
		System.out.println("Starting search! Start:" + start + " End:"+end);
		System.out.println("File position:" + f.position() + " Search offset:" + offset);
		int cnt = 0;
		
		while (offset + 0x20 + bytesDown < end) {
			
			setPosition(offset + 0x20 + bytesDown);
			id = readByte();
			id &= ~0b1;
			id &= ~0b10;
			
			float f = readFloat();
			
			System.out.println("Loc:" + (offset+0x20+bytesDown) + " Val:"+f);

			setPosition(offset + 0x20 + bytesDown);

			// System.out.println(e.name + " " + Integer.toHexString(id));
			bytesDown += 1;
			cnt ++;
		}
		
		System.out.println("Total scripts read: " + cnt + " FileIO ending position:" + f.position());
		
		// for(int i = 0; i<20; i++)
		// System.out.println(Integer.toHexString(readByte()));

		MeleeEdit.setScripts();
	}
	
	
}