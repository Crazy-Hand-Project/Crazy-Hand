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

import com.ProjectileID;


public class ProjectileScript extends Script {
    	
	//Just a little note
	//041D is a really neat-looking graphic effect :)

    	JFormattedTextField 
    			xPos = new JFormattedTextField(),
    			yPos = new JFormattedTextField(),
    			xVel = new JFormattedTextField(),
    			yVel = new JFormattedTextField(),
    			scale = new JFormattedTextField(),
    			time = new JFormattedTextField();
    	
    	public JComboBox<String> flag,projectile;

        public ProjectileScript(String n, int[] d, int l){
        	super(n,d,l);
        	
        	this.removeAll();
        	hexField=false;
        	init(n,d,l);
        	
        	

        	
        	

        	
            xPos.setValue(convertToFloat(4));
            yPos.setValue(convertToFloat(6));     
            xVel.setValue(convertToFloat(8));
            yVel.setValue(convertToFloat(10));
            
            scale.setValue(convertToFloat(12));
            time.setValue(convertToFloat(14));
            
            projectile = new JComboBox<String>(ProjectileID.projectiles);
            projectile.setSelectedIndex(data[3]);
            
            String[] tmpArray = { "launch projectile", "give projectile to player"};
            flag = new JComboBox<String>(tmpArray);
            flag.setSelectedIndex(data[2]%2);// %2 just as a fail safe

            
            
            JPanel tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
    
            
            
            tempPanel.add(new JLabel("Projectile: "));
            tempPanel.add(projectile);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));

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
            tempPanel.add(new JLabel("Size Scaling: "));
            tempPanel.add(scale);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            
            
            
            this.add(Box.createVerticalStrut(5));
            this.add(tempPanel);
            this.add(Box.createVerticalStrut(5));
            
            
            
            
            tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            

            //tempPanel.add(new JLabel("Flag: "));
            tempPanel.add(flag);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("X Velocity: "));
            tempPanel.add(xVel);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Y Velocity: "));
            tempPanel.add(yVel);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Duration: "));
            tempPanel.add(time);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
          
            
            this.add(Box.createVerticalStrut(5));
            this.add(tempPanel);
            this.add(Box.createVerticalStrut(5));
            
            
            
            
            
            
            this.setBackground(Color.WHITE);
            this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        }
        
        
        //converts the raw data into a float
        public float convertToFloat(int index){
        	
        	//bitshift one byte, then mask it to make sure there's no garbage data, then add the next byte to this.
        	int tmpValue = (data[index]<<8 & 0xff00) + data[index+1];
        	
        	//bitshift two bytes and mask again to clear out any garbage data.
        	//the two least significant bytes will always be 0's due to Achilles' design.
        	tmpValue = (tmpValue << 16) & 0xffff0000;
        	
        	//convert this raw data into a float
        	float val = Float.intBitsToFloat(tmpValue);
        	
        	
        	return val;
        }
        
        //takes a float, converts into bytes, and saves it to the specified index (in the data array).
        public int saveData(int index, float f){
        	int val = Float.floatToIntBits(f);
        	val = val >> 16;
        	data[index+1] = val & 0xff;
        	val = val >> 8;
        	data[index] = val & 0xff;

        	
        	return 0;
        }
        
        public void updateData(){
        	data[1] = 0xff;
        	data[2] = flag.getSelectedIndex();
        	data[3] = projectile.getSelectedIndex();
        	
        	saveData(4,((Number)xPos.getValue()).floatValue());
        	saveData(6,((Number)yPos.getValue()).floatValue());
        	saveData(8,((Number)xVel.getValue()).floatValue());
        	saveData(10,((Number)yVel.getValue()).floatValue());
        	saveData(12,((Number)scale.getValue()).floatValue());
        	saveData(14,((Number)time.getValue()).floatValue());
        	
        	//String dat = (String) (graphicID.getValue());
        	//int byte1 = Integer.valueOf(dat.substring(2),16);
        	//int byte0 = Integer.valueOf(dat.substring(0,2),16);
        	//data[4]=byte0;
        	//data[5]=byte1;
            
        	/*
            this.saveBits(96,111,((Number)xPos.getValue()).intValue());
            this.saveBits(80,95,((Number)yPos.getValue()).intValue());
            this.saveBits(64,79,((Number)zPos.getValue()).intValue());
            this.saveBits(144,159,((Number)xExtent.getValue()).intValue());
            this.saveBits(128,143,((Number)yExtent.getValue()).intValue());
            this.saveBits(112,127,((Number)zExtent.getValue()).intValue());
            
            this.saveBits(7, 13, ((Number)boneAttachment.getValue()).intValue());
            
            //this.saveBits(15, 15, 0);
             
             */
  		
        }
       
    }
