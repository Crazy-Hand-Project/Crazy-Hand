package com;

public class Event {

	public static Event[] events = {
    	new Event(0x2C, 0x14, "Hitbox"),
    	new Event(0x88, 0xC, "Throw"),
		new Event(0xE0, 0x8, "Start Smash Charge"),
		new Event(0x68, 0x4, "Body State"),
		new Event(0xCC, 0x4, "Self-Damage"),
		new Event(0x40, 0x4, "Terminate Collisions"),
		new Event(0xD0, 0x4, "Continuation control?"),
		new Event(0x74, 0x4, "Toggle Followup Flag"),
		new Event(0x78, 0x4, "Set Followup Flag"),
    	new Event(0x5C, 0x4, "Allow Interrupt (IASA)"),
    	new Event(0x4C, 0x4, "Autocancel"),
    	new Event(0x50, 0x4, "Reverse Direction?"),//Formerly Reverse Direction, Then Release Grabbed character, now back to Reverse Direction. 50 00 00 00 seems to do one or the other depending on unknown circumstances.
    	new Event(0x04, 0x4, "Synchronous Timer"),
    	new Event(0x08, 0x4, "Asynchronous Timer"),
    	new Event(0x0C, 0x4, "Set Loop"),
    	new Event(0x10, 0x4, "Execute Loop"),
    	new Event(0x1C, 0x8, "Subroutine"),
    	new Event(0x14, 0x8, "GoTo"),
    	new Event(0x18, 0x4, "Return"),
    	new Event(0xAC, 0x4, "Rumble"),
    	new Event(0x7C, 0x4, "Model mod"),
    	new Event(0x28, 0x14, "Graphic Effect"),
    	new Event(0x44, 0xC, "Sound Effect"),
    	new Event(0xDC, 0xC, "Sound + Graphic effect"),
    	new Event(0x48, 0x4, "Random Smash SFX"),
    	new Event(0x3C, 0x4, "Terminate Specific Collision"),
    	new Event(0x6C, 0x4, "Set All Bones State"),
    	new Event(0x70, 0x4, "Set Specific Bone State"),
    	new Event(0xE8, 0x10, "Aesthetic wind effect"),
    	new Event(0x98, 0x1C, "Psuedo-random Sound Effect"),
    	//new Event(0x4e, 0x4, "B button check?"),//01 checks if b button is held down, and if it is not continues the script. See young link's arrow start.
    	new Event(0x02, 0x4, "Unknown"),//0x74 for unknown is a placeholder so that if a user selects it there aren't issues
    	
    	//Custom subaction events
    	new Event(0xF4,0x10,"Achilles projectile event"),
    	
    };

	public int id, length;
	public String name;
	
	public Event(int i, int l, String n){
		id = i;
		length = l;
		name = n;
	}
	
	
	public static Event getEvent(int k){
		for(int i = 0; i < events.length; i++){
			if(k==events[i].id){
				return events[i];
			}
		}
		//System.out.println("SHIIIEEEET SON, THERE BE ERRRS");
		return new Event(k, 4, "Unknown");
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
