package com;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.scripts.Script;
import com.scripts.ScriptUtils;
import com.scripts.SoundScript;
import com.scripts.SynchronousScript;

public class FileMenu extends JMenuBar {

	public JMenuItem saveCharacterButton, loadCharacterButton, newTabButton, wipeSubactionButton, revertSubactionButton,
			growSubactionButton, shrinkSubactionButton;

	fileListener fl = new fileListener();

	// TODO move to bottom
	// this just cleans up the code a bit visually and makes it easier to edit
	public JMenuItem createItem(String name, String command) {
		JMenuItem tmp = new JMenuItem(name);
		tmp.setActionCommand(command);
		tmp.addActionListener(fl);
		return tmp;
	}

	public JMenu createFileTree(String name, String tag) {
		JMenu tmp = new JMenu(name);

		JMenu character = new JMenu("Character File");
		character.add(createItem("Currently Selected Character", "current"));
		character.add(createItem("All Character Files", "allChar"));
		for (int i = 0; i < Character.characters.length; i++) {
			character.add(createItem(Character.characters[i].name, Character.characters[i].id));
		}

		tmp.add(character);
		tmp.add(createItem("Start.dol", "dol"));
		tmp.add(createItem("PlCo.dat", "plco"));
		tmp.add(createItem("ItCo.dat", "itco"));
		tmp.add(createItem("ALL above files", "all"));

		return tmp;
	}

	
	//TODO decide which of these actually need member variables
	public JMenu file, run, editor, options, subaction;
	public JMenuItem openISO, closeISO;
	public JMenu save, load, reset;
	public JMenuItem wipeSub, revertSub, toggleRaw;

	public JCheckBox rawSubactions, isoStart;

	public FileMenu() {
		file = new JMenu("File");
		run = new JMenu("Run");
		editor = new JMenu("Editor View");
		options = new JMenu("Options");
		subaction = new JMenu("Subaction Tools");

		openISO = createItem("Open ISO", "openISO");
		closeISO = createItem("Close ISO", "closeISO");

		save = createFileTree("Save File", "s");
		load = createFileTree("Load File", "l");
		reset = createFileTree("Reset File", "r");
		

		// TODO
		run.add(createItem("Run Dolphin", "dolphin"));
		// run.add code manager, melee toolbox ...?
		
		// TODO a) decide if this is worth keeping, and b) make it a member
		//TOOD prolly just get rid of it. looks bad
		// variable and add action listener.
		DefaultListModel<String> m = new DefaultListModel<String>();
		m.addElement("Most recently accessed directory");
		m.addElement("Crazy Hand root directory");
		m.addElement("Specified location");
		JList<String> list = new JList<String>(m);
		JMenu tmp = (new JMenu("Default ISO directory"));
		tmp.add(list);
		options.add(tmp);

		isoStart = new JCheckBox("Auto-load ISO from default directory");
		isoStart.addActionListener(fl);
		isoStart.setActionCommand("start");
		options.add(isoStart);

		editor.add(createItem("Subaction Editor (common values only)", "editorSub"));
		editor.add(createItem("Subaction Editor (all)", "editorSuball"));
		editor.add(createItem("Attribute Editor", "editorAttributes"));
		editor.add(createItem("Character Specific Attribute Editor", "editorSpecial"));
		editor.add(createItem("Projectile Editor", "editorProjectiles"));
		editor.add(createItem("Animation Swap Editor", "editorAnimation"));
		editor.add(createItem("Player Common Values (PlCo) Editor", "editorPlCo"));
		editor.add(createItem("Frame Speed Modifier Editor", "editorFSM"));
		editor.add(createItem("Move Logic Table", "editorLogic"));
		editor.add(createItem("Tools", "editorTools"));

		subaction.add(createItem("Wipe Subaction", "wipeSub"));
		subaction.add(createItem("Revert Subaction", "revertSub"));
		rawSubactions = new JCheckBox("Display raw data");
		rawSubactions.addActionListener(fl);
		rawSubactions.setActionCommand("raw");
		subaction.add(rawSubactions);

		file.add(openISO);
		file.add(closeISO);
		file.add(save);
		file.add(load);
		file.add(reset);
		
		add(file);
		add(Box.createHorizontalStrut(10));
		add(editor);
		add(Box.createHorizontalStrut(10));
		add(run);
		add(Box.createHorizontalStrut(10));
		add(options);
		add(Box.createHorizontalStrut(10));
		add(subaction);
		add(Box.createHorizontalStrut(10));
		
		
		

		
		subaction.setVisible(false);
	}

