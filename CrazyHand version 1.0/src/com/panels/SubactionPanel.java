package com.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.Character;
import com.EventIdentifier;
import com.FileIO;
import com.MeleeEdit;
import com.scripts.BodyStateScript;
import com.scripts.ComboSFXGFXScript;
import com.scripts.GraphicScript;
import com.scripts.HitboxScript;
import com.scripts.LoopScript;
import com.scripts.ProjectileScript;
import com.scripts.Script;
import com.scripts.ScriptComparator;
import com.scripts.ScriptUtils;
import com.scripts.SmashChargeScript;
import com.scripts.SoundScript;
import com.scripts.SynchronousScript;
import com.scripts.ThrowScript;

public class SubactionPanel extends JPanel {

	public static JComboBox subactionList;
	public static int loggedSubactionSelection = 0;
	// public static ScriptEditWindow scriptEditor = null;
	public int selectedSubaction = 0;
	public static JPanel scriptInner;
	public JScrollPane scripts;

	public SubactionPanel() {

		
		setSubactionList();
		

		scriptInner = new JPanel();
		scriptInner.setLayout(new BoxLayout(scriptInner, BoxLayout.PAGE_AXIS));
		FileIO.init();
		readScripts();

		scripts = new JScrollPane(scriptInner);//TODO see what to do about this
		//scripts = new JScrollPane(scriptInner,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scripts.setPreferredSize(new Dimension(600, 600));
		scripts.getVerticalScrollBar().setUnitIncrement(10);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(subactionList);
		add(scripts);

	}
	
	//type=0 commonOnly
	//type=1 all
	//TODO clean this up
	public void setSubactionList(){	
		if (MeleeEdit.selectedMenu == MeleeEdit.MENU_SUBACTIONS_COMMON) {
			String[] tmp2 = new String[Subaction.subActions.length];
			for (int i = 0; i < tmp2.length; i++) {
				tmp2[i] = Subaction.subActions[i].name;
			}
			subactionList = new JComboBox(tmp2);
			subactionList.setSelectedIndex(0);
			subactionList.addActionListener(new SubactionListener());
			subactionList.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			subactionList.setMaximumRowCount(20);//TODO idk... like, something. get off my back
		}

		if (MeleeEdit.selectedMenu == MeleeEdit.MENU_SUBACTIONS_ALL) {
			subactionList = new JComboBox(getDefaultSubactions());
			subactionList.setSelectedIndex(0);
			subactionList.addActionListener(new SubactionListener());
			subactionList.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		}
	}

	public void updateSubactions() {
		//TOGO remove this bogus
		System.out.println("LO");
		System.out.println(MeleeEdit.selected);
		System.out.println(selectedSubaction);
		System.out.println("");
		
		FileIO.init();
		readScripts(Script.scripts, MeleeEdit.selected, selectedSubaction);
		
		System.out.println("LO");
		System.out.println(MeleeEdit.selected);
		System.out.println(selectedSubaction);
		System.out.println("");

		//scripts = new JScrollPane(scriptInner);//TODO see what to do about this
		//scripts.setPreferredSize(new Dimension(600, 600));
		//scripts.getVerticalScrollBar().setUnitIncrement(10);

		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//add(subactionList);
		//add(scripts);


	}

	public void update() {
		updateSubactions();
	}

