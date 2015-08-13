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


public class GraphicScript extends Script {
    	
	//Just a little note
	//041D is a really neat-looking graphic effect :)
    	JFormattedTextField graphicID,
    			xPos = new JFormattedTextField(0),
    			yPos = new JFormattedTextField(0),
    			zPos = new JFormattedTextField(0),
    			xExtent = new JFormattedTextField(0),
    			yExtent = new JFormattedTextField(0),
    			zExtent = new JFormattedTextField(0),
    			boneAttachment = new JFormattedTextField(0);

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
            while(tmp.length()<4){//makes it a 4-digit number just for appearance
            	tmp="0" + tmp;
            }
            graphicID.setValue(tmp);
            xPos.setValue(this.setBits(96,111));
            yPos.setValue(this.setBits(80,95));
            zPos.setValue(this.setBits(64,79));
            zExtent.setValue(this.setBits(112,127));
            yExtent.setValue(this.setBits(128,143));
            xExtent.setValue(this.setBits(144,159));
            
            boneAttachment.setValue(this.setBits(8, 13));

            
            
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
            
            
            
            tempPanel.add(new JLabel("X Offset: "));
            tempPanel.add(xPos);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Y Offset: "));
            tempPanel.add(yPos);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Z Offset: "));
            tempPanel.add(zPos);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            //this.add(Box.createVerticalStrut(5));
            //this.add(tempPanel);
            //this.add(Box.createVerticalStrut(5));
            //tempPanel= new JPanel();
            //tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            tempPanel.add(new JLabel("X Scatter: "));
            tempPanel.add(xExtent);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Y Scatter: "));
            tempPanel.add(yExtent);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Z Scatter: "));
            tempPanel.add(zExtent);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            this.add(Box.createVerticalStrut(5));
            this.add(tempPanel);
            this.add(Box.createVerticalStrut(5));
            tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            //Temporary
            String bitstring="Bits:";
            int[] bits = new int[8];
            for(int i = 0; i < bits.length; i ++){
            	bits[i]=this.setBits(8+i, 8+i);
            	bitstring+="|"+bits[i];
            }
            
            tempPanel.add(new JLabel(bitstring));
            
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            System.out.println(bitstring);
            
            int UK1 = this.data[2];
            int UK2 = this.data[3];
            int UK3 = this.data[6];
            int UK4 = this.data[7];
            
            if(UK1!=0){
            	System.out.println("UNKNOWN 1 IS NOT 0(="+UK1+")@0x"+Integer.toHexString(location));
            }
            if(UK2!=0){
            	System.out.println("UNKNOWN 2 IS NOT 0(="+UK2+")@0x"+Integer.toHexString(location));
            }
            if(UK3!=0){
            	System.out.println("UNKNOWN 3 IS NOT 0(="+UK3+")@0x"+Integer.toHexString(location));
            }
            if(UK4!=0){
            	System.out.println("UNKNOWN 4 IS NOT 0(="+UK4+")@0x"+Integer.toHexString(location));
            }
            
            
            //
            
            tempPanel.add(new JLabel("Bone attachment: "));
            tempPanel.add(boneAttachment);
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
            

            this.saveBits(96,111,((Number)xPos.getValue()).intValue());
            this.saveBits(80,95,((Number)yPos.getValue()).intValue());
            this.saveBits(64,79,((Number)zPos.getValue()).intValue());
            this.saveBits(144,159,((Number)xExtent.getValue()).intValue());
            this.saveBits(128,143,((Number)yExtent.getValue()).intValue());
            this.saveBits(112,127,((Number)zExtent.getValue()).intValue());
            
            this.saveBits(8, 13, ((Number)boneAttachment.getValue()).intValue());
            
            //this.saveBits(15, 15, 0);
  		
        }
       
    }
