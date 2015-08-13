package com.scripts;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.Event;
import com.FileIO;
import com.MeleeEdit;



//Moved all the utility methods that were starting to pile up in Script to their own class


public class ScriptUtils {
	
	public static void updateScripts(ArrayList<Script> scripts){
		int start = ScriptUtils.getLowestPointerInScriptList(scripts);
		int offset = 0x0;
		int loopScript = 0;
		System.out.println("First script pointer:"+ Integer.toHexString(start));
		for(int i = 0; i < scripts.size(); i ++){
			
			Event e = Event.getEvent(scripts.get(i).id);
			if(e.id == 0x10){
				loopScript=-1;
			}
			else if(e.id == 0x0C){
				loopScript = 1;
			}
			
			scripts.get(i).isScriptInsideLoop=loopScript!=0;
			scripts.get(i).updateScriptBoxInfo();
			
			if(loopScript==-1)loopScript=0;
			
			scripts.get(i).arrayPlacement=i;
			scripts.get(i).location = start + offset;
			scripts.get(i).updateScriptBoxInfo();
			scripts.get(i).updateData();
			offset += Event.getEvent(scripts.get(i).id).length;
		}
		if(scripts.get(0).editWindow != null){
			//scripts.get(0).editWindow.updateScripts();
		}
		System.out.println("Last script pointer:" + Integer.toHexString((start+offset)));
	}
	
	public static int getLowestPointerInScriptList(ArrayList<Script> scripts){
		int result = Integer.MAX_VALUE-1;
		for(int i = 0; i < scripts.size(); i ++){
			if(scripts.get(i).location < result){
				result = scripts.get(i).location;
			}
		}
		
		return result;
	}
	
	public static int getHighestPointerInScriptList(ArrayList<Script> scripts){
		int result = Integer.MIN_VALUE+1;
		for(int i = 0; i < scripts.size(); i ++){
			if(scripts.get(i).location > result){
				result = scripts.get(i).location;
			}
		}
		
		return result;
	}


	public static int getArrayIndexForScriptAtPlace(Script arg0)
	{
		int loc = 0;
		
		ArrayList<Script> scripts = arg0.getArray();
		
		for(int i = 0; i < scripts.size(); i ++)
		{
			if(Script.scripts.get(i)==arg0){
				loc = i;
			}
		}
		
		return loc;
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
		
		ArrayList<Script> effectedScripts = new ArrayList<Script>();
		Event e = Event.events[script];
		curSubStart = getLowestPointerInScriptList(result.getArray());
		 curSubEnd = getHighestPointerInScriptList(result.getArray());
		int differingSize = e.length - Event.getEvent(result.getArray().get(ScriptUtils.getArrayIndexForScriptAtPointer(loc, result.getArray())).id).length;
		
		if(differingSize>0){
			System.out.println("Differing sizes in replace; Script is bigger than the previous one!");
			int space = e.length-Event.getEvent(result.getArray().get(ScriptUtils.getArrayIndexForScriptAtPointer(loc, result.getArray())).id).length;
			int placement = ScriptUtils.getArrayIndexForScriptAtPointer(loc, result.getArray())+1;
			int effectedBytes = 0;
			//While there's still more scripts to find
			while(space > 0){
				Script sc = result.getArray().get(placement);
				space -= Event.getEvent(sc.id).length;
				effectedScripts.add(sc);
				placement ++;
			}
			
			String effects = "";
			for(int i = 0; i < effectedScripts.size(); i ++){
				effects += effectedScripts.get(i).name;
				effects += "(Offset: 0x"+ Integer.toHexString(effectedScripts.get(i).location) +")";
				effects += "\n";
				effectedBytes += Event.getEvent(effectedScripts.get(i).id).length;
				
			}
			
			JOptionPane optionPane = new JOptionPane(
				    JOptionPane.QUESTION_MESSAGE,
				    JOptionPane.OK_CANCEL_OPTION);
			
			
			
			String debugWarning="BE WARNED; If there are four pairs of zeroes in a row (00 00 00 00) within"+"\n"+
					"the subaction's data, Crazy Hand will think that is the end of the subaction."+"\n"+
					"This is a known bug and is being worked on."+"\n";
			
			int res = optionPane.showConfirmDialog(result, e.name + " is " + differingSize + " bytes larger than the script it is replacing!\n"+
				    										"If you choose to continue, the following scripts in this subaction will be overwritten/effected!\n\n"+
				    										effects+"\n"+
				    										"Hit \"OK\" if you wish to continue.", "Warning!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(res!=JOptionPane.OK_OPTION){
				return result.getArray().get(ScriptUtils.getArrayIndexForScriptAtPointer(loc, result.getArray()));
			}
			
			System.out.println("effb:"+effectedBytes);
			for(Script garbage : effectedScripts){
				garbage.setAsGarbageData();
			}
		}
		
		if(differingSize<0){
			System.out.println("Differing sizes in replace; Script is smaller than the previous one!");
			Script replaced = result.getArray().get(ScriptUtils.getArrayIndexForScriptAtPointer(loc,result.getArray()));
			
			int leftoverBytes = Math.abs(differingSize);
			
			System.out.println(Event.getEvent(replaced.id).name+"(size:"+replaced.data.length+") Replaced by:"+e.name+"(size:"+e.length+")"+"Left over:("+leftoverBytes+")");
			replaced.setAsGarbageData();
			
			
			//ScriptUtils.fixScriptsAfterSwap(result.getArray());
		}
		
		System.out.println("EFSCRSIZE:"+effectedScripts.size());
		
		scriptData = new int[e.length];
		for(int i = 0; i < scriptData.length; i ++)
		{
			scriptData[i]=0;
		}
		scriptData[0]=e.id;
		
		if (e.id == 0x2c) {
			result = new HitboxScript(e.name, scriptData, loc);
		} else if (e.id == 0x4 || e.id == 0x8) {
			result = new SynchronousScript(e.name, scriptData, loc);
			result.data[3] = 1;
		} else if (e.id == 0xe0) {
			result = new SmashChargeScript(e.name, scriptData, loc);
		} else if (e.id == 0x88) {
			result = new ThrowScript(e.name, scriptData, loc);
		} else if (e.id == 0x68) {
			result = new BodyStateScript(e.name, scriptData, loc);
		} else if (e.id == 0x44) {
			scriptData[10]=0x7F;
			scriptData[11]=0x40;
			result = new SoundScript(e.name, scriptData, loc);
		}
		else if (e.id == 0xDC){
			scriptData[10]=0x7F;
			scriptData[11]=0x40;
			result = new ComboSFXGFXScript(e.name, scriptData, loc);
		}
		else {
			scriptData[0]=e.id;
			result = new Script(e.name, scriptData, loc);
		}
		//ScriptUtils.fixScriptsAfterSwap(result.getArray());
		return result;
	}
	