	public void setScripts() {
		int i = -1;

		scriptInner.removeAll();
		scriptInner.setBackground(new Color(.81f,.81f,.9f,1.0f));
		scriptInner.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));//TODO functional!

		ScriptUtils.updateScripts(Script.scripts);
		Collections.sort(Script.scripts, new ScriptComparator());

		for (Script script : Script.scripts) {
			i++;
			script.setBorder(BorderFactory.createEmptyBorder(6, 2, 6, 2));
			scriptInner.add(script);
			scriptInner.add(Box.createVerticalStrut(20));
		}

	}


	public class SubactionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			
			System.out.print("subaction Index:");
			System.out.println(cb.getSelectedIndex());
			
			selectedSubaction = cb.getSelectedIndex();
			
			MeleeEdit.contentPane.softUpdate();

		}
	}

	public void readScripts() {
		readScripts(Script.scripts);
	}
	
	public void readScripts(ArrayList<Script> scripts) {
		if (selectedSubaction == -1) {
			selectedSubaction = 0;
		}
		readScripts(scripts, MeleeEdit.selected, selectedSubaction);
	}
	
	public void readScripts(ArrayList<Script> scripts, int charId, int subactionIndex) {
		Script.number = 1;//TODO might be relic
		scripts.clear();
		scriptInner.removeAll();


		// -1 means that loopScript should be set to 0(Used after 10 00 00 00)
		// 0 means that a script is NOT inside a loop
		// 1 means that a script is inside a loop
		int loopScript = 0;
		
		
		
		if (subactionIndex < 0) {
			subactionIndex = 0;
			System.out.println("Error! Subaction ID out of bounds!");
		}
		if(charId>=Character.characters.length){
			charId=0;
			System.out.println("Error! Character ID out of bounds!");
		}
		
		
		int pointerLoc;
		
		pointerLoc = Character.characters[charId].subOffset + 0x20 + 4 * 3;

		if(MeleeEdit.selectedMenu != MeleeEdit.MENU_SUBACTIONS_ALL){
			pointerLoc+=Subaction.subActions[subactionIndex].offset * 6 * 4;
		}
		else{
			pointerLoc+=subactionIndex*6*4;
		}

		
		
		
		
		
		FileIO.setPosition(pointerLoc);
		int offset = FileIO.readInt();

		int id;
		int bytesDown = 0;

		while(true){
			FileIO.setPosition(offset + 0x20 + bytesDown);
			id = FileIO.readByte();
			id &= ~0b11;

			EventIdentifier e = EventIdentifier.getEventIdentifier(id);
			if (e.id == 0) {
				// check three times for three more bytes
				if (FileIO.readByte() == 0 && FileIO.readByte() == 0 && FileIO.readByte() == 0) {

					if (bytesDown == 0) {
						scripts.add(new Script("NO DATA FOUND", new int[4], 4));
					}
					break;
				}

			}

			FileIO.setPosition(offset + 0x20 + bytesDown);
			int[] d = new int[e.length];
			for (int i = 0; i < e.length; i++) {
				d[i] = FileIO.readByte();
			}

			Script temp = getScript(e, d, offset + 0x20 + bytesDown);
			if(e.id==0x10)
				loopScript=-1;

			temp.isScriptInsideLoop = loopScript != 0;
			temp.updateScriptBoxInfo();

			if (loopScript == -1)
				loopScript = 0;
			scripts.add(temp);
			Script.number++;
			temp.setLinkedToCharacterSubaction(Character.characters[charId], pointerLoc);

			bytesDown += e.length;

		}
		setScripts();
	}

	
	public Script getScript(EventIdentifier e,int[] data, int location){
		Script temp;
		if(MeleeEdit.fileMenu.rawSubactions.isSelected()){//TODO add ||(the script .txt said to ignore custom script)
			return new Script(e.name,data, location);
		}
		else{
			switch(e.id){
				case 0x2c:
					return new HitboxScript(e.name, data, location);
				case 0x04:
					return new SynchronousScript(e.name, data, location);
				case 0x08:
					return new SynchronousScript(e.name, data, location);
				case 0xe0:
					return new SmashChargeScript(e.name, data, location);
				case 0x88:
					return new ThrowScript(e.name, data, location);
				case 0x68:
					return new BodyStateScript(e.name, data, location);
				case 0x0c:
					return new LoopScript(e.name, data, location);
				case 0x28:
					return new GraphicScript(e.name, data, location);
				case 0x44:
					return new SoundScript(e.name, data, location);
				case 0xdc:
					return new ComboSFXGFXScript(e.name, data, location);
				case 0xf4:
					return new ProjectileScript(e.name, data, location);
				default:
					return new Script(e.name,data,location);
			}
		}
		
	}

	

	public static String[] getDefaultSubactions() {
		return getDefaultSubactions(MeleeEdit.selected);
	}

	public static String[] getDefaultSubactions(int charID) {
		RandomAccessFile file = null;
		try {
			// charID = 0;
			file = new RandomAccessFile("def/102/Pl" + Character.characters[charID].id + ".dat", "rw");

			int numSubactions = Subaction.getNum(charID);
			String[] subactions = new String[numSubactions];
			for (int i = 0; i < numSubactions; i++) {
				int offTmp = i * 6 * 4;
				int pointerLoc = Character.characters[charID].subOffset + 0x20 + 4 * 0 + offTmp;
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

				name += "(0x" + Integer.toHexString(i).toUpperCase() + ")";

				subactions[i] = name;
			}

			int c = 0;
			for (String s : subactions) {
				if (s.startsWith("[No Name]"))
					c++;
			}

			//System.out.println("[No name] = " + c);

			file.close();
			return subactions;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}

class Subaction {
	public static Subaction[] subActions = { new Subaction(0x2e, "Jab 1"), new Subaction(0x2f, "Jab 2"),
			new Subaction(0x30, "Jab 3?"), new Subaction(0x31, "Rapid Jab Start"),
			new Subaction(0x32, "Rapid Jab Loop"), new Subaction(0x33, "Rapid Jab End"),
			new Subaction(0x34, "Dash Attack"), new Subaction(0x35, "Forward-Tilt (High)"),
			new Subaction(0x36, "Forward-Tilt (Mid-High)"), new Subaction(0x37, "Forward-Tilt (Middle)"),
			new Subaction(0x38, "Forward-Tilt (Mid-Low)"), new Subaction(0x39, "Forward-Tilt (Low)"),
			new Subaction(0x3a, "Up-Tilt"), new Subaction(0x3b, "Down-Tilt"),
			new Subaction(0x3c, "Forward-Smash (High)"), new Subaction(0x3d, "Forward-Smash (Mid-High)"),
			new Subaction(0x3e, "Forward-Smash (Middle)"), new Subaction(0x3f, "Forward-Smash (Mid-Low)"),
			new Subaction(0x42, "Up-Smash"), new Subaction(0x43, "Down-Smash"), new Subaction(0x44, "Neutral-Air"),
			new Subaction(0x45, "Forward-Air"), new Subaction(0x46, "Back-Air"), new Subaction(0x47, "Up-Air"),
			new Subaction(0x48, "Down-Air"), new Subaction(0xf7, "Forward Throw"), new Subaction(0xf8, "Back Throw"),
			new Subaction(0xf9, "Up Throw"), new Subaction(0xfa, "Down Throw"),

			new Subaction(0xf2, "Grab"), new Subaction(0xf3, "Dash Grab"), new Subaction(0xf5, "Grab Pummel"),
			new Subaction(0xfa, "Down Throw"), new Subaction(0xef, "Taunt"), new Subaction(0x29, "Spot Dodge"),
			new Subaction(0x2a, "Roll Forward"), new Subaction(0x2b, "Roll Backward"),
			new Subaction(0x2c, "Air Dodge"), };

	public int offset;
	public String name;

	public Subaction(int o, String s) {
		name = s;
		offset = o;

	}

	public static int getNum() {// returns total number of subactions for
								// selected character
		return (Character.characters[MeleeEdit.selected].subEnd - Character.characters[MeleeEdit.selected].subOffset)
				/ 4 / 6;
	}

	public static int getNum(int k) {
		return (Character.characters[k].subEnd - Character.characters[k].subOffset) / 4 / 6;
	}

}
