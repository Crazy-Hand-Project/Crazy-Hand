package com;

public class EventIdentifier {
//TODO so some things say event and some things say script? gosh guys... I mean I know I started this but jesus. wish I wasn't dumb when I started this program
	
	
	public static EventIdentifier[] events = {
    	new EventIdentifier(0x2C, 0x14, "Hitbox"),
    	new EventIdentifier(0x88, 0xC, "Throw"),
		new EventIdentifier(0xE0, 0x8, "Start Smash Charge"),
		new EventIdentifier(0x68, 0x4, "Body State"),
		new EventIdentifier(0xCC, 0x4, "Self-Damage"),
		new EventIdentifier(0x40, 0x4, "Terminate Collisions"),
		new EventIdentifier(0xD0, 0x4, "Continuation control"),
		new EventIdentifier(0x74, 0x4, "Toggle Followup Flag"),
		new EventIdentifier(0x78, 0x4, "Set Followup Flag"),
    	new EventIdentifier(0x5C, 0x4, "Allow Interrupt (IASA)"),
    	new EventIdentifier(0x4C, 0x4, "Action Flag"),
    	new EventIdentifier(0x50, 0x4, "Reverse Direction"),
    	new EventIdentifier(0x04, 0x4, "Synchronous Timer"),
    	new EventIdentifier(0x08, 0x4, "Asynchronous Timer"),
    	new EventIdentifier(0x0C, 0x4, "Set Loop"),
    	new EventIdentifier(0x10, 0x4, "Execute Loop"),
    	new EventIdentifier(0x1C, 0x8, "Subroutine"),
    	new EventIdentifier(0x14, 0x8, "GoTo"),
    	new EventIdentifier(0x18, 0x4, "Return"),
    	new EventIdentifier(0xAC, 0x4, "Rumble"),
    	new EventIdentifier(0x7C, 0x4, "Model mod"),
    	new EventIdentifier(0x28, 0x14, "Graphic Effect"),
    	new EventIdentifier(0x44, 0xC, "Sound Effect"),
    	new EventIdentifier(0xDC, 0xC, "Sound + Graphic effect"),
    	new EventIdentifier(0x48, 0x4, "Random Smash SFX"),
    	new EventIdentifier(0x3C, 0x4, "Terminate Specific Collision"),
    	new EventIdentifier(0x6C, 0x4, "Set All Bones State"),
    	new EventIdentifier(0x70, 0x4, "Set Specific Bone State"),
    	new EventIdentifier(0xE8, 0x10, "Aesthetic wind effect"),
    	new EventIdentifier(0x98, 0x1C, "Psuedo-random Sound Effect"),
    	//new EventIdentifier(0x4e, 0x4, "B button check?"),//01 checks if b button is held down, and if it is not continues the script. See young link's arrow start.
    	new EventIdentifier(0x02, 0x4, "Unknown"),//0x74 for unknown is a placeholder so that if a user selects it there aren't issues
    	
    	//Custom subaction events
    	new EventIdentifier(0xF4,0x10,"Spawn Projectile [Achilles]"),
    	
    };

	public int id, length;
	public String name;
	
	public EventIdentifier(int i, int l, String n){
		id = i;
		length = l;
		name = n;
	}
	
	
	public static EventIdentifier getEventIdentifier(int k){
		for(int i = 0; i < events.length; i++){
			if(k==events[i].id){
				return events[i];
			}
		}
		//System.out.println("SHIIIEEEET SON, THERE BE ERRRS");
		return new EventIdentifier(k, 4, "Unknown");
	}
	
	public static int getEventPlacementFromName(String s){
		for(int i = 0; i < events.length; i ++){
			if(s.equalsIgnoreCase(events[i].name)){
				return i;
			}
		}
		
		System.out.println("Could not find ID for event with name " + s + "!");
		return 0;
	}
	

}
