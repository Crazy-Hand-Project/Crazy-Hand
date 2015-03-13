package com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
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
	public JLabel nameTag;
	public JButton extras;
	
	public Script(String n, int[] d, int l){
		super();
		
		id = d[0];
    	id &= ~0b1;
    	id &= ~0b10;
		
    	//Default size for unidentified/small scripts
    	
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
		nameTag = new JLabel("[ " + number + " ]  " + name + "(Loc:"+this.location+")"); 
		nameTag.setFont(new Font("wut", Font.BOLD, 16));
		
		Box b0 = Box.createVerticalBox();
		ImageIcon img = new ImageIcon("img/hand.png");
		
		JButton upButton = new JButton("^");
		upButton.setToolTipText("Move this script up");
		upButton.setActionCommand("scriptUp");
		
		JButton downButton = new JButton("v");
		downButton.setToolTipText("Move this script down");
		downButton.setActionCommand("scriptDown");
		
		ButtonActionListener bac = new ButtonActionListener();
		
		upButton.addActionListener(bac);
		downButton.addActionListener(bac);
		
		b0.add(upButton);
		b0.add(downButton);
		
        JPanel pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
		Box  b = Box.createHorizontalBox();
        b.add(nameTag);
        b.add( Box.createHorizontalGlue() );
        pan.add(b);
        pan.add(b0);
		this.add(pan);
		
        
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
            
            //For scripts larger than 0x4 but without a special script box of their own, such as SFX
          // this.scriptSize = (hex.getText().length()+1)/2;
            
        }
		this.setBackground(new Color(255,255,255));
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
	}
	public void save(){
    	updateData();
    	saveData();
	}
	public void saveData(){
    	FileIO.setPosition(location);
		for(int i = 0; i < data.length; i++)
			FileIO.writeByte(data[i]);
		
		//try {
		//	FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFileInfo(), FileIO.f.array());
		
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		
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
    
    public JMenu dropDownMenu;
    public JButton upButton;
    
	public void updateNametag() {
		//this.nameTag.setName(this.nameTag.getName().split("(Loc:")[0]+this.location+")");
	}
	
	public static int getArrayIndexForScriptAtPointer(int p)
	{
		for(int i = 0; i < Script.scripts.size(); i ++)
		{
			if(Script.scripts.get(i).location==p){
				return i;
			}
		}
		System.out.println("Could not find script at pointer " + p + "!"+System.lineSeparator()+"Some nasty errors might occur :/");
			return -1;
	}
	
	
	
	
	class ButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getActionCommand()=="scriptUp"){//Tabbed code is non-debug
					MeleeEdit.changeScripts(getArrayIndexForScriptAtPointer(location), true);
					updateUI();
			}
			else if(arg0.getActionCommand()=="scriptDown"){
				MeleeEdit.changeScripts(getArrayIndexForScriptAtPointer(location), false);
				updateUI();
			}
			
		}
		
	}
	
	
}
