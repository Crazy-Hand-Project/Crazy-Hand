package com;

public class HitboxEffect {
	
	public static HitboxEffect[] hitboxEffect = {
		new HitboxEffect(0x00, "Normal"),
		new HitboxEffect(0x04, "Fire"),
		new HitboxEffect(0x08, "Electric"),
		new HitboxEffect(0x0C, "Slash"),
		new HitboxEffect(0x10, "Coin"),
		new HitboxEffect(0x14, "Ice"),
		new HitboxEffect(0x18, "Sleep"),
		new HitboxEffect(0x1C, "Sleep"),
		new HitboxEffect(0x20, "Grounded"),
		new HitboxEffect(0x24, "Grounded"),
		new HitboxEffect(0x28, "Cape"),
		new HitboxEffect(0x2C, "Empty (doesn't hit)"),
		new HitboxEffect(0x30, "Disabled"),
		new HitboxEffect(0x34, "Darkness"),
		new HitboxEffect(0x38, "Screw Attack"),
		new HitboxEffect(0x3C, "Poison/Flower"),
		new HitboxEffect(0x40, "Nothing (no graphic)"),
	};

	public int id;
	public String name;
	
	public HitboxEffect(int i, String n){
		name = n;
		id = i;
	}
	
	
	public static int getSelect(int id){
		for(int i = 0; i < hitboxEffect.length; i++){
			if(id==hitboxEffect[i].id){
				return i;
			}
		}
		
		return 0;
	}

}
