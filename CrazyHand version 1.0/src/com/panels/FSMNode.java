package com.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.MeleeEdit;

import com.Character;


public class FSMNode extends JPanel{
	
	
	public JComboBox actionBox;
	JButton addButton;
	
	
	public LinkedList<SubNode> subNodes = new LinkedList<SubNode>();
	
	public int actionSubaction;
	
	public boolean active = true;//used to tell if this node should be deleted or not.

	
	public FSMNode(LinkedList<FSMData> modifiers){
		super();
		
		

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		addButton = new JButton("Add");
		addButton.addActionListener(new DeleteListener());
		addButton.setPreferredSize(new Dimension(150,30));
		
		actionBox = new JComboBox(SubactionPanel.getDefaultSubactions(Character.getIndexFromID(modifiers.get(0).charId)));
		actionBox.setSelectedIndex(modifiers.get(0).actionID);
		actionBox.setPreferredSize(new Dimension(150,30));

		//this.add(new JSeparator(SwingConstants.HORIZONTAL));
		//this.add(new JSeparator(SwingConstants.HORIZONTAL));
		Box  mainBox = Box.createHorizontalBox();
		mainBox.add(Box.createHorizontalStrut(30));
		mainBox.add(actionBox);
		mainBox.add(Box.createHorizontalStrut(20));
        mainBox.add(addButton);
        mainBox.add(Box.createHorizontalStrut(30));
		
        //this.add(Box.createVerticalStrut(1));
        //this.add(new JSeparator(SwingConstants.HORIZONTAL));
        
		this.add(mainBox);
		this.add(new JSeparator(SwingConstants.HORIZONTAL));
		add(Box.createVerticalStrut(5));
		
		
		
		for(FSMData d: modifiers){
			subNodes.add(new SubNode(d));
		}
		for(SubNode s: subNodes){
			this.add(s);
		}
		
		//this.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		

		
	}
	

	class SubNode extends JPanel{
		
		JFormattedTextField value,frame;
		JButton deleteButton;
		
		public SubNode(FSMData d){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setPreferredSize(new Dimension(40,30));
			
			 value = new JFormattedTextField(new DecimalFormat("##.####"));
			 value.setValue(d.multiplier);
			 value.setPreferredSize(new Dimension(60,5));
			 
			 frame = new JFormattedTextField(new DecimalFormat("###"));
			 frame.setValue(d.startFrame);
			 frame.setPreferredSize(new Dimension(40,5));
			 
			 deleteButton = new JButton("Remove");
			 deleteButton.addActionListener(new DeleteListener());

			
			Box  t = Box.createHorizontalBox();
			t.add(Box.createHorizontalGlue());
	        
	        int space = 10;
	        
	        t.add(Box.createHorizontalStrut(60));
	        
	        t.add(Box.createHorizontalStrut(space));
	        t.add(new JSeparator(SwingConstants.VERTICAL));
	        t.add(Box.createHorizontalStrut(space));
	        
	        
	        t.add(new JLabel("Starting Frame: "));
	        t.add(frame);
	        
	        
	        t.add(Box.createHorizontalStrut(space));
	        t.add(new JSeparator(SwingConstants.VERTICAL));
	        t.add(Box.createHorizontalStrut(space));

	        t.add(new JLabel("Multiplier: "));
	        t.add(value);
	        
	        t.add(Box.createHorizontalStrut(space));
	        t.add(new JSeparator(SwingConstants.VERTICAL));
	        t.add(Box.createHorizontalStrut(space));
	        
	        t.add(deleteButton);
	        
	        t.add(Box.createHorizontalStrut(60));
	        
	        this.add(t);

	        add(Box.createVerticalStrut(space/2));
			
		}
	}
	
	public void save(int location){
		/*
		//FileIO.writeByte(data[i]);
		data[0] = FSMPanel.characters[character.getSelectedIndex()].value;

		data[1] = ((Number)frame.getValue()).intValue();
		
		
		if(actionBox.getSelectedIndex() !=0){
			if(actionSubaction==0)
				BitWork.saveBits(20,31,FSMPanel.actions[actionBox.getSelectedIndex()].value,data);
			if(actionSubaction==8)
				BitWork.saveBits(20,31,actionBox.getSelectedIndex(),data);
		}
		
		
		System.out.println("po4");
		FileIO.writeByte(data[0]);
		FileIO.writeByte(data[1]);
		FileIO.writeByte(data[2]);
		FileIO.writeByte(data[3]);
		FileIO.writeFloat(((Number)value.getValue()).floatValue());
		System.out.println("po5");
		*/
		
	}
	
	class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			active=false;
			MeleeEdit.fsmPanel.update();
			//System.out.println(character.getSelectedIndex());
		}
	}
	public void reconstitute(){
		/*
		
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
		
		*/
	}
	
	


	
	
	
	

}



