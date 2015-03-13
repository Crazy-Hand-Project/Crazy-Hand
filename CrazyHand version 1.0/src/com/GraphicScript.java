package com;

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


class GraphicScript extends Script {
    	
	
    	JFormattedTextField graphicID,
    			xPos = new JFormattedTextField(0),
    			yPos = new JFormattedTextField(0),
    			xExtent = new JFormattedTextField(0),
    			yExtent = new JFormattedTextField(0),
    			zExtent = new JFormattedTextField(0);

        public GraphicScript(String n, int[] d, int l){
        	super(n,d,l);
        	
        	this.removeAll();
        	hexField=false;
        	init(n,d,l);
        	
        	
        	MaskFormatter formatter = null;
            try {
                formatter = new MaskFormatter("HHHH");
            } catch (java.text.ParseException exc) {
                System.err.println("formatter is bad: " + exc.getMessage());
                System.exit(-1);
            }
            graphicID = new JFormattedTextField(formatter);

        	
            String tmp = Integer.toHexString(this.setBits(32,47));
            while(tmp.length()<4){
            	tmp="0" + tmp;
            }
            graphicID.setValue(tmp);
            xPos.setValue(this.setBits(80,87));
            yPos.setValue(this.setBits(96,103));
            xExtent.setValue(this.setBits(112,119));
            yExtent.setValue(this.setBits(128,135));
            zExtent.setValue(this.setBits(144,151));

            
            
            JPanel tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
    
            
            
            tempPanel.add(new JLabel("Graphic ID: "));
            tempPanel.add(graphicID);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));


            
            //this.add(Box.createVerticalStrut(5));
            //this.add(tempPanel);
            //this.add(Box.createVerticalStrut(5));
            //tempPanel= new JPanel();
            //tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            
            
            tempPanel.add(new JLabel("X Position: "));
            tempPanel.add(xPos);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Y Position: "));
            tempPanel.add(yPos);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            //this.add(Box.createVerticalStrut(5));
            //this.add(tempPanel);
            //this.add(Box.createVerticalStrut(5));
            //tempPanel= new JPanel();
            //tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            tempPanel.add(new JLabel("X Extent: "));
            tempPanel.add(xExtent);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Y Extent: "));
            tempPanel.add(yExtent);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Z Extent: "));
            tempPanel.add(zExtent);
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

        	String dat = (String) (graphicID.getValue());
        	int byte1 = Integer.valueOf(dat.substring(2),16);
        	int byte0 = Integer.valueOf(dat.substring(0,2),16);
        	data[4]=byte0;
        	data[5]=byte1;
            
            //this.saveBits(32,48,((Number)graphicID.getValue()).intValue());
            this.saveBits(80,87,((Number)xPos.getValue()).intValue());
            this.saveBits(96,103,((Number)yPos.getValue()).intValue());
            this.saveBits(112,119,((Number)xExtent.getValue()).intValue());
            this.saveBits(128,135,((Number)yExtent.getValue()).intValue());
            this.saveBits(144,151,((Number)zExtent.getValue()).intValue());

  		
        }
       
    }