	public static int curSubStart;
	public static int curSubEnd;
	
	public static void fixScriptsAfterSwap(ArrayList<Script> scripts){
		FileIO.init();
		for(Script script: scripts){
			script.saveData();
		}
		FileIO.init();
		int bytesDown = curSubStart;
		int id = 0;
		int scriptSize = 0;
		int scriptParsed = 0;
		boolean flag = false;
		Event e=null;
		System.out.println("Checking for zeroes. Start: 0x" + Integer.toHexString(curSubStart) + " End: 0x" + Integer.toHexString(curSubEnd));
		while(bytesDown < curSubEnd){
			
			FileIO.setPosition(bytesDown);
			id = FileIO.readByte();
			id &= ~0b1;
			id &= ~0b10;
			if(scriptParsed>=scriptSize){
				e = Event.getEvent(id);
				scriptParsed=0;
				scriptSize=e.length;
			}
				if (FileIO.readByte() == 0 && FileIO.readByte() == 0 && FileIO.readByte() == 0 && FileIO.readByte() == 0) {// check
																							   // three
																							   // times
																							   // for
																							   // three
																							   // more
																							   // bytes
					scriptParsed+=3;
					System.out.println("Found four zeroes in a script!");
					if((scriptParsed >= scriptSize &&id==0)){
						System.out.println("Zeroes not attached to any script at pointer 0x" + Integer.toHexString(bytesDown+3) + " ID:"+ Integer.toHexString(id));
						FileIO.setPosition(bytesDown+3);
						FileIO.writeByte(0xCC);
						FileIO.writeByte(0x00);
						FileIO.writeByte(0x00);
						FileIO.writeByte(0x00);
						flag = true;
						FileIO.readScripts();
						FileIO.init();
					}
					else
					scriptParsed-=3;
					
				}
			FileIO.setPosition(bytesDown);
			scriptParsed++;
			bytesDown++;
		}
		
		if(flag){
			ScriptUtils.fixScriptsAfterSwap(scripts);
		}
		else
		System.out.println("Finished checking for zeroes at index 0x" + Integer.toHexString(bytesDown));
		
	}
	
	
	public static int getArrayIndexForScriptAtPointer(int p, ArrayList<Script> scripts)
	{
		for(int i = 0; i < scripts.size(); i ++)
		{
			if(scripts.get(i).location==p){
				return i;
			}
		}
		System.out.println("Could not find script at pointer " + p + "!"+System.lineSeparator()+"Some nasty errors might occur :/");
			return -1;
	}
	
	//Optimized script changing, much less confusing now.
		public static void changeScripts(int old, int n, ArrayList<Script>scripts) {
			
			if (n < 0 || n > scripts.size() - 1) {
				System.out.println("Moving scripts out of bounds! Values: " + old
						+ "," + n);
				return;
			}
			Script tmp = scripts.get(old);
			Script tmp2 = scripts.get(n);
			
			if(tmp.getArray() != scripts){
				System.out.println("Error occured while moving scripts! Type:Different arrays" + System.lineSeparator() +
								   ""
						          );
				return;
			}
			
			tmp.arrayPlacement = tmp2.arrayPlacement;
			tmp2.arrayPlacement = tmp.arrayPlacement;

				scripts.set(old, tmp2);
				scripts.set(n, tmp);
			
			
			ScriptUtils.updateScripts(scripts);
			for (Script script : scripts) {
				script.save();
			}
			ScriptUtils.updateScripts(scripts);
			if(scripts.get(0).linkedToCharacterFile){
				FileIO.init(scripts.get(0).linkedCharacter.getPlaceInArray());
				for (Script script : scripts) {
					script.save();
				}
				FileIO.init(scripts.get(0).linkedCharacter.getPlaceInArray());
				FileIO.readScripts(scripts.get(0).getArray());
				if(scripts.get(0).editWindow != null){
					scripts.get(0).editWindow.updateScripts();
				}
				MeleeEdit.scriptInner.updateUI();
			}

		}
	
}
