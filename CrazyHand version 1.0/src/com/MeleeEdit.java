package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.AbstractTableModel;

public class MeleeEdit extends JPanel implements ActionListener {

	public static final int MENU_ATTRIBUTES = 0, MENU_ATTACKS = 1,
			MENU_SPECIAL_MOVES = 20, MENU_ALL = 2, MENU_OTHER = 4,
			MENU_ANIMATION = 3;

	public static int selected = 0, selectedSubaction = 0, selectedMenu = 0;

	public static String[] options = { "Attributes",
			"Subactions (Attacks only)",// "Subactions (Special moves)",
			"Subactions (All)", "Animation Swapping", "Other",
	// "Special Moves",
	// "Frames Speed Modifiers",
	//
	};

	public static JFrame frame;
	public JButton saveButton;
	public static JTable attributeTable;
	public JScrollPane aPane, scripts;
	public JComboBox charList, subactionList;

	public static JComboBox subactionList2;

	public JComboBox specialList;
	public JComboBox optionList;
	public JPanel comboPane, scriptPanel;// ,specialPanel;

	public static RestorePanel restorePane;
	public static AnimationPanel animationPanel;

	public static JPanel scriptInner;

	public MeleeEdit() {
		super(new BorderLayout());

		String[] tmp = new String[Character.characters.length];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = Character.characters[i].name;
		}

		String[] tmp2 = new String[SubAction.subActions.length];
		for (int i = 0; i < tmp2.length; i++) {
			tmp2[i] = SubAction.subActions[i].name;
		}

		charList = new JComboBox(tmp);
		charList.setSelectedIndex(0);
		charList.addActionListener(new CharListener());
		charList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		optionList = new JComboBox(options);
		optionList.setSelectedIndex(0);
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
		comboPane.add(Box.createHorizontalStrut(5));
		comboPane.add(optionList);
		comboPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		saveButton = new JButton("save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(new SaveListener());

		FileIO.init();

		restorePane = new RestorePanel();

		attributeTable = new JTable(new AttributeTable());
		// attributeTable.setPreferredScrollableViewportSize(new Dimension(700,
		// 600));
		attributeTable.setFillsViewportHeight(true);
		attributeTable.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		scriptInner = new JPanel();
		scriptInner.setLayout(new BoxLayout(scriptInner, BoxLayout.PAGE_AXIS));

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

		// specialPanel = new JPanel();

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

	}

	public void refreshSpecialMoves() {
		/*
		 * String[] tmp3 = new
		 * String[SpecialMovesList.getListForCharacter(MeleeEdit
		 * .selected).length]; for(int i = 0; i < tmp3.length; i++){
		 * tmp3[i]=SpecialMovesList
		 * .getListForCharacter(MeleeEdit.selected)[i].getLocalizedName(); }
		 * 
		 * this.selectedSubaction = 0; this.subactionList.setSelectedIndex(0);
		 * 
		 * specialList = new JComboBox(tmp3); specialList.setSelectedIndex(0);
		 * specialList.addActionListener(new SubactionListener());
		 * specialList.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		 * 
		 * 
		 * 
		 * specialPanel.setLayout(new BoxLayout(specialPanel,
		 * BoxLayout.PAGE_AXIS)); specialPanel.removeAll();
		 * specialPanel.add(specialList); specialPanel.add(scripts);
		 */
	}

	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			try {
				FileIO.loadedISOFile.reload();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return;

			}

