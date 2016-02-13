package com.scripts;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.FileIO;


public class SynchronousScript extends Script {
    	
	
    	JFormattedTextField frames = new JFormattedTextField(0);


        public SynchronousScript(String n, int[] d, int l){
        	super(n,d,l);
        	this.removeAll();
        	hexField=false;
        	init(n,d,l);
        	
        	this.add(Box.createVerticalStrut(5));
        	Box b = Box.createHorizontalBox();
        	b.add(new JLabel("Frames: "));
            b.add(frames);
            b.add( Box.createHorizontalGlue() );
            this.add(b);
            
            frames.setValue(data[3]);
        	
       
         
            
        }

        public void updateData(){
        	data[3]=((Number)frames.getValue()).intValue();
        }
    }
