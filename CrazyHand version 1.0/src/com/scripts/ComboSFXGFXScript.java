package com.scripts;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;


public class ComboSFXGFXScript extends Script {
    	
	
    	JFormattedTextField soundID,
    	graphicID;

        public ComboSFXGFXScript(String n, int[] d, int l){
        	super(n,d,l);
        	
        	this.removeAll();
        	hexField=false;
        	init(n,d,l);
        	
        	
        	MaskFormatter formatter = null;
            try {
            	 formatter = new MaskFormatter("HHHHH");
            } catch (java.text.ParseException exc) {
                System.err.println("formatter is bad: " + exc.getMessage());
                System.exit(-1);
            }
            soundID = new JFormattedTextField(formatter);

        	
            String tmp = Integer.toHexString(this.setBits(39,63));
            while(tmp.length()<5){//makes it a 5-digit number just for appearance
            	tmp="0" + tmp;
            }
            soundID.setValue(tmp);
            
            
            JPanel tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
    
            
            
            tempPanel.add(new JLabel("Sound ID: "));
            tempPanel.add(soundID);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            try {
           	 formatter = new MaskFormatter("HHHH");
           } catch (java.text.ParseException exc) {
               System.err.println("formatter is bad: " + exc.getMessage());
               System.exit(-1);
           }
            
            graphicID = new JFormattedTextField(formatter);

        	
            tmp = Integer.toHexString(this.setBits(16,31));
            while(tmp.length()<4){//makes it a 4-digit number just for appearance
            	tmp="0" + tmp;
            }
            graphicID.setValue(tmp);
            
            tempPanel.add(new JLabel("Graphic ID: "));
            tempPanel.add(graphicID);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            this.add(Box.createVerticalStrut(5));
            this.add(tempPanel);
            this.add(Box.createVerticalStrut(5));
            //tempPanel= new JPanel();
            //tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            
            String bitstring="Bits:";
            int[] bits = new int[data.length*8];
            for(int i = 0; i < bits.length; i ++){
            	bits[i]=this.setBits(i, i);
            	bitstring+="|"+bits[i];
            }
            
            System.out.println(bitstring);
            
            
            
            
            this.setBackground(Color.WHITE);
            this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        }
        
        
        
        public void updateData(){
        	String dat = (String) (soundID.getValue());

        	data[7]=Integer.valueOf(dat.substring(3),16);
        	data[6]=Integer.valueOf(dat.substring(1,3),16);
        	data[5]=Integer.valueOf(dat.substring(0,1),16);
        	
        	dat = (String) (graphicID.getValue());
        	int byte1 = Integer.valueOf(dat.substring(2),16);
        	int byte0 = Integer.valueOf(dat.substring(0,2),16);
        	data[2]=byte0;
        	data[3]=byte1;
        	
        	//5,6,7
            //this.saveBits(44,63, Integer.parseInt((String)soundID.getValue(), 16));

        }
       
    }
