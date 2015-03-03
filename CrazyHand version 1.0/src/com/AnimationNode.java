package com;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimationNode extends JPanel{
	int pointerLocation,pointerValue,pointerValue2,val3,index;
	String subName;
	JComboBox choice;
	public AnimationNode(int pointerLoc, int pointerVal, int pointerVal2, int ind){
		super();
		
		pointerLocation=pointerLoc;
		pointerValue=pointerVal;
		pointerValue2 = pointerVal2;
		index = ind;
		subName = AnimationPanel.defaultNames[ind];
		choice = new JComboBox(AnimationPanel.getNames());
		
		for(int i = 0; i < AnimationPanel.anims.length; i++){
			String[] t = AnimationPanel.anims[i].split(" ");
			if(pointerValue==Integer.parseInt(t[2])){
				choice.setSelectedIndex(i);
			}
			if(i==0)
				System.out.println(pointerValue + " " + Integer.parseInt(t[1]));
		}
		
		
		JPanel t= new JPanel();
        t.setLayout(new BoxLayout(t, BoxLayout.LINE_AXIS));
        t.add(new JLabel(subName + " "));
        t.add(choice);
        t.add(new JLabel("Value: " + Integer.toString(pointerValue)));
		this.add(t);
		
	}
	
	public void save(){
		FileIO.init();
    	FileIO.setPosition(pointerLocation-4);


    	
    	String[] t = AnimationPanel.anims[choice.getSelectedIndex()].split(" ");
    	FileIO.writeInt(Integer.parseInt(t[1]));
    	FileIO.writeInt(Integer.parseInt(t[2]));
    	FileIO.writeInt(Integer.parseInt(t[3]));
    	FileIO.readInt();//dummy
    	FileIO.writeByte(Integer.parseInt(t[4]));
   // 	System.out.println()
    	
	}
	
	
	
	
	
	

}
