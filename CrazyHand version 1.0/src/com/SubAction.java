package com;

public class SubAction {
	public static SubAction[] subActions = {
		new SubAction(0x2e, "Jab 1"),	
		new SubAction(0x2f, "Jab 2"),
		new SubAction(0x30, "Jab 3?"),	
		new SubAction(0x31, "Rapid Jab Start"),	
		new SubAction(0x32, "Rapid Jab Loop"),	
		new SubAction(0x33, "Rapid Jab End"),	
		new SubAction(0x34, "Dash Attack"),	
		new SubAction(0x35, "Forward-Tilt (High)"),	
		new SubAction(0x36, "Forward-Tilt (Mid-High)"),		
		new SubAction(0x37, "Forward-Tilt (Middle)"),	
		new SubAction(0x38, "Forward-Tilt (Mid-Low)"),		
		new SubAction(0x39, "Forward-Tilt (Low)"),		
		new SubAction(0x3a, "Up-Tilt"),		
		new SubAction(0x3b, "Down-Tilt"),		
		new SubAction(0x3c, "Forward-Smash (High)"),	
		new SubAction(0x3d, "Forward-Smash (Mid-High)"),		
		new SubAction(0x3e, "Forward-Smash (Middle)"),
		new SubAction(0x3f, "Forward-Smash (Mid-Low)"),		
		new SubAction(0x40, "Forward-Smash (Low)"),	
		//new SubAction(0x41, "Smash Attack Charge?"),	
		new SubAction(0x42, "Up-Smash"),		
		new SubAction(0x43, "Down-Smash"),		
		new SubAction(0x44, "Neutral-Air"),		
		new SubAction(0x45, "Forward-Air"),		
		new SubAction(0x46, "Back-Air"),	
		new SubAction(0x47, "Up-Air"),		
		new SubAction(0x48, "Down-Air"),
		new SubAction(0xf7,"Forward Throw"),
		new SubAction(0xf8,"Back Throw"),
		new SubAction(0xf9,"Up Throw"),
		new SubAction(0xfa,"Down Throw"),
	};
	
	
	
	public int offset;
	public String name;
	
	public SubAction(int o, String s){
		name=s;
		offset=o;
		
	}
	
	public static int getNum(){//returns total number of subactions for selected character
		return (Character.characters[MeleeEdit.selected].subEnd-Character.characters[MeleeEdit.selected].subOffset)/4/6;
	}
	
	
	public static int getNum(int k){
		return (Character.characters[k].subEnd-Character.characters[k].subOffset)/4/6;
	}
	

}