			if (selectedMenu == MENU_ATTRIBUTES) {
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

			try {
				FileIO.isoFileSystem
						.replaceFile(FileIO.isoFileSystem.getCurrentFile(),
								FileIO.f.array());

			} catch (IOException e2) {
				e2.printStackTrace();
			}

			FileIO.loadedISOFile.close();

			System.out.println("Save Complete!");

		}

	}

	class CharListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			selected = cb.getSelectedIndex();

			if (selectedMenu == MENU_ATTRIBUTES) {
				updateAttributes();
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

	public static void updateSubactions() {
		FileIO.init();
		FileIO.readScripts();

		// updates the "all" subactions list for the new character.
		// I might move this to a function later on. --Ampers
		subactionList2.removeAllItems();
		String[] tmp = FileIO.getDefaultSubactions();
		for (int i = 0; i < tmp.length; i++) {
			subactionList2.addItem(tmp[i]);
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

			removeAll();
			add(comboPane, BorderLayout.PAGE_START);
			add(saveButton, BorderLayout.PAGE_END);

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

				updateSubactions();
			}
			if (selectedMenu == MENU_SPECIAL_MOVES) {
				// scriptPanel.remove(subactionList);
				// scriptPanel.remove(scripts);
				// scriptPanel.remove(subactionList2);

				// refreshSpecialMoves();

				// scriptPanel.add(specialPanel);
				// scriptPanel.add(scripts);

				// add(scriptPanel, BorderLayout.CENTER);

				// //comboPane.add(subactionList);
			}
			if (selectedMenu == MENU_ALL) {

				scriptPanel.remove(subactionList);
				scriptPanel.remove(scripts);
				// scriptPanel.remove(specialPanel);
				scriptPanel.add(subactionList2);
				scriptPanel.add(scripts);

				add(scriptPanel, BorderLayout.CENTER);
				// comboPane.add(subactionList);

				updateSubactions();
			}
			if (selectedMenu == MENU_OTHER) {
				remove(saveButton);
				add(restorePane, BorderLayout.CENTER);
			}
			if (selectedMenu == MENU_ANIMATION) {
				add(animationPanel, BorderLayout.CENTER);

				updateAnimations();
			}

			frame.pack();
			System.out.println("Option Selection Updated");
		}
	}

	public class SubactionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();

			selectedSubaction = cb.getSelectedIndex();

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


				contentPane.setOpaque(true);
				frame.setContentPane(contentPane);

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

	// I won't lie to you guys, this code is a clusterfuck and I'm surprised
	// it's functional
	// given that I did the programming equivalent of throwing fish at a wall
	// until one turned into pasta
	// But it works and it *is* comprehensible, just confusing and probably not
	// optimized.
	public static void changeScripts(int old, boolean movingDown) {

		int n = movingDown ? old : old + 1;

		if (n < 1 || n > Script.scripts.size() - 1) {
			System.out.println("Moving scripts out of bounds! Values: " + old
					+ "," + n);
			return;
		}

		if (movingDown)
			old -= 1;

		Script tmp = Script.scripts.get(old);
		Script tmp2 = Script.scripts.get(n);

		int to = tmp.location;
		int tn = tmp2.location;

		if (Event.getEvent(tmp.id).length > Event.getEvent(tmp2.id).length) {
			if (tmp.location > tmp2.location) {
				to = tn;
				tn = tn + Event.getEvent(tmp.id).length;
			} else {
				tn = to;
				to = tn + Event.getEvent(tmp2.id).length;
			}
		} else if (Event.getEvent(tmp.id).length < Event.getEvent(tmp2.id).length) {
			if (tmp.location > tmp2.location) {
				to = tn;
				tn = to + Event.getEvent(tmp2.id).length;
			} else {
				tn = to;
				to = tn + Event.getEvent(tmp2.id).length;
			}
		} else {
			if (tmp.location > tmp2.location) {
				to = tn;
				tn = to + Event.getEvent(tmp.id).length;
			} else {
				tn = to;
				to = tn + Event.getEvent(tmp.id).length;
			}
		}

		tmp.location = to;
		tmp2.location = tn;

		tmp.updateNametag();
		tmp2.updateNametag();

		tmp.updateData();
		tmp2.updateData();

		if (tmp.location > tmp2.location) {
			Script.scripts.set(old, tmp);
			Script.scripts.set(n, tmp2);
		} else {
			Script.scripts.set(old, tmp2);
			Script.scripts.set(n, tmp);
		}

		tmp = Script.scripts.get(old);
		tmp2 = Script.scripts.get(n);

		FileIO.init();
		for (Script script : Script.scripts) {
			script.save();
		}

		FileIO.init();
		FileIO.readScripts();

		MeleeEdit.scriptInner.updateUI();

	}

	public static void setScripts() {
		int i = -1;

		scriptInner.removeAll();

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

	/**
	 * Refreshes the data values.
	 */
	public static void refreshData() {
		// currently this is only used for when restoring characters to defaults
		// It refreshes the subactions values, etc to reflect the change to
		// default
		FileIO.init();
		FileIO.readScripts();
		FileIO.setPosition(Character.characters[MeleeEdit.selected].offset);
		for (int i = 0; i < Attribute.attributes.length; i++) {
			MeleeEdit.attributeTable.setValueAt(FileIO.readFloat(), i, 1);
		}

	}

	public static void updateTitle(String isoPath) {
		frame.setTitle("Crazy Hand " + Config.VERSION + " [" + isoPath + "]");
	}
}