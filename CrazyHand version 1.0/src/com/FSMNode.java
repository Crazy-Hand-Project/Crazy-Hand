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
	JFormattedTextField value,frame;
	JTextField val;
	int[] data;
	public FSMNode(int[] b, float modifier){
		super();
		character = new JComboBox(FSMPanel.characterNames);
		action = new JComboBox(FSMPanel.actionNames);
		
		data = new int[b.length];
		for(int i = 0; i < data.length; i++){
			data[i]=b[i];
		}
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
		 
		 frame = new JFormattedTextField(new DecimalFormat("###"));
		 frame.setValue(b[1]);
		 frame.setPreferredSize(new Dimension(80,5));
		
		
		//JLabel nameTag = new JLabel("boobs"); 
		//nameTag.setFont(new Font("wut", Font.BOLD, 14));
		
		Box  t = Box.createHorizontalBox();
		t.add( Box.createHorizontalGlue() );

        t.add(character);
        
        t.add(Box.createHorizontalStrut(15));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(15));
        
        
        t.add(new JLabel("Starting Frame: "));
        t.add(frame);
       
        t.add(Box.createHorizontalStrut(15));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(15));
        
        t.add(action);
        
        t.add(Box.createHorizontalStrut(15));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(15));

        t.add(new JLabel("Multiplier: "));
        t.add(value);

		this.add(t);
		
	}
	
	public void save(int location){
		//FileIO.writeByte(data[i]);
		data[0] = FSMPanel.characters[character.getSelectedIndex()].value;

		data[1] = ((Number)frame.getValue()).intValue();
		
		
		if(action.getSelectedIndex() !=0)
			BitWork.saveBits(20,31,FSMPanel.actions[action.getSelectedIndex()].value,data);
		
		
		
		FileIO.writeByte(data[0]);
		FileIO.writeByte(data[1]);
		FileIO.writeByte(data[2]);
		FileIO.writeByte(data[3]);
		FileIO.writeFloat(((Number)value.getValue()).floatValue());
		
	}
	
	
	
	
	
	

}
