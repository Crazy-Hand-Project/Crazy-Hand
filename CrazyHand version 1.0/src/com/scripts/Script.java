package com.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

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

import com.Character;
import com.Event;
import com.FileIO;
import com.MeleeEdit;
import com.ScriptEditWindow;




public class Script extends JPanel{
	
	public static int number = 1;
	public static ArrayList<Script> scripts = new ArrayList<Script>();
	
	public int[] data;
	public int location;
	public int arrayPlacement;//For the script editor
	public String name;
	public boolean hexField=true;
	public JFormattedTextField hex;
	public int id;
	public JLabel numTag,offsetTag;
	public JButton extras;
	public JComboBox scriptSwitchBar;
	
	public int subactionOffset;//Not sure if this will be needed yet. Leaving it in for the time being.
	public Character linkedCharacter;//Not sure about this one either. We'll see.
	
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

		numTag = new JLabel(" " + (arrayPlacement+1) + "   ");// + name + "(Loc:"+Integer.toHexString(this.location)+")"); 
		numTag.setFont(new Font("wut", Font.BOLD, 18));
		offsetTag = new JLabel("   Offset: 0x" + Integer.toHexString(this.location));
		offsetTag.setFont(new Font("wut", Font.ITALIC, 12));
				
		
		
		
		Box b0 = Box.createHorizontalBox();
		
		
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
        b.add(numTag);
        b.add( Box.createHorizontalGlue() );
        
        for(int i = 0; i < Event.events.length; i ++){
        	allScripts[i]=Event.events[i].name+"  ["+Event.events[i].length+" bytes]";
        }
        
        scriptSwitchBar = new JComboBox(allScripts);
        scriptSwitchBar.setSelectedIndex(Event.getEventPlacementFromName(Event.getEvent(this.id).name));
        scriptSwitchBar.addActionListener(bac);
        scriptSwitchBar.setToolTipText("Change the kind of script this is.");
        scriptSwitchBar.setFont(new Font("wut", Font.BOLD, 14));
        b.add(scriptSwitchBar);
         
        b.add(offsetTag);
        b.add( Box.createHorizontalGlue() );
        pan.add(b);
        pan.add(b0);
		this.add(pan);
		
		String hexForm = "";
        
        for(int i = 0; i < data.length; i++){
        	hexForm=hexForm + "HH";
        	if(i != data.length-1){
        		hexForm=hexForm + " ";
        	}
        }
        
        MaskFormatter formatter = createHexadecimalMaskFormatter();
        hex = new JFormattedTextField(formatter);
        hex.setValue(tmp);
        
        this.add(Box.createVerticalStrut(5));
        
        //b.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        b = Box.createHorizontalBox();
        
        
        if(hexField){
             b.add(hex);
             b.add( Box.createHorizontalGlue() );
        }
        
        this.add(b);
        if(!hexField){
        	this.remove(b);
        }
        
