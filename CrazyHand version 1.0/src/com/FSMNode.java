package com;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import com.scripts.Script;
import com.scripts.ScriptUtils;

public class FSMNode extends JPanel implements Comparable<FSMNode>{
	public JComboBox character,actionBox;
	JFormattedTextField value,frame;
	JTextField val;
	JButton deleteButton;
	public int actionSubaction;
	
	public boolean active = true;//used to tell if this node should be deleted or not.
	
	int[] data;
	
	public FSMNode(int[] b, float modifier){
		super();
		character = new JComboBox(FSMPanel.characterNames);
		character.addActionListener(new CharListener());
		actionBox = new JComboBox(FSMPanel.actionNames);
		
		
		
		
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteListener());

		
		data = new int[b.length];
		for(int i = 0; i < data.length; i++){
			data[i]=b[i];
		}
		for(int i = 0; i < FSMPanel.characters.length; i++){
			if(b[0]==FSMPanel.characters[i].value){
				character.setSelectedIndex(i);
			}
		}
		
		actionSubaction = BitWork.setBits(16,19,b);
		if(actionSubaction==0){
			int actionID = BitWork.setBits(20,31,b);
			for(int i = 0; i < FSMPanel.actions.length; i++){
				if(actionID==FSMPanel.actions[i].value){
					actionBox.setSelectedIndex(i);
				}
			}
		}
		else{
			int id = 0;
			boolean hold = false;
			for(int i = 0; i < Character.characters.length; i++){
				if(b[0] == Character.characters[i].characterID){
					id=i;
					hold=true;
				}
				System.out.println(hold);
			}
			String[] tmp = FileIO.getDefaultSubactions(id);
			actionBox.removeAll();
			actionBox=new JComboBox(tmp);
			actionBox.setSelectedIndex(BitWork.setBits(20,31,b));
			
		}
		

		 value = new JFormattedTextField(new DecimalFormat("##.####"));
		 value.setValue(modifier);
		 value.setPreferredSize(new Dimension(60,5));
		 
		 frame = new JFormattedTextField(new DecimalFormat("###"));
		 frame.setValue(b[1]);
		 frame.setPreferredSize(new Dimension(40,5));
		
		
		//JLabel nameTag = new JLabel("boobs"); 
		//nameTag.setFont(new Font("wut", Font.BOLD, 14));
		
		Box  t = Box.createHorizontalBox();
		t.add( Box.createHorizontalGlue() );

        t.add(character);
        
        t.add(Box.createHorizontalStrut(10));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(10));
        
        
        t.add(new JLabel("Starting Frame: "));
        t.add(frame);
       
        t.add(Box.createHorizontalStrut(10));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(10));
        
        t.add(actionBox);
        
        t.add(Box.createHorizontalStrut(10));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(10));

        t.add(new JLabel("Multiplier: "));
        t.add(value);
        
        t.add(Box.createHorizontalStrut(10));
        t.add(new JSeparator(SwingConstants.VERTICAL));
        t.add(Box.createHorizontalStrut(10));
        
        t.add(deleteButton);

		this.add(t);
		
		//this.setPreferredSize(new Dimension(700,40));
		
	}
	
	public void save(int location){
		//FileIO.writeByte(data[i]);
		data[0] = FSMPanel.characters[character.getSelectedIndex()].value;

		data[1] = ((Number)frame.getValue()).intValue();
		
		
		if(actionBox.getSelectedIndex() !=0){
			if(actionSubaction==0)
				BitWork.saveBits(20,31,FSMPanel.actions[actionBox.getSelectedIndex()].value,data);
			if(actionSubaction==8)
				BitWork.saveBits(20,31,actionBox.getSelectedIndex(),data);
		}
		
		
		
		FileIO.writeByte(data[0]);
		FileIO.writeByte(data[1]);
		FileIO.writeByte(data[2]);
		FileIO.writeByte(data[3]);
		FileIO.writeFloat(((Number)value.getValue()).floatValue());
		
	}
	
	class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			active=false;
			MeleeEdit.fsmPanel.update();
			System.out.println(character.getSelectedIndex());
		}
	}
	public void reconstitute(){
		FileIO.loadedISOFile.close();
		
		if(actionSubaction==0)
			return;
		
		
		//System.out.println("YAY");
		int id = 0;
		for(int i = 0; i < Character.characters.length; i++){
			if(character.getSelectedIndex() == Character.characters[i].characterID){
				id=i;
			}
		}
		String[] tmp = FileIO.getDefaultSubactions(id);
		actionBox.removeAllItems();
		for(int i = 0; i < tmp.length; i++){

			actionBox.addItem(tmp[i]);

			
			//System.out.println("WOW" + i);
		}
		actionBox.setSelectedIndex(BitWork.setBits(20,31,data));
		MeleeEdit.frame.pack();

	}
	int dumbVar = 0;
	class CharListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
			
			
			
			if(dumbVar>0){
				MeleeEdit.fsmPanel.update();
				
			}
			dumbVar++;
			System.out.println("update!");
		}
	}
	
	@Override
	 public int compareTo(FSMNode n) {
		 return (this.character.getSelectedIndex()*1000000+this.actionBox.getSelectedIndex()*1000+((Number)n.frame.getValue()).intValue()) - (n.character.getSelectedIndex()*1000000+n.actionBox.getSelectedIndex()*1000+((Number)this.frame.getValue()).intValue());

	  }


	
	
	
	

}