	class fileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "openISO":
				FileIO.loadISOFile();
				break;
			case "closeISO":
				Container comp = getParent();
				Container comp2 = null;
				while (comp != null) {
					comp2 = comp;
					comp = comp2.getParent();
				}
				if (comp2 instanceof JFrame) {
					((JFrame) comp2).dispose();
				}
				break;
			case "dolphin":
				Options.openDolphin();
				break;
			case "wipeSub":
				for (int i = 0; i < Script.scripts.size(); i++) {
					Script s = Script.scripts.get(i);
					for (int d = 0; d < s.data.length; d++) {
						s.data[d] = (d % 4 == 0 ? 0xCC : 0x00);
					}
				}
				ScriptUtils.fixScriptsAfterSwap(Script.scripts);
				// attributePanel.save();
				// MeleeEdit.refreshData();
				break;
			case "revertSub":
				try {
					RandomAccessFile raf = new RandomAccessFile(
							"def/102/Pl" + Character.characters[MeleeEdit.selected].id + ".dat", "r");

					FileIO.init();

					int subStart = Integer.MAX_VALUE;
					int subEnd = Integer.MIN_VALUE;

					for (Script sc : Script.scripts) {
						if (sc.location < subStart) {
							subStart = sc.location;
						}
						if (sc.location + sc.data.length > subEnd) {
							subEnd = sc.location + sc.data.length;
						}
					}

					System.out.println("Reverting subaction[START=0x" + Integer.toHexString(subStart) + ", END=0x"
							+ Integer.toHexString(subEnd) + "]");

					raf.skipBytes(subStart);
					FileIO.setPosition(subStart);

					int off = 0;
					while (subStart + off < subEnd) {
						FileIO.writeByte(raf.readByte());
						off++;
					}

					// FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(),
					// FileIO.f.array());

					// FileIO.init();
					// attributePanel.save();//TODO again, really tho?
					// MeleeEdit.refreshData();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case "start":
				System.out.println("Toggled iso autoload");
				break;
			case "editorSub":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_SUBACTIONS_COMMON;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorSuball":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_SUBACTIONS_ALL;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorAttributes":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_ATTRIBUTES;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorSpecial":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_SPECIAL_ATTRIBUTES;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorProjectiles":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_PROJECTILE_EDIT_CHARACTER;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorAnimation":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_ANIMATION;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorPlCo":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_COMMON_DATA;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorFSM":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_FRAME_SPEED_MODIFIERS;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorLogic":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_MOVE_LOGIC;
				MeleeEdit.contentPane.hardUpdate();
				break;
			case "editorTools":
				MeleeEdit.selectedMenu = MeleeEdit.MENU_OTHER;
				MeleeEdit.contentPane.hardUpdate();
				break;
			default:
				System.out.println("Not implemented!");
				break;
			// save = createFileTree("Save File","s");
			// load = createFileTree("Load File" ,"l");
			// reset = createFileTree("Reset File","r");

			}

		}

	}

	public static JFileChooser characterChooser, subactionChooser;

	public static void saveCharacter(int selected) {
		characterChooser = new JFileChooser();
		System.out.println("SAVE:" + selected);
		FileNameExtensionFilter datFilter = new FileNameExtensionFilter("DAT Files", "dat");
		characterChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		characterChooser.addChoosableFileFilter(datFilter);
		characterChooser.setFileFilter(datFilter);
		characterChooser.setDialogType(JFileChooser.SAVE_DIALOG);

		int returnVal = characterChooser.showSaveDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (characterChooser.getSelectedFile() != null) {
				try {
					File fi = characterChooser.getSelectedFile();

					String filePath = fi.getAbsolutePath();
					if (!filePath.endsWith(".dat")) {
						fi = new File(filePath + ".dat");
					}

					if (!fi.exists()) {
						fi.createNewFile();
					}

					RandomAccessFile raf = new RandomAccessFile(fi, "rw");

					FileIO.init();

					FileIO.f.position(0);
					raf.write(FileIO.f.array());
					System.out.println("Saving character file 0x" + Integer.toHexString(FileIO.f.capacity())
							+ " bytes in length.");
					raf.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static void loadCharacter(int selected) {
		characterChooser = new JFileChooser();
		System.out.println("LOAD:" + selected);
		FileNameExtensionFilter datFilter = new FileNameExtensionFilter("DAT Files", "dat");
		characterChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		characterChooser.addChoosableFileFilter(datFilter);
		characterChooser.setFileFilter(datFilter);
		characterChooser.setDialogType(JFileChooser.OPEN_DIALOG);

		int returnVal = characterChooser.showOpenDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				FileIO.init();
				File fi = characterChooser.getSelectedFile();
				if (!fi.exists()) {
					System.out.println("Tried to load a file that didn't exist!");
					return;
				}

				RandomAccessFile raf = new RandomAccessFile(fi, "r");

				byte[] characterFileData = new byte[(int) raf.length()];
				raf.readFully(characterFileData);

				ByteBuffer charData = ByteBuffer.wrap(characterFileData);
				charData.position(0);
				FileIO.f.position(Character.characters[selected].offset);
				FileIO.f = charData;
				/*
				 * Used for checking if there were ANY differences between the
				 * two files Only used for debugging purposes and is not needed.
				 * String diffs = ""; int place = 0; System.out.println(
				 * "Starting file comparison"); while(charData.hasRemaining()){
				 * byte b1 = charData.get(); byte b2 = f.get(); if(b1!=b2){
				 * diffs += ("@"+place+":"+b1+":"+b2+"\n"); } place ++; }
				 * System.out.println("Finished file comparison");
				 * FileIO.init(); charData.position(0); System.out.println(
				 * "Starting to write debug"); Options.writeDebug(diffs);
				 * System.out.println("Finished writing debug");
				 */

				FileIO.loadedISOFile.reload();
				FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), charData.array());

				FileIO.loadedISOFile.close();
				raf.close();
				// isoFileSystem.clearCachedISOFiles();

				FileIO.loadedISOFile.reload();
				FileIO.init();

			} catch (Exception e) {
			}
		}
	}

}