		this.setBackground(new Color(255,255,255));
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.updateScriptBoxInfo();
	}
	
	public boolean linkedToCharacterFile = true;
	
	public void save(){
		if(linkedToCharacterFile&&linkedCharacter!=null){
			FileIO.init(linkedCharacter.getPlaceInArray());
		}
    	updateData();
    	saveData();
    	FileIO.init();
	}
	public void saveData(){
		
		if(!linkedToCharacterFile){System.out.println("Tried to save an unlinked script!");return;}//Just makes sure that stand-alone scripts aren't saved to any character files that may be open in CrazyHand
		
    	FileIO.setPosition(location);
		for(int i = 0; i < data.length; i++)
			FileIO.writeByte(data[i]);
		
		//try {
		//	FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFileInfo(), FileIO.f.array());
		
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		
    }
	
	public ScriptEditWindow editWindow;
	public Script setInEditor(ScriptEditWindow w){
		editWindow = w;
		return this;
	}
	public Script setStandalone(boolean b){
		this.linkedToCharacterFile = b;
		if(b)
		this.subactionOffset = 0x0;
		return this;
	}
	public Script setLinkedToCharacterSubaction(Character c, int offset){
		this.linkedCharacter = c;
		this.linkedToCharacterFile = true;
		this.subactionOffset = offset;
		return this;
	}
	
	public boolean isScriptInsideLoop;
	
	public void updateScriptBoxInfo(){//Mainly used for scripts inside loops
									  //This function will be added to as new needs arise.
		if(this.isScriptInsideLoop){
			this.setBackground(new Color(0xDDDDEE));
			for(int i = 0; i < this.getComponentCount(); i ++)
			{
				this.getComponent(i).setBackground(new Color(0xD0D0E0));
			}
			this.setToolTipText("This script is part of a loop");
		}
		else{
			this.setBackground(Color.white);
			this.setToolTipText("");
		}
		numTag.setText(" " + (arrayPlacement+1) + "   ");
		offsetTag.setText("   Offset: 0x" + Integer.toHexString(this.location));
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
	
	public static int toByte(int val)//Taken from FileIO
	{
		return ((short) (val & 0xff));
	}
	
	public MaskFormatter createHexadecimalMaskFormatter()
	{
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
        
        return formatter;
	}
	
	@Override
	public String toString(){
		String s = "";//"script:" + this.name;
		//s += "|location:"+location;
		//s +="|data:";
		String dat = hex.getText();
		dat = dat.replaceAll(" ", "");
		if(this.hexField){
			for(int i = 0; i < dat.length(); i ++){
				if(i > 0 && i % 2 == 0){
					s+=".";
				}
				s+= dat.charAt(i);
			}
		}
		s+="&&";
		//System.out.println(s);
		return s;
	}
	
	public ArrayList<Script> getArray(){
		if(editWindow == null){
			return Script.scripts;
		}
		else{
			return editWindow.getCurrentTab().scriptsInTab;
		}
	}
	
	

	
	class ButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(arg0.getSource() instanceof JButton){
				if(arg0.getActionCommand()=="scriptUp"){
						ScriptUtils.changeScripts(arrayPlacement, arrayPlacement-1, getArray());
						updateUI();
						ScriptUtils.updateScripts(getArray());
				}
				else if(arg0.getActionCommand()=="scriptDown"){
					ScriptUtils.changeScripts(arrayPlacement, arrayPlacement+1, getArray());
					updateUI();
					ScriptUtils.updateScripts(getArray());
				}
				else if(arg0.getActionCommand()=="more"){
					
				}
			}
			else if(arg0.getSource() instanceof JComboBox){
				JComboBox cb = (JComboBox) arg0.getSource();
				Script sc = ScriptUtils.createBaseScript(location, cb.getSelectedIndex());
				sc.linkedToCharacterFile = linkedToCharacterFile;
				sc.editWindow = editWindow;
				sc.save();
				getArray().set(ScriptUtils.getArrayIndexForScriptAtPointer(location, getArray()), sc);
				if(linkedToCharacterFile){
					MeleeEdit.refreshData(linkedCharacter.getPlaceInArray(), subactionOffset, getArray());
				}
				
				ScriptUtils.fixScriptsAfterSwap(getArray());
				
				ScriptUtils.updateScripts(getArray());
				//FileIO.readScripts();
				FileIO.init();
				for (Script script : Script.scripts) {
					script.save();
				}

				FileIO.init();
				FileIO.readScripts();
			}
			
		}
	}
	
	
	public void setAsGarbageData(){
		setAsGarbageData(0,data.length);
	}
	//Start and end are specified in case a need for this method to only overwrite part of a script arises in the future.	
	public void setAsGarbageData(int start, int end) {
		for(int i = start; i < end; i ++){
			if(i % 4 == 0){
				data[i]=0xCC;
			}else
				data[i]=0x00;
		}
		System.out.println("Overwriting script at 0x"+Integer.toHexString(location)+" with CC 00 00 00 per 4 bytes");
		saveData();
	}
	
}
