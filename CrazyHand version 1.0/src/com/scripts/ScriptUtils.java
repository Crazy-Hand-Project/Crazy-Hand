package com.scripts;

import java.util.ArrayList;

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
			result = new SynchronousScript(e.name, scriptData, loc);
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
		
			for(int i = 0; i < result.data.length; i ++)
			{
				System.out.println(result.data[i]);
			}
		
		return result;
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
				FileIO.init(scripts.get(0).linkedCharacter.getPlaceInArray(), scripts.get(0).subactionOffset);
				for (Script script : scripts) {
					script.save();
				}
				FileIO.init(scripts.get(0).linkedCharacter.getPlaceInArray(), scripts.get(0).subactionOffset);
				FileIO.readScripts(scripts.get(0).getArray());
				if(scripts.get(0).editWindow != null){
					scripts.get(0).editWindow.updateScripts();
				}
				MeleeEdit.scriptInner.updateUI();
			}

		}
	
}
