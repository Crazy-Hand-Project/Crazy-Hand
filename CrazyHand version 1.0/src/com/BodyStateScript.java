package com;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

class BodyStateScript extends Script {
	
	public JComboBox<String> bodyState;

    public BodyStateScript(String n, int[] d, int l){
    	super(n,d,l);
    	
    	this.removeAll();
    	hexField=false;
    	init(n,d,l);
    	
    	String[] tmp = new String[] {"Normal", "Invulnerable", "Intangible" };
    	
        int state = this.setBits(23,31);
        
        
        
        bodyState = new JComboBox(tmp);
        bodyState.setSelectedIndex(state);
        
      //  System.out.println(state);
        
        JPanel tempPanel= new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
        
        this.add(Box.createVerticalStrut(5));
        this.add(tempPanel);
        this.add(Box.createVerticalStrut(5));
        tempPanel= new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
        
        
        this.add(Box.createVerticalStrut(5));
        this.add(tempPanel);
        this.add(Box.createVerticalStrut(5));
        tempPanel= new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
        
        tempPanel.add(new JLabel("Body State: "));
        tempPanel.add(bodyState);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        this.add(Box.createVerticalStrut(5));
        this.add(tempPanel);
        this.add(Box.createVerticalStrut(5));
        
        
        
        
        
        
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

    }
    public static int bit(int num, int bit, int set) {
    	if(set==1){
    		return num | (1 << bit);
    	}
    	else{
    		return num & ~(1 << bit);
    	}

     }
    public static int getBit(int num, int bit) {
    	return (num >> bit) & 1;

     }
    
    public int setBits(int start, int end){
    	int tmp = 0, length=end-start;
    	
    	for(int i = 0; i <= length; i++){
    		tmp = bit(tmp,length-i,getBit(data[(i+start)/8],7-(i+start)%8));
    	}
    	
    	
    	return tmp;
    }
    public void saveBits(int start, int end, int val){
    	int length=end-start;
    	for(int i = 0; i <= length; i++){
    		data[(i+start)/8] = bit(data[(i+start)/8], 7-(i+start)%8, getBit(val,length-i));
    	}
    }
    
    
    public void updateData(){
    	int num = bodyState.getSelectedIndex();
    	//System.out.println(num);
    	
    	this.saveBits(23,31,num);
    	
    }
}
