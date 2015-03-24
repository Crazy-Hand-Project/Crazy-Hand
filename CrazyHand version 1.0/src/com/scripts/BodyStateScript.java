package com.scripts;

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

public class BodyStateScript extends Script {
	
	public JComboBox<String> bodyState;

    public BodyStateScript(String n, int[] d, int l){
    	super(n,d,l);
    	
    	this.removeAll();
    	hexField=false;
    	init(n,d,l);
    	
    	String[] tmp = new String[] {"Normal", "Invulnerable", "Intangible" };
    	
        int state = this.setBits(23,31);
        
        if(state > 2)state=2;
        
        bodyState = new JComboBox(tmp);
        bodyState.setSelectedIndex(state <= 2 ? state : 2);
        
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

    
    
    public void updateData(){
    	int num = bodyState.getSelectedIndex();
    	//System.out.println(num);
    	
    	this.saveBits(23,31,num);
    	
    }
}
