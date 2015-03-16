package com;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;




public class Script extends JPanel{
	
	public static int number = 1;
	public static ArrayList<Script> scripts = new ArrayList<Script>();
	
	public int[] data;
	public int location;
	public String name;
	public boolean hexField=true;
	public JFormattedTextField hex;
	public int id;
	public JLabel nameTag;
	public JButton extras;
	public JComboBox scriptSwitchBar;
	
	String[] allScripts=new String[Event.events.length];
	
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

			nameTag = new JLabel("[ " + number + " ]  " + name + "(Loc:"+Integer.toHexString(this.location)+")"); 
			nameTag.setFont(new Font("wut", Font.BOLD, 16));
				
		
		
		
		Box b0 = Box.createVerticalBox();
		
		
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
        
        for(int i = 0; i < Event.events.length; i ++){
        	allScripts[i]=Event.events[i].name+"(Size:"+Event.events[i].length+")";
        }
        
        scriptSwitchBar = new JComboBox(allScripts);
        scriptSwitchBar.setSelectedIndex(Event.getEventPlacementFromName(Event.getEvent(this.id).name));
        scriptSwitchBar.addActionListener(bac);
        scriptSwitchBar.setToolTipText("Change the kind of script this is.");
         b.add(scriptSwitchBar);
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
	
	public static int toByte(int val)//Taken from FileIO
	{
		return ((short) (val & 0xff));
	}
	
	//This is still in-progress.
	//Script switching works properly, but if a script is smaller than the one it is replacing
	
	//EX. 74 00 00 00 replacing 88 99 AA BB CC DD EE FF, two "scripts" will be created.
	//74 00 00 00 will replace 88 99 AA BB, and then another entry of CC DD EE FF will be present as the leftover data from the first larger script.
	//In a similar manner, if replacing a smaller script with a larger script, the data from following scripts will be overwritten.
	
	//EX. 74 00 00 00 being replaced by 11 22 33 44 55 66 77 88 will overwrite the next four entries of data(99 AA BB CC)
	//Meaning in its current state, a user can easily make a mistake that could overwrite vital data.
	//A simple size check for scripts could be passed, but that would be somewhat limiting(Scripts would only able to be replaced by smaller/same size scripts)
	//I don't plan to release this in its current state for a major release, but I plan to have it finished in time for one.
	
	public static Script createBaseScript(int loc, int script){
		int[] scriptData = new int[0x4];
		for(int i = 0; i < scriptData.length; i ++)
		{
			scriptData[i]=0;
		}
		Script result = new Script("Whoops, this isn't supposed to happen!", scriptData, 0x74);//Result ALWAYS needs to be overwritten.
		
		Event e = Event.events[script];
		
		scriptData = new int[e.length];
		for(int i = 0; i < scriptData.length; i ++)
		{
			scriptData[i]=0;
		}
		scriptData[0]=e.id;
		
		if (e.id == 0x2c) {
			result = new HitboxScript(e.name, scriptData, loc);
		} else if (e.id == 0x4 || e.id == 0x8) {
			result = new TimerScript(e.name, scriptData, loc);
			result.data[3] = 1;
		} else if (e.id == 0xe0) {
			result = new SmashChargeScript(e.name, scriptData, loc);
		} else if (e.id == 0x88) {
			result = new ThrowScript(e.name, scriptData, loc);
		} else if (e.id == 0x68) {
			result = new BodyStateScript(e.name, scriptData, loc);
		} else {
			scriptData[0]=e.id;
			result = new Script(e.name, scriptData, loc);
		}
		
		result.updateData();
		result.save();
		
			for(int i = 0; i < result.data.length; i ++)
			{
				System.out.println(result.data[i]);
			}
		
		return result;
	}
	
	
	
	
	class ButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(arg0.getSource() instanceof JButton){
				if(arg0.getActionCommand()=="scriptUp"){//Tabbed code is non-debug
						MeleeEdit.changeScripts(getArrayIndexForScriptAtPointer(location), true);
						updateUI();
				}
				else if(arg0.getActionCommand()=="scriptDown"){
					MeleeEdit.changeScripts(getArrayIndexForScriptAtPointer(location), false);
					updateUI();
				}
				else if(arg0.getActionCommand()=="more"){
					
				}
			}
			else if(arg0.getSource() instanceof JComboBox){
				JComboBox cb = (JComboBox) arg0.getSource();
				Script.scripts.set(Script.getArrayIndexForScriptAtPointer(location), createBaseScript(location, cb.getSelectedIndex()));
				MeleeEdit.refreshData();
			}
			
		}
	}
	
}
