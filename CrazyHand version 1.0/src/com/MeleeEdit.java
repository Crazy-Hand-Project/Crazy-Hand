package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import com.SpecialAttributeIndex.SpecialMoveAttribute;
import com.dolManagement.DOLPatch;
import com.dolManagement.DOLPatchManager;
import com.scripts.Script;
import com.scripts.ScriptComparator;
import com.scripts.ScriptUtils;

public class MeleeEdit extends JPanel implements ActionListener {

	public static final int
			MENU_ATTRIBUTES = 0, MENU_SPECIAL_ATTRIBUTES = 1,
			MENU_ATTACKS = 2, MENU_ALL = 3,
			MENU_PROJECTILE_EDIT_CHARACTER = 4,
			MENU_ANIMATION = 5, MENU_FRAME_SPEED_MODIFIERS = 6,
			MENU_OTHER=7;

	public static int selected = 0, selectedSubaction = 0, selectedMenu = 0;

	public static String[] options = {
		"Attributes","Character specific Attributes",
		"Subactions (Attacks only)", "Subactions (All)",
		"Projectiles",
		"Animation Swapping", "Frame Speed Modifiers",
		"Other",
		
	};

	public static JFrame frame, geckoManagerFrame;
	public JButton saveButton;
	public JMenuItem saveCharacterButton, loadCharacterButton, newTabButton;
	public static JTable attributeTable, attributeTable2;
	public JScrollPane aPane, SApane;

	public JScrollPane scripts;
	public JComboBox charList;
	
	public static JMenuBar fileMenu;

	public static JComboBox subactionList;

	public static JComboBox subactionList2;
	
	public static int loggedSubactionSelection=0;

	public JComboBox specialList;
	public JComboBox optionList;
	public JPanel comboPane, scriptPanel;// ,specialPanel;

	public static RestorePanel restorePane;
	public static AnimationPanel animationPanel;
	public static FSMPanel fsmPanel;
	public static ProjectileEditPane projectilePanel;
	public static DOLPatchManager patchManager;
	
	public static ScriptEditWindow scriptEditor = null;

	public static JPanel scriptInner;

	public MeleeEdit() {
		super(new BorderLayout());
		
		SpecialMovesList.load();

		String[] tmp = new String[Character.characters.length];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = ""+i;
		}

		String[] tmp2 = new String[SubAction.subActions.length];
		for (int i = 0; i < tmp2.length; i++) {
			tmp2[i] = SubAction.subActions[i].name;
		}

		charList = new JComboBox(tmp);
		charList.setSelectedIndex(0);
		charList.setEditable(false);
		
		ComboBoxRenderer renderer = new ComboBoxRenderer();
		renderer.setPreferredSize(new Dimension(64,58));
		charList.setRenderer(renderer);
		charList.setPreferredSize(new Dimension(100,70));
		charList.setMaximumSize(charList.getPreferredSize());
		charList.addActionListener(new CharListener());
		charList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		optionList = new JComboBox(options);
		optionList.setSelectedIndex(0);
		
		optionList.setPreferredSize(new Dimension(200, 40));
		optionList.setMaximumSize(optionList.getPreferredSize());
		
