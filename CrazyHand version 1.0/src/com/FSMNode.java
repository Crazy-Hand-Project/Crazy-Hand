package com;

import java.awt.Dimension;
import java.awt.Font;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

public class FSMNode extends JPanel{
	JComboBox character,action;
	JFormattedTextField value;
	JTextField val;
	public static String[] dummy = { "l", "k", "o"};
	public FSMNode(int[] b, float modifier){
		super();
		character = new JComboBox(FSMPanel.characterNames);
		action = new JComboBox(FSMPanel.actionNames);
		
		
		for(int i = 0; i < FSMPanel.characters.length; i++){
			if(b[0]==FSMPanel.characters[i].value){
				character.setSelectedIndex(i);
			}
		}
		//angle.setValue(this.setBits(96,104));
		int actionID = BitWork.setBits(20,31,b);
		for(int i = 0; i < FSMPanel.actions.length; i++){
			if(actionID==FSMPanel.actions[i].value){
				action.setSelectedIndex(i);
			}
		}

		 value = new JFormattedTextField(new DecimalFormat("##.####"));
		 value.setValue(modifier);
		 value.setPreferredSize(new Dimension(80,5));
		
		
		//JLabel nameTag = new JLabel("boobs"); 
		//nameTag.setFont(new Font("wut", Font.BOLD, 14));
		
		Box  t = Box.createHorizontalBox();
		t.add( Box.createHorizontalGlue() );

        t.add(character);
        
        t.add(Box.createHorizontalStrut(15));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(15));
        
        t.add(action);
       
        t.add(Box.createHorizontalStrut(15));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(15));
        
        t.add(value);

		this.add(t);
		
	}
	
	public void save(){
	}
	
	
	
	
	
	

}
