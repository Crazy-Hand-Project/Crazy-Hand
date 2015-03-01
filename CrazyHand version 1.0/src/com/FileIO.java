package com;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;


import static com.MeleeEdit.MENU_ATTACKS;
import static com.MeleeEdit.MENU_SPECIAL_MOVES;
import static com.MeleeEdit.MENU_ATTRIBUTES;
import static com.MeleeEdit.MENU_ALL;
import static com.MeleeEdit.MENU_OTHER;;


public class FileIO {
	//TEST TEST TEST
	
	
	
	public static RandomAccessFile f;
	//TEST!
    public static void init(){
       try {
		f = new RandomAccessFile("root/Pl" + Character.characters[MeleeEdit.selected].id + ".dat", "rw");
       } catch (FileNotFoundException e) {
    	   e.printStackTrace();
       }

       try {
    	   f.seek(Character.characters[MeleeEdit.selected].offset);
       } catch (IOException e) {
    	   e.printStackTrace();
       }

    }
    
    public static float readFloat(){
    	float val=0.9f;
		try {
			val = f.readFloat();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return val;
    }
    public static int readByte(){
    	int  val=0;
		try {
			val = f.readUnsignedByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return val;
    }
    public static void writeByte(int b){
		try {
			f.writeByte(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static int readInt(){
    	int val=0;
		try {
			val = f.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return val;
    }
    public static void setPosition(int pos){
    	try {
			f.seek(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void readScripts(){
    	Script.number=1;
    	Script.scripts.clear();
    	MeleeEdit.scriptInner.removeAll();
    	
    	
    	//fox 6, dash 2e
    	//System.out.println("Start");
    	int offTmp;
    	if(MeleeEdit.selectedMenu==MENU_ATTACKS){
    		offTmp = SubAction.subActions[MeleeEdit.selectedSubaction].offset*6*4;
    	}
    	else if(MeleeEdit.selectedMenu==MENU_SPECIAL_MOVES){
    		offTmp = SpecialMovesList.getListForCharacter(MeleeEdit.selected)[MeleeEdit.selectedSubaction].offset*6*4;
    	}
    	else if(MeleeEdit.selectedMenu==MENU_ALL){
    		offTmp = SubAction.subActionsAll[MeleeEdit.selectedSubaction].offset*6*4;
    	}
    	else{
    		offTmp = 0;//The if/else statements above cover all the possible menus I've worked on, so this is to satisfy errors
    	}
    	
    	int pointerLoc = Character.characters[MeleeEdit.selected].subOffset+0x20 +4*3+offTmp;
    	//SubActionsStart 
    	//	+ (4 bytes)*(3rd position) 
    	//	+ SubActionNumber*(6 sets)*(4 bytes)
    	
    	//System.out.println("Pointer location: " + Integer.toHexString(pointerLoc));
    	setPosition(pointerLoc);
    	int offset = readInt();
    	//System.out.println("Pointer: " + Integer.toHexString(offset));
    	
    	
    	int id;
    	int bytesDown=0;

    	while(true){
    		setPosition(offset+0x20+bytesDown);
        	id = readByte();
        	id &= ~0b1;
        	id &= ~0b10;
        	
        	Event e = Event.getEvent(id);
        	
        	
        	
        	if(e.id==0){
        		if(readByte()==0 && readByte()==0 && readByte()==0){//check three times for three more bytes
        			if(bytesDown==0){
            			Script.scripts.add(new Script("NO DATA FOUND",new int[4],4));
            		}
            		break;
        		}
        		
        	}
        	
        	setPosition(offset+0x20+bytesDown);
        	int[] d = new int[e.length];
        	for(int i = 0; i < e.length; i++){
        		d[i]= readByte();
        	}
        	Script temp;
        	
        	if(1==1){
	        	if(e.id==0x2c){
	        		temp= new HitboxScript(e.name,d,offset+0x20+bytesDown);
	        	}
	        	else if(e.id==0x04 || e.id==0x08){
	        		temp= new TimerScript(e.name,d,offset+0x20+bytesDown);
	        	}
	        	else if(e.id==0xe0){
	        		temp= new SmashChargeScript(e.name,d,offset+0x20+bytesDown);
	        	}
	        	else if(e.id==0x88){
	        		temp= new ThrowScript(e.name, d, offset+0x20+bytesDown);
	        	}
	        	else if(e.id==0x68){
	        		temp= new BodyStateScript(e.name, d, offset+0x20+bytesDown);
	        	}
	        	else{
	        		temp= new Script(e.name,d,offset+0x20+bytesDown);
	        	}
        	
        	}
        	else
        	{
        		temp= new Script(e.name,d,offset+0x20+bytesDown);
        	}
        	
        	Script.scripts.add(temp);
        	Script.number++;
        	
        	//System.out.println(e.name + " " + Integer.toHexString(id));
        	bytesDown+=e.length;
        	

        	

    		
    	}
    	
    	
    	
    	
    	
    	//for(int i = 0; i<20; i++)
    	//	System.out.println(Integer.toHexString(readByte()));
    	
    	MeleeEdit.setScripts();
    }
    
    public static void copy(String source, String dest){//nabbed this function from the interwebs
    	FileChannel inputChannel = null;
    	FileChannel outputChannel = null;
    	try {
    		inputChannel = new FileInputStream(new File(source)).getChannel();
    		outputChannel = new FileOutputStream(new File(dest)).getChannel();
    		outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    		inputChannel.close();
    		outputChannel.close();
    		
    	}catch (IOException e) {

		}
    }

    
    public static void randoTize(){//currently not working, causes crashes with the hitboxes sometimes?
    	int maxIts = Character.characters.length*(SubAction.subActions.length+Attribute.attributes.length);
    	int its=0;
    	
    	
    	int ints[] = {
    			14,34,58,59,60,61,62
    	};
    	int dos[] = {
    			2,6,10,14,15,16,19,20,23,24,25,26,29,34,35,36,42,43,58,59,60,61,62,65,66
        };
    	for(int i = 0; i < Character.characters.length-1; i++){
    		try {
    			f = new RandomAccessFile("root/Pl" + Character.characters[i].id + ".dat", "rw");

    			for(int k = 0 ; k < Attribute.attributes.length; k++){
    				boolean hold = false;
    				for(int p = 0; p < dos.length; p++){
    					if(dos[p]==k)
    						hold=true;
    				}
    				if(Attribute.attributes[k].name != "????" && hold){
    					f.seek(Character.characters[i].offset + k*4);
        				float tmp = f.readFloat();
        				tmp*=randInt(50,200)/100.f;
        				
        				for(int o = 0; o < ints.length; o++){//worst variable name I've ever used
        					if(ints[o]==k){
        						tmp=(int)tmp;
        					}
        				}
        				
        				f.seek(Character.characters[i].offset + k*4);
        				f.writeFloat(tmp);
        				its++;
        				System.out.println(its/(float)maxIts*100 + "%");
    				}	
    			}
    			for(int k = 0; k < SubAction.subActions.length; k++){
    				///MeleeEdit.selected=i;
    				//MeleeEdit.selectedSubaction=k;
    				//MeleeEdit.selectedMenu=1;
    				
    				
    				randoScripts(k,i);
    				
    				for(Script s: Script.scripts){
    					if(s.id==0x2c){
    						s.scramble();
        					s.save();
    					}
    					
    				}
    				its++;
    				System.out.println(its/(float)maxIts*100 + "%");
    			}

    			
    			
    			its++;
				System.out.println(its/(float)maxIts*100 + "%");
    		} catch (IOException e) {
    	    	  e.printStackTrace();
    	    }
    	}
    }
    public static void randoScripts(int sub, int chara){
    	Script.scripts.clear();

    	int offTmp = SubAction.subActions[sub].offset*6*4;
    	int pointerLoc = Character.characters[chara].subOffset+0x20 +4*3+offTmp;

    	setPosition(pointerLoc);
    	int offset = readInt();

    	
    	int id;
    	int bytesDown=0;

    	while(true){
    		setPosition(offset+0x20+bytesDown);
        	id = readByte();
        	id &= ~0b1;
        	id &= ~0b10;
        	
        	Event e = Event.getEvent(id);
        	
        	if(e.id==0){
        		if(bytesDown==0){
        			Script.scripts.add(new Script("NO DATA FOUND",new int[4],4));
        		}
        		break;
        	}
        	
        	setPosition(offset+0x20+bytesDown);
        	int[] d = new int[e.length];
        	for(int i = 0; i < e.length; i++){
        		d[i]= readByte();
        	}
        	Script temp;
        	
        	if(e.id==0x2c){
        		temp= new HitboxScript(e.name,d,offset+0x20+bytesDown);
        	}
        	else if(e.id==0x04 || e.id==0x08){
        		temp= new TimerScript(e.name,d,offset+0x20+bytesDown);
        	}
        	else if(e.id==0xe0){
        		temp= new SmashChargeScript(e.name,d,offset+0x20+bytesDown);
        	}
        	else{
        		temp = new Script(e.name,d,offset+0x20+bytesDown);
        	}
        	Script.scripts.add(temp);
        	

        	bytesDown+=e.length;
        	

        	

    		
    	}
    }
    
    
    static Random rand = new Random();
    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    public static void save(){
    	try {
			f.seek(Character.characters[MeleeEdit.selected].offset);
			for(int i = 0; i < Attribute.attributes.length; i++){
				f.writeFloat((float)MeleeEdit.attributeTable.getValueAt(i,1));
			}
		} catch (IOException e) {
		}
    }
    public static void shuffle(){
    	try {
			f.seek(Character.characters[MeleeEdit.selected+1].offset);
			for(int i = 0; i < Attribute.attributes.length; i++){
				f.writeFloat((float)MeleeEdit.attributeTable.getValueAt(i,1));
			}
		} catch (IOException e) {
		}
    }
}