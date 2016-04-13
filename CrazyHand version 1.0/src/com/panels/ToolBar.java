package com.panels;

import com.Character;
import com.FileIO;
import com.MeleeEdit;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JToolBar{
	
	
	public static String[] options = {
			"Attributes","Character specific Attributes",
			"Subactions (Common)", "Subactions (All)",
			"Projectiles", "Animation Swapping", "Frame Speed Modifiers",
			"Player Common Values (PlCo)", "Move Logic", "Other",
			
		};
	
	public JButton saveButton;

	public JComboBox charList;
	public JComboBox specialList;
	public JComboBox optionList;
	

	
	public ToolBar(){
		saveButton = new JButton("Save Changes");
		//saveButton.setActionCommand("save");
		saveButton.addActionListener(new SaveListener());
		saveButton.setPreferredSize(new Dimension(250, 25));
		
		
		String[] tmp = new String[Character.characters.length];
		for (int i = 0; i < tmp.length; i++) {tmp[i] = Character.characters[i].name;}
		charList = new JComboBox(tmp);
		charList.setSelectedIndex(0);
		charList.setEditable(false);
		//ComboBoxRenderer renderer = new ComboBoxRenderer();
		//renderer.setPreferredSize(new Dimension(80,40));
		//charList.setRenderer(renderer);
		charList.setPreferredSize(new Dimension(100,25));
		charList.setMaximumSize(charList.getPreferredSize());
		charList.addActionListener(new CharListener());
		//charList.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(Box.createHorizontalStrut(20));
		add(charList);
        add(Box.createHorizontalStrut(25));
		add(saveButton);
		add(Box.createHorizontalGlue());
		
		add(new JLabel("There's text over here"));


		//add(optionList);TODO get rid of the optionList combo box. obsolete now
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setFloatable(false);

		

		
	}
	
	public void setButton(boolean b){
		if(b){
			saveButton.setEnabled(true);
			saveButton.setText("Save Changes");
		}
		else{
			saveButton.setEnabled(false);
			saveButton.setText("Saved!");
		}

		MeleeEdit.frame.pack();
	}
	
	

	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
				
		public ComboBoxRenderer() {
			setOpaque(true);
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(CENTER);
		}
		
		public Component getListCellRendererComponent( JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
			
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

	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MeleeEdit.contentPane.save();
			
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						Thread.sleep(1000);//TODO make this not freeze prog
						MeleeEdit.toolBar.setButton(true);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	class CharListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			MeleeEdit.selected = cb.getSelectedIndex();
			
			MeleeEdit.contentPane.softUpdate();
		}
	}

	
	



	
	class OptionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			MeleeEdit.selectedMenu = cb.getSelectedIndex();

			MeleeEdit.contentPane.hardUpdate();
		}
	}
	
	
	
	
	
	//TODO this stuff
	/*default: character, save

	projectiles - --character
	FSM - --character
	PlCo - --character
	move logic --save?
	other --char, --save
	 */

}
