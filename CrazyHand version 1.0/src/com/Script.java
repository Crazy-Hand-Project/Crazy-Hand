package com;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;




public class Script extends JPanel{
	
	public static int number = 1;
	public static ArrayList<Script> scripts = new ArrayList<Script>();
	
	public int[] data;
	public int location;//will use for replacing
	public String name;
	public boolean hexField=true;
	public JFormattedTextField hex;
	public int id;
	
	public Script(String n, int[] d, int l){
		super();
		
		id = d[0];
    	id &= ~0b1;
    	id &= ~0b10;
		
		init(n,d,l);
		
		
		
	}
	public void init(String n, int[] d, int l){

		data = new int[d.length];
		for(int i = 0; i < d.length; i++)
			data[i] = d[i];
		
		location=l;
		name = n;
		
		String tmp = "";
		for(int i = 0; i < data.length; i++){
			if(data[i] <= 0xF)
				tmp = tmp + "0";
			tmp = tmp + Integer.toHexString(data[i]);
			tmp = tmp + " ";
		}
		
		JLabel nameTag = new JLabel("[ " + number + " ]  " + name); 
		nameTag.setFont(new Font("wut", Font.BOLD, 16));

		
		Box  b = Box.createHorizontalBox();
        b.add(nameTag);
        b.add( Box.createHorizontalGlue() );
        this.add(b);
        
        if(hexField){
        	this.add(Box.createVerticalStrut(5));
        	String hexForm = "";
            
            for(int i = 0; i < data.length; i++){
            	hexForm=hexForm + "HH";
            	if(i != data.length-1){
            		hexForm=hexForm + " ";
            	}
            }
            
            MaskFormatter formatter = null;
            try {
                formatter = new MaskFormatter(hexForm);
            } catch (java.text.ParseException exc) {
                System.err.println("formatter is bad: " + exc.getMessage());
                System.exit(-1);
            }
            hex = new JFormattedTextField(formatter);
            hex.setValue(tmp);
            
            //b.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            
            b = Box.createHorizontalBox();
            b.add(hex);
            b.add( Box.createHorizontalGlue() );
            this.add(b);
        }
        


        
		this.setBackground(new Color(255,255,255));
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		
	}
	public void save(){
    	updateData();
    	saveData();
	}
	public void saveData(){
    	FileIO.setPosition(location);
		for(int i = 0; i < data.length; i++)
			FileIO.writeByte(data[i]);
    }
	
	
	
	
	
	public void updateData(){
		String dat = (String) (hex.getValue());//.intValue();
		//System.out.println(dat);
		String[] dats = dat.split(" ");
		
		for(int i = 0; i < dats.length; i++){
			int tmp = Integer.parseInt(dats[i], 16);
			data[i] = tmp;
		}
		
	}
	public void scramble(){
		
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


}