		optionList.addActionListener(new OptionListener());
		optionList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		subactionList = new JComboBox(tmp2);
		subactionList.setSelectedIndex(0);
		subactionList.addActionListener(new SubactionListener());
		subactionList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		subactionList2 = new JComboBox(FileIO.getDefaultSubactions());
		subactionList2.setSelectedIndex(0);
		subactionList2.addActionListener(new SubactionListener());
		subactionList2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		comboPane = new JPanel();
		comboPane.setLayout(new BoxLayout(comboPane, BoxLayout.LINE_AXIS));
		comboPane.add(charList);
		comboPane.add(Box.createHorizontalGlue());
		comboPane.add(optionList);
		comboPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		saveButton = new JButton("save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(new SaveListener());

		
		//fsmPanel = new FSMPanel();
		
		FileIO.init();
		
		projectilePanel = new ProjectileEditPane();

		restorePane = new RestorePanel();
		
		patchManager = new DOLPatchManager();

		attributeTable = new JTable(new AttributeTable());
		// attributeTable.setPreferredScrollableViewportSize(new Dimension(700,
		// 600));
		attributeTable.setFillsViewportHeight(true);
		attributeTable.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		attributeTable2 = new JTable(new SpecialAttributeTable());
		attributeTable2.setFillsViewportHeight(true);
		attributeTable2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		SApane = new JScrollPane(attributeTable2);
		SApane.setPreferredSize(new Dimension(700, 500));
		
		scriptInner = new JPanel();
		scriptInner.setLayout(new BoxLayout(scriptInner, BoxLayout.PAGE_AXIS));
		FileIO.init();
		FileIO.readScripts();

		// j.setPreferredSize(new Dimension(300,400);

		animationPanel = new AnimationPanel();

		// Create the scroll pane and add the table to it.
		aPane = new JScrollPane(attributeTable);
		aPane.setPreferredSize(new Dimension(700, 500));

		scripts = new JScrollPane(scriptInner);
		scripts.setPreferredSize(new Dimension(700, 600));
		scripts.getVerticalScrollBar().setUnitIncrement(10);

		scriptPanel = new JPanel();
		scriptPanel.setLayout(new BoxLayout(scriptPanel, BoxLayout.PAGE_AXIS));

		scriptPanel.add(subactionList);
		scriptPanel.add(scripts);
		
		
		if(fileMenu==null){
			fileMenu = new JMenuBar();
			
			JMenu menu = new JMenu("File");
			JMenu runMenu = new JMenu("Run");
			JMenu optionsMenu = new JMenu("Options");
			JMenu isoPatchMenu = new JMenu("ISO patches");
			//Close to complete, but not ready at this time.
			//JMenu subactionEditorMenu = new JMenu("Subaction editor");
			
			fileListener fl = new fileListener();
			
				JMenuItem openButton = new JMenuItem("Open ISO");
					openButton.setActionCommand("openISO");
					openButton.addActionListener(fl);
				JMenuItem closeButton = new JMenuItem("Close");
					closeButton.addActionListener(fl);
					closeButton.setActionCommand("close");
				saveCharacterButton = new JMenuItem("Save character");
					saveCharacterButton.addActionListener(fl);
					saveCharacterButton.setActionCommand("savecharacter");
				loadCharacterButton = new JMenuItem("Load character");
					loadCharacterButton.addActionListener(fl);
					loadCharacterButton.setActionCommand("loadcharacter");
				JMenuItem m = new JMenuItem();
					m.setEnabled(false);
					
					/*
					JMenuItem editorButton = new JMenuItem("Open subaction editor");
						editorButton.setOpaque(false);
						editorButton.setBackground(new Color(0xEBEBEB));
						editorButton.setBorder(BorderFactory.createEmptyBorder());
						editorButton.addActionListener(fl);
						editorButton.setActionCommand("scriptEditWindow");
					 newTabButton = new JMenuItem("Open in subaction editor");
						newTabButton.addActionListener(fl);
						newTabButton.setActionCommand("newSubactionTab");
						newTabButton.setEnabled(false);
					subactionEditorMenu.add(editorButton);
					subactionEditorMenu.add(newTabButton);
				*/
			menu.add(openButton);
			menu.add(saveCharacterButton);
			menu.add(loadCharacterButton);
			menu.add(m);
			menu.add(closeButton);
			
				JMenuItem dolphinButton = new JMenuItem("Run loaded ISO in Dolphin");
				dolphinButton.addActionListener(fl);
				dolphinButton.setActionCommand("runDolphin");
				
			runMenu.add(dolphinButton);
			
			
				optionsMenu.setActionCommand("options");
				optionsMenu.addActionListener(fl);
				
				ArrayList<Field>temp = new ArrayList<Field>();
				Field[] dolpatchclassfields = DOLPatch.class.getFields();
				for(Field field : dolpatchclassfields){
					try {
						if(field.getName().startsWith("dolPatch")&&field.get(null) instanceof DOLPatch){
							temp.add(field);
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				
				JMenuItem managerbtn = new JMenuItem("Gecko code manager");
				managerbtn.setActionCommand("codemgr");
				managerbtn.addActionListener(fl);
				
				isoPatchMenu.add(managerbtn);
				
				if(!temp.isEmpty()){
					System.out.println("Fields:"+temp.size());
					for(Field fld : temp){
						try {
							DOLPatch patch = (DOLPatch)fld.get(null);
							JMenuItem patchMenuItem = new JMenuItem(patch.name);
							patchMenuItem.setActionCommand("patchISO"+fld.getName());
							patchMenuItem.addActionListener(fl);
							
							isoPatchMenu.add(patchMenuItem);
						} catch (IllegalArgumentException
								| IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
				
			fileMenu.add(menu);
			fileMenu.add(Box.createHorizontalStrut(5));
			fileMenu.add(runMenu);
			fileMenu.add(Box.createHorizontalStrut(5));
			
			//Commented out for now since it's still in-progress and I need to push a bugfix release.
			//Nevermind I want to show it off and this patch is fully functional.
			fileMenu.add(isoPatchMenu);
			
			fileMenu.add(Box.createHorizontalStrut(5));
		}

		add(comboPane, BorderLayout.PAGE_START);
		add(aPane, BorderLayout.CENTER);
		add(saveButton, BorderLayout.PAGE_END);
		
		// FileIO.loadedISOFile.close();

		// try {
		// Runtime.getRuntime().exec("cmd.exe /c start");
		// Runtime.getRuntime().exec("GCReEx.exe -x a.iso");
		// System.out.println("ok");
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }
		FileIO.init();
	}
	
	public static void openScriptEditor(){
		scriptEditor = new ScriptEditWindow();
	}
	
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
				
		public ComboBoxRenderer() {
			setOpaque(true);
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(CENTER);
		}
		
		public Component getListCellRendererComponent(
				                    JList list,
				                    Object value,
				                    int index,
				                    boolean isSelected,
				                    boolean cellHasFocus) {
			
			int selectedIndex = Integer.parseInt(((String)value));
			
			if(selectedIndex<0){return this;}
			
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			
			ImageIcon icon = Character.characters[selectedIndex].characterIcon;
			if (icon != null && icon.getImage() != null) {
				setFont(list.getFont());
				setIcon(icon);
			}
			
				return this;
		}
				
	}
	
	class fileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.startsWith("patchISO")){
				try {
					System.out.println(cmd);
					DOLPatch patchSelected = (DOLPatch)DOLPatch.class.getField(cmd.split("patchISO")[1]).get(null);
					if(patchSelected==null){
						System.out.println("Error finding DOL patch " + cmd + "! Entry is null!");
					}
					else{
						JOptionPane optionPane = new JOptionPane(
							    JOptionPane.QUESTION_MESSAGE,
							    JOptionPane.OK_CANCEL_OPTION);
						
						optionPane.setOptions(new String[]{
								"Apply",
								"Remove",
								"Cancel"
						});
						int res = optionPane.showOptionDialog(MeleeEdit.frame, "Would you like to apply this patch, or remove it?", "DOL Patcher", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionPane.getOptions(), optionPane.getOptions()[0]);
						
						//Apply
						if(res==JOptionPane.OK_OPTION){
							patchSelected.applyPatch();
						}
						//Remove
						else if(res==JOptionPane.NO_OPTION){
							patchSelected.undoPatch();
						}
						//Cancel
						else{
							
						}
						
						
					}
				} catch (IllegalArgumentException | IllegalAccessException
						| NoSuchFieldException | SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(cmd=="codemgr"){
				if(geckoManagerFrame==null){
					geckoManagerFrame = new JFrame();
					
					Dimension dim = new Dimension(800,600);
					geckoManagerFrame.setPreferredSize(dim);
					geckoManagerFrame.setMaximumSize(dim);
					geckoManagerFrame.setMinimumSize(dim);
					geckoManagerFrame.setVisible(true);
					
					geckoManagerFrame.setDefaultCloseOperation(geckoManagerFrame.DISPOSE_ON_CLOSE);
					DOLPatchManager pann = new DOLPatchManager();
					geckoManagerFrame.add(pann);
					geckoManagerFrame.setTitle("Gecko code manager");
				}
				else{
					System.out.println("nope");
					geckoManagerFrame.dispose();
					geckoManagerFrame=null;
				}
			}
			else if(e.getActionCommand()=="openISO"){
				FileIO.loadISOFile();
			}
			else if(e.getActionCommand()=="close"){
				Container comp = getParent();
				Container comp2 = null;
				while(comp != null){
					comp2 = comp;
					comp = comp2.getParent();
				}
				if(comp2 instanceof JFrame){
					((JFrame)comp2).dispose();
				}
			}
			else if(e.getActionCommand()=="runDolphin"){
				Options.openDolphin();
			}
			else if(e.getActionCommand()=="savecharacter"){
				FileIO.saveCharacter(selected);
			}
			else if(e.getActionCommand()=="loadcharacter"){
				FileIO.loadCharacter(selected);
			}
			else if(e.getActionCommand()=="scriptEditWindow"){
				if(MeleeEdit.scriptEditor == null){
					MeleeEdit.openScriptEditor();
				}
			}
			else if(e.getActionCommand()=="newSubactionTab"){
				if(MeleeEdit.scriptEditor == null){
					MeleeEdit.openScriptEditor();
				}
				MeleeEdit.scriptEditor.createNewTab(MeleeEdit.selected, MeleeEdit.selectedSubaction, MeleeEdit.selectedSubaction);
				String att = selectedMenu == MENU_ATTACKS ? (String)subactionList.getSelectedItem() : (String)subactionList2.getSelectedItem();
				MeleeEdit.scriptEditor.currentTab = MeleeEdit.scriptEditor.tabs.size()-1;
				MeleeEdit.scriptEditor.getCurrentTab().scriptName = Character.characters[selected].name + ":" + att;
				MeleeEdit.scriptEditor.changeTabs(MeleeEdit.scriptEditor.currentTab);
			}
		}
		
	}

	class helpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//HelpWindow w = new HelpWindow();
			
			//w.show();
			
		}
		
	}

	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			try {
				FileIO.loadedISOFile.reload();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return;

			}

			if (selectedMenu == MENU_ATTRIBUTES || selectedMenu == MENU_SPECIAL_ATTRIBUTES) {
				FileIO.save();
			}
			if (selectedMenu == MENU_ATTACKS || selectedMenu == MENU_ALL) {
				FileIO.init();
				for (Script script : Script.scripts) {
					script.save();
				}

				FileIO.init();
				FileIO.readScripts();
				frame.pack();
				
			}
			if (selectedMenu == MENU_ANIMATION) {
				// FileIO.init();
				for (AnimationNode n : animationPanel.nodes) {
					n.save();
				}
			}
			
			if(selectedMenu == MENU_FRAME_SPEED_MODIFIERS){
				fsmPanel.save();
			}
			
			if(selectedMenu == MENU_PROJECTILE_EDIT_CHARACTER){
				projectilePanel.save();
			}

			try {
				FileIO.isoFileSystem
						.replaceFile(FileIO.isoFileSystem.getCurrentFile(),
								FileIO.f.array());

			} catch (IOException e2) {
				e2.printStackTrace();
			}

			FileIO.loadedISOFile.close();

			System.out.println("Save Complete!");
			
			/*
			// * Ignore this, just an easy way to convert large chunks of hexadecimal bytes into a copy-pastable format for int arrays.
			// * 
				String res = "";
				String hex = "0x";
				hex+= "00 11 22 33 44 ee tt cc";
				res=hex.replaceAll(" ", ",0x");
				System.out.println(res);
			//*/
		}
	}

	class CharListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			selected = cb.getSelectedIndex();

			if (selectedMenu == MENU_ATTRIBUTES) {
				updateAttributes();
			}
			if(selectedMenu == MENU_SPECIAL_ATTRIBUTES){
				if(SpecialMovesList.getSpecialAttributesForCharacter(selected) != null){
					updateSpecialAttributes();
				}
				else
				{
					selectedMenu = MENU_ATTRIBUTES;
					optionList.setSelectedIndex(MENU_ATTRIBUTES);
					updateAttributes();
				}
			}
			if (selectedMenu == MENU_ATTACKS || selectedMenu == MENU_ALL) {
				updateSubactions();
			}
			if (selectedMenu == MENU_ANIMATION) {
				updateAnimations();
			}

			frame.pack();

			FileIO.loadedISOFile.close();
			System.out.println("Character Selection Updated");
		}
	}

	public static void updateAttributes() {
		FileIO.init();
		FileIO.setPosition(Character.characters[MeleeEdit.selected].offset);
		for (int i = 0; i < Attribute.attributes.length; i++) {
			attributeTable.setValueAt(FileIO.readFloat(), i, 1);
		}
	}
	
	public void updateSpecialAttributes() {
		FileIO.init();
		
		//***Remove this line if it causes long load times***
					SpecialMovesList.load();
		//***See SpecialMovesList.load() for more info***
		
		boolean b = false;
		for(int i = 0; i < this.getComponentCount(); i ++) {
			if(this.getComponent(i) == SApane){
				b = true;
			}
		}
		
		if(b)
		this.remove(SApane);
		
		attributeTable2 = new JTable(new SpecialAttributeTable());
		attributeTable2.setFillsViewportHeight(true);
		attributeTable2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		SApane = new JScrollPane(attributeTable2);
		SApane.setPreferredSize(new Dimension(700, 500));
		
		
		this.add(SApane);
	}

	public static void updateSubactions() {
		FileIO.init();
		FileIO.readScripts();

		// updates the "all" subactions list for the new character.
		// I might move this to a function later on. --Ampers
		subactionList2.removeAllItems();
		subactionList.removeAllItems();
		String[] tmp = FileIO.getDefaultSubactions();
		for (int i = 0; i < tmp.length; i++) {
			subactionList2.addItem(tmp[i]);
		}
		for(int i = 0; i < SubAction.subActions.length; i ++)
		{
			subactionList.addItem(SubAction.subActions[i].name);
		}
		SubAction[] moves=SpecialMovesList.getListForCharacter(selected);
		for(int i = 0; i < moves.length; i ++)
		{
			subactionList.addItem(moves[i].name);
		}
	}

	public void updateAnimations() {
		FileIO.init();
		animationPanel.refresh();

		add(animationPanel, BorderLayout.CENTER);
	}

	class OptionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			selectedMenu = cb.getSelectedIndex();
			
			//newTabButton.setEnabled(false);
			//loadSubactionButton.setEnabled(false);
			
			removeAll();
			add(comboPane, BorderLayout.PAGE_START);
			add(saveButton, BorderLayout.PAGE_END);
			
			MeleeEdit.loggedSubactionSelection = MeleeEdit.selectedMenu == MeleeEdit.MENU_ATTACKS ? MeleeEdit.subactionList.getSelectedIndex() : MeleeEdit.subactionList2.getSelectedIndex();
			
			if(selectedMenu == MENU_SPECIAL_ATTRIBUTES) {
				if(SpecialMovesList.getSpecialAttributesForCharacter(selected) != null){
					updateSpecialAttributes();
				}
				else {
					MeleeEdit.selectedMenu = MENU_ATTRIBUTES;
					optionList.setSelectedIndex(MENU_ATTRIBUTES);
					add(aPane, BorderLayout.CENTER);
					updateAttributes();
				}
			}
			
			if (selectedMenu == MENU_ATTRIBUTES) {

				add(aPane, BorderLayout.CENTER);
				// comboPane.remove(subactionList);

				updateAttributes();

			}
			if (selectedMenu == MENU_ATTACKS) {
				scriptPanel.remove(subactionList2);
				scriptPanel.remove(scripts);
				// scriptPanel.remove(specialPanel);
				scriptPanel.add(subactionList);
				scriptPanel.add(scripts);

				add(scriptPanel, BorderLayout.CENTER);
				// comboPane.add(subactionList);
				
				//newTabButton.setEnabled(true);

				updateSubactions();
				
				if(MeleeEdit.loggedSubactionSelection>MeleeEdit.subactionList.getItemCount()){
					MeleeEdit.loggedSubactionSelection = 0;
				}
				
				MeleeEdit.subactionList.setSelectedIndex(MeleeEdit.loggedSubactionSelection);
			}
			
			//TODO Maybe we should move the FSM button to the "other" pane until we can sort it out for universal ISO use?
			if (selectedMenu == MENU_FRAME_SPEED_MODIFIERS) {
				//remove(saveButton);
				
				JOptionPane optionPane = new JOptionPane(
					    JOptionPane.ERROR_MESSAGE,
					    JOptionPane.OK_OPTION);
				
				
				/*
				String debugWarning="BE WARNED; If there are four pairs of zeroes in a row (00 00 00 00) within"+"\n"+
						"the subaction's data, Crazy Hand will think that is the end of the subaction."+"\n"+
						"This is a known bug and is being worked on."+"\n";
				
				int res = optionPane.showConfirmDialog(MeleeEdit.frame, "This feature is ONLY available with an ISO that has the FSM patch applied to it.\n"+
															"Using an ISO without the FSM patch will cause Crazy Hand to crash, and may corrupt some important data on the ISO.\n"+
					    										"Only hit \"OK\" if this ISO has the FSM patch or is a \"20XX Hack Pack\" iso.", "Warning!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				*/
				
				boolean flag = true;
				
				//Check if FSM engine is installed
				FileIO.initDOL();
				String code = "7F63DB788BE3006C3FA0804563BD30841FFF0E907FFDFA14809F00008BFF00082C0400134182001C2C04001240A200202C1F000140A2001838800013480000102C1F000140A2000838800012C03E0894FC20081ED822000080A2000480C3007080E3007460E780003FE0804063FFB9A887BF00082C1D00004182006057BC463E2C1C00FF418200147C1C20004182000C418100484BFFFFDC57BC863E7C1C280041A1FFD057BC043E7C1C30004182000C7C1C38004082FFBC839F00042C1CFFFF41820018C03F00043FE0800663FFF1907FE803A64E800021BB6100144BC679B0";
				
				int pointer = 0x4088B0;
				
				FileIO.setPosition(pointer);
				for(int i = 0; i < code.length(); i+=2){
					String tmp = new StringBuilder().append("").append(code.charAt(i)).append(code.charAt(i+1)).toString();
					int byt = Integer.decode("0x" + tmp);
					int fbt = FileIO.readByte();
					
					System.out.println(code.length()-i);
					
					if(fbt!=byt){
						flag=false;
						System.out.println("[FSM check] FSM engine not installed.");
						optionPane.showMessageDialog(MeleeEdit.frame, "FSM engine is not installed on this ISO!");MeleeEdit.selectedMenu=MENU_ATTRIBUTES;
						
						cb.setSelectedIndex(MeleeEdit.MENU_ATTRIBUTES);
						actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.getActionCommand()));
						FileIO.init();
						return;
					}
					
				}
				
				
				if(flag){
					if(fsmPanel==null){
						fsmPanel = new FSMPanel();
					}
					add(fsmPanel, BorderLayout.CENTER);
					fsmPanel.updateUI();
					updateUI();
				}
				else{
					MeleeEdit.selectedMenu=MENU_ATTRIBUTES;
					cb.setSelectedIndex(MeleeEdit.MENU_ATTRIBUTES);
					actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.getActionCommand()));
				}
			}
			if (selectedMenu == MENU_ALL) {
				scriptPanel.remove(subactionList);
				scriptPanel.remove(scripts);
				// scriptPanel.remove(specialPanel);
				scriptPanel.add(subactionList2);
				scriptPanel.add(scripts);

				add(scriptPanel, BorderLayout.CENTER);
				// comboPane.add(subactionList);
				
				//newTabButton.setEnabled(true);

				updateSubactions();
				
				if(MeleeEdit.loggedSubactionSelection>MeleeEdit.subactionList2.getItemCount()){
					MeleeEdit.loggedSubactionSelection = 0;
				}
				
				MeleeEdit.subactionList2.setSelectedIndex(MeleeEdit.loggedSubactionSelection);
			}
			if (selectedMenu == MENU_OTHER) {
				remove(saveButton);
				//restorePane = new RestorePanel();
				add(restorePane, BorderLayout.CENTER);
			}
			if (selectedMenu == MENU_ANIMATION) {
				add(animationPanel, BorderLayout.CENTER);

				updateAnimations();
			}
			
			if(selectedMenu == MENU_PROJECTILE_EDIT_CHARACTER){
				projectilePanel = new ProjectileEditPane();
				add(projectilePanel);
				//remove(saveButton);
			}
			else{
				//add(saveButton);
			}

			frame.pack();
			System.out.println("Option Selection Updated");
		}
	}

	public class SubactionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();

			selectedSubaction = cb.getSelectedIndex();
			FileIO.init();
			FileIO.readScripts();
		}
	}

	class AttributeTable extends AbstractTableModel {
		public String[] columnNames = { "Attribute", "Value", "Info", };
		public Object[][] data = initGrid();

		public Object[][] initGrid() {
			FileIO.setPosition(Character.characters[MeleeEdit.selected].offset);
			Object[][] tmp = new Object[Attribute.attributes.length][3];
			for (int i = 0; i < Attribute.attributes.length; i++) {
				tmp[i][0] = Attribute.attributes[i].name;
				tmp[i][1] = FileIO.readFloat();
				if (Attribute.attributes[i].name.equals("????"))
					tmp[i][2] = "Don't modify.";
				else
					tmp[i][2] = Attribute.attributes[i].info;
			}
			return tmp;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			if (col == 1)
				return true;
			else
				return false;
		}

		public void setValueAt(Object value, int row, int col) {
			if (value == null)
				return;
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}
	
	class SpecialAttributeTable extends AbstractTableModel {
		public String[] columnNames = { "Attribute", "Value", "Used for", "Info"};
		public Object[][] data = initGrid();

		public Object[][] initGrid() {
			SpecialMoveAttribute[] list = SpecialMovesList.getSpecialAttributesForCharacter(selected);
			System.out.println("Loading special move attributes");
			Object[][] tmp = new Object[SpecialMovesList.getSpecialAttributesForCharacter(selected).length][4];
			for (int i = 0; i < list.length; i++) {
				tmp[i][0] = list[i].name;

					if(list[i].isInt){
						FileIO.setPosition(list[i].loc);
						tmp[i][1] = Float.parseFloat(""+FileIO.readInt());
					}
					else{
						FileIO.setPosition(list[i].loc);
						tmp[i][1] = FileIO.readFloat();
					}
					
					if (list[i].name.equals("????"))
						tmp[i][3] = "Unsure what this value is.";
					else
						tmp[i][3] = list[i].info;
					
					if(list[i].isInt){
						tmp[i][3] += " (Integer values only!)";
					}
					
					tmp[i][2] = list[i].associatedMove;
					
			}
			return tmp;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			if (col == 1)
				return true;
			else
				return false;
		}

		public void setValueAt(Object value, int row, int col) {
			if (value == null)
				return;
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

	public static void main(String[] args) throws IOException {
		Options.loadOptions();
		FileIO.loadISOFile();
		FileIO.init();
		// FileIO.declareAnims();
		// SpecialMovesList.load();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				frame = new JFrame();
				updateTitle(FileIO.loadedISOFile.getChosenISOFile().getName());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				ImageIcon img = new ImageIcon("img/hand.png");
				frame.setIconImage(img.getImage());

				// Create and set up the content pane.
				MeleeEdit contentPane = new MeleeEdit();

				//
				//

				contentPane.setOpaque(true);
				frame.setContentPane(contentPane);
				frame.setJMenuBar(fileMenu);
				// Display the window.
				frame.pack();
				frame.setVisible(true);

			}
		});
		
		
		Options.saveOptions();
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// not used, but required

	}

	public static void setScripts() {
		int i = -1;

		scriptInner.removeAll();
		
		ScriptUtils.updateScripts(Script.scripts);
		Collections.sort(Script.scripts, new ScriptComparator());

		for (Script script : Script.scripts) {
			i++;

			scriptInner.add(Script.scripts.get(i));

			byte[] tempData = { 4, 53, 6 };
			// j.add(new HitboxScript(tempData));

			JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
			sep.setBackground(Color.BLUE);
			scriptInner.add(sep);
			// JPanel box = Box.createVerticalStrut(10);
			scriptInner.add(Box.createVerticalStrut(30));

			sep = new JSeparator(SwingConstants.HORIZONTAL);
			sep.setBackground(Color.BLUE);
			scriptInner.add(sep);
			// j.add(Box.createVerticalStrut(5));

		}
		frame.pack();
	}
	
	public static void refreshData() {
		refreshData(MeleeEdit.selected, MeleeEdit.selectedSubaction, Script.scripts);
	}

	/**
	 * Refreshes the data values.
	 */
	public static void refreshData(int c, int pointer, ArrayList<Script> scripts) {
		// currently this is only used for when restoring characters to defaults
		// It refreshes the subactions values, etc to reflect the change to
		// default
		FileIO.init(c);
		FileIO.readScripts(scripts);
		FileIO.setPosition(Character.characters[MeleeEdit.selected].offset);
		for (int i = 0; i < Attribute.attributes.length; i++) {
			MeleeEdit.attributeTable.setValueAt(FileIO.readFloat(), i, 1);
		}

	}

	public static void updateTitle(String isoPath) {
		frame.setTitle("Crazy Hand v" + Config.VERSION + " [" + isoPath + "]");
	}
}