package com;

import static com.SpecialMovesList.specialAttributesToCharacterMap;

public class SpecialAttributeIndex {
	
	

	public SpecialAttributeIndex(){
		specialAttributesToCharacterMap.clear();
		specialAttributesToCharacterMap.put(Character.characters[0], bowserSpecialMoveAttributes);
		specialAttributesToCharacterMap.put(Character.characters[1], falconSpecialMoveAttributes);
		specialAttributesToCharacterMap.put(Character.characters[2], doctorMarioSpecialMoveAttributes);
		specialAttributesToCharacterMap.put(Character.characters[3], dkSpecialMoveAttributes);
		specialAttributesToCharacterMap.put(Character.characters[4], falcoSpecialMoveAttributes);
		specialAttributesToCharacterMap.put(Character.characters[6], gnwSpecialMoveAttributes);
		specialAttributesToCharacterMap.put(Character.characters[24], zeldaSpecialMoveAttributes);
	}
	
	//WIP
		public SpecialMoveAttribute[] bowserSpecialMoveAttributes = {

			new SpecialMoveAttribute(0x37CC,"????","").setIsInt(),
			new SpecialMoveAttribute(0x37D0,"????",""),
			new SpecialMoveAttribute(0x37D4,"????",""),
			new SpecialMoveAttribute(0x37D8,"Refueling/spacing in gfx?","","Neutral-B"),
			new SpecialMoveAttribute(0x37DC,"Spacing in fire gfx?","","Neutral-B"),
			new SpecialMoveAttribute(0x37E0,"GFX scaling","Smaller number makes GFX larger","Neutral-B"),
			new SpecialMoveAttribute(0x37E4,"Minimum distance?","","Neutral-B"),
			new SpecialMoveAttribute(0x37E8,"????","","Neutral-B").setIsInt(),
			new SpecialMoveAttribute(0x37EC,"X offset of Fire","","Neutral-B"),
			new SpecialMoveAttribute(0x37F0,"Y offset of Fire","","Neutral-B"),
			new SpecialMoveAttribute(0x37F4,"????","","Neutral-B").setIsInt(),
			new SpecialMoveAttribute(0x40F8,"Maximum lifespan for flames(in frames)","","Neutral-B"),
			new SpecialMoveAttribute(0x40FC,"Minimum lifespan for flames(in frames)","","Neutral-B"),
			new SpecialMoveAttribute(0x4100,"Flame speed?","","Neutral-B"),
			new SpecialMoveAttribute(0x4104,"Horizontal momentum of flames?","","Neutral-B"),
			new SpecialMoveAttribute(0x4108,"Default:(125 degrees)","The minimum angle that a spawned flame can go at","Neutral-B"),
			new SpecialMoveAttribute(0x410C,"Default:(145 degrees)","The maximum angle that a spawned flame can go at","Neutral-B"),

			new SpecialMoveAttribute(0x37F8,"Side B Throw?","Something to do with throwing after grabbing with side B","Side-B"),
			new SpecialMoveAttribute(0x37FC,"????","","Side-B"),
			new SpecialMoveAttribute(0x3800,"????","","Side-B"),
			new SpecialMoveAttribute(0x3804,"????","","Side-B"),
			new SpecialMoveAttribute(0x3808,"????","","Side-B"),
			new SpecialMoveAttribute(0x380C,"????","","Side-B"),
			new SpecialMoveAttribute(0x3810,"Side B grab break out","Higher number breaks out faster","Side-B"),
			new SpecialMoveAttribute(0x3814,"????","","Side-B"),
			new SpecialMoveAttribute(0x3818,"????","","Side-B"),

			new SpecialMoveAttribute(0x381C,"Vertical momentum in air","","Up-B"),
			new SpecialMoveAttribute(0x3820,"Gravity","","Up-B"),
			new SpecialMoveAttribute(0x3824,"????",""),
			new SpecialMoveAttribute(0x3828,"Grounded moving speed","","Up-B"),
			new SpecialMoveAttribute(0x382C,"Momentum preservation on turning?","","Up-B"),
			new SpecialMoveAttribute(0x3830,"Grounded turning speed","","Up-B"),
			new SpecialMoveAttribute(0x3834,"Aerial mobility","","Up-B"),
			new SpecialMoveAttribute(0x3838,"Frames after landing to automatically Up-B again?","","Up-B"),
			new SpecialMoveAttribute(0x383C,"Something dealing with above","Affects how long you stay in shell?","Up-B"),
			new SpecialMoveAttribute(0x3840,"Something dealing with above","High values delay the animation somehow?","Up-B"),
			new SpecialMoveAttribute(0x3844,"Landing lag?","","Up-B"),

			new SpecialMoveAttribute(0x3848,"Vertical aerial momentum preservation on startup?","","Down-B"),
			new SpecialMoveAttribute(0x384C,"Initial aerial vertical momentum","","Down-B"),
			new SpecialMoveAttribute(0x3850,"Distance to the ground before impact","Do NOT set to zero. Trust me.", "Down-B"),
			new SpecialMoveAttribute(0x3854,"????",""),
			new SpecialMoveAttribute(0x3858,"Gravity","Bowser's new gravity while using Down-B", "Down-B"),
			new SpecialMoveAttribute(0x385C,"Descent speed","Positive values send you upwards", "Down-B"),
			new SpecialMoveAttribute(0x3860,"????",""),
			new SpecialMoveAttribute(0x3864,"????",""),
		};
		
		public SpecialMoveAttribute[] falconSpecialMoveAttributes = {
			new SpecialMoveAttribute(0x3904,"Momentum forward","","Neutral-B"),
			new SpecialMoveAttribute(0x3908,"Momentum holding up","","Neutral-B"),
			new SpecialMoveAttribute(0x3930,"Landing lag","","Side-B"),
			new SpecialMoveAttribute(0x3934,"Landing lag(on hit)","","Side-B"),
			new SpecialMoveAttribute(0x3944,"Landing lag","","Up-B"),
			new SpecialMoveAttribute(0x3974,"Ground-lag multiplier","Higher values means less lag","Down-B"),
			new SpecialMoveAttribute(0x3978,"Aerial langing lag multiplier","Higher values means less lag","Down-B"),
			new SpecialMoveAttribute(0x397C,"????","","Down-B"),
		};
		
		public SpecialMoveAttribute[] doctorMarioSpecialMoveAttributes = {
			new SpecialMoveAttribute(0x3BF4,"Initial projectile speed","","Pills"),
			new SpecialMoveAttribute(0x3BF8,"Initial projectile angle(radians)","","Pills"),
			new SpecialMoveAttribute(0x3BFC,"Projectile duration(In frames)","","Pills"),
			new SpecialMoveAttribute(0x3C04,"Projectile bounce height multiplier","","Pills"),
			new SpecialMoveAttribute(0x36C4,"Horizontal momentum from moving left/right","Smaller values means more momentum","Side-B"),
			new SpecialMoveAttribute(0x36C8,"Affects vertical momentum?","","Side-B"),
			new SpecialMoveAttribute(0x36CC,"Vertical momentum","","Side-B"),
			new SpecialMoveAttribute(0x36D0,"Vertical momentum from moving left/right","","Side-B"),
			new SpecialMoveAttribute(0x36D4,"????","","Side-B"),
			new SpecialMoveAttribute(0x36D8,"Prop ID","54 = cape?","Side-B").setIsInt(),
			new SpecialMoveAttribute(0x3728,"Max damage reflectable?","","Side-B").setIsInt(),
			new SpecialMoveAttribute(0x372C,"Reflection bubble X-offset","","Side-B"),
			new SpecialMoveAttribute(0x3730,"Reflection bubble Y-offset","","Side-B"),
			new SpecialMoveAttribute(0x3734,"Reflection bubble Z-offset","","Side-B"),
			new SpecialMoveAttribute(0x3738,"Reflection bubble size","","Side-B"),
			new SpecialMoveAttribute(0x373C,"Reflection damage multiplier","","Side-B"),
			new SpecialMoveAttribute(0x3740,"Reflection speed multiplier","","Side-B"),
			new SpecialMoveAttribute(0x3744,"????","","Side-B"),
			new SpecialMoveAttribute(0x36DC,"Special fall aerial mobility","","Up-B"),
			new SpecialMoveAttribute(0x36E0,"Landing lag(in frames)","","Up-B"),
			new SpecialMoveAttribute(0x36E4,"Control stick threshold to reverse?","","Up-B"),
			new SpecialMoveAttribute(0x36E8,"Control stick threshold to move horizontally?","","Up-B"),
			new SpecialMoveAttribute(0x36EC,"Air control during Up-B","","Up-B"),
			new SpecialMoveAttribute(0x36F0,"Initial horizontal momentum from moving stick forward","","Up-B"),
			new SpecialMoveAttribute(0x36F4,"Gravity during initial Up-B","","Up-B"),
			new SpecialMoveAttribute(0x36F8,"Initial vertical momentum during aerial Up-B","","Up-B"),
			new SpecialMoveAttribute(0x36FC,"Affects ability to rise during grounded tornado","","Down-B"),
			new SpecialMoveAttribute(0x3700,"Horizontal momentum on ground","","Down-B"),
			new SpecialMoveAttribute(0x3704,"Horizontal momentum in the air","","Down-B"),
			new SpecialMoveAttribute(0x3708,"????","","Down-B"),
			new SpecialMoveAttribute(0x370C,"????","","Down-B"),
			new SpecialMoveAttribute(0x3710,"????","","Down-B"),
			new SpecialMoveAttribute(0x3714,"????","","Down-B").setIsInt(),
			new SpecialMoveAttribute(0x3718,"Affects ability to rise from mashing B","","Down-B"),
			new SpecialMoveAttribute(0x371C,"Maximum vertical moment gained from mashing B","","Down-B"),
			new SpecialMoveAttribute(0x3720,"Landing lag?","Non-0 values toggle freefall after usage","Down-B"),
			new SpecialMoveAttribute(0x3724,"????","","Down-B").setIsInt(),
		};
		
		public SpecialMoveAttribute[] dkSpecialMoveAttributes = {
			new SpecialMoveAttribute(0x3B60,"Max arm swings","How many arm swings to fully charge","Neutral-B").setIsInt(),
			new SpecialMoveAttribute(0x3B64,"Damage per arm swing","First arm swing has no effect","Neutral-B").setIsInt(),
			new SpecialMoveAttribute(0x3B68,"Grounded charged punch horizontal velocity","","Neutral-B"),
			new SpecialMoveAttribute(0x3B6C,"Landing lag","","Neutral-B"),
			new SpecialMoveAttribute(0x3B70,"????","","Neutral-B"),
			new SpecialMoveAttribute(0x3B74,"????","","Neutral-B"),
			new SpecialMoveAttribute(0x3B78,"Aerial gravity","","Side-B"),
			new SpecialMoveAttribute(0x3B7C,"????","","Side-B"),
			new SpecialMoveAttribute(0x3B80,"Aerial vertical velocity","","Up-B"),
			new SpecialMoveAttribute(0x3B84,"Aerial gravity","","Up-B"),
			new SpecialMoveAttribute(0x3B88,"Grounded horizontal velocity","","Up-B"),
			new SpecialMoveAttribute(0x3B8C,"Aerial horizontal velocity","","Up-B"),
			new SpecialMoveAttribute(0x3B90,"Grounded mobility","","Up-B"),
			new SpecialMoveAttribute(0x3B94,"Aerial mobility","","Up-B"),
			new SpecialMoveAttribute(0x3B98,"Landing lag","","Up-B"),
			new SpecialMoveAttribute(0x3B9C,"X offset of hitbox 1","","Down-B"),
			new SpecialMoveAttribute(0x3BA0,"X offset of hitbox 2","","Down-B"),
			new SpecialMoveAttribute(0x3BA4,"????","","Down-B"),
		};
		
		public SpecialMoveAttribute[] falcoSpecialMoveAttributes = {
			new SpecialMoveAttribute(0x39C0,"????","","Up-B"),
			new SpecialMoveAttribute(0x39C4,"Aerial momentum preservation on startup?","","Up-B"),
			new SpecialMoveAttribute(0x39C8,"Momentum on startup?","","Up-B"),
			new SpecialMoveAttribute(0x39CC,"Default angle?(radians)","","Up-B"),
			new SpecialMoveAttribute(0x39D0,"How many frames before you stop traveling","Does not affect distance past a certain point","Up-B"),
			new SpecialMoveAttribute(0x39D4,"????","","Up-B").setIsInt(),
			new SpecialMoveAttribute(0x39D8,"Something to do with distance?","","Up-B"),
			new SpecialMoveAttribute(0x39DC,"Speed of travel/distance traveled","","Up-B"),
			new SpecialMoveAttribute(0x39E0,"Max momentum to stop at","","Up-B"),
			new SpecialMoveAttribute(0x39E4,"????","","Up-B"),
			new SpecialMoveAttribute(0x39E8,"Something to do with angle when hitting the ground?","","Up-B"),
			new SpecialMoveAttribute(0x39EC,"How far you go when hitting the ground at an angle","","Up-B"),
			new SpecialMoveAttribute(0x39F0,"????","","Up-B"),
			new SpecialMoveAttribute(0x39F4,"????","","Up-B"),
			new SpecialMoveAttribute(0x39F8,"Landing lag?","","Up-B"),
			
			
			
			new SpecialMoveAttribute(0x3984,"Neutral-B projectile ID","The integer ID for the desired projectile.","Neutral B projectile").setIsInt(),
			new SpecialMoveAttribute(0x3988,"Neutral-B gun ID","The integer ID for what Falco holds while using Neutral B","Neutral B").setIsInt(),
			new SpecialMoveAttribute(0x3F44,"Projectile lifetime?","Blaster shot","Unknown"),
			new SpecialMoveAttribute(0x665B,"Test", "Falco's shadow?").setIsInt(),
			new SpecialMoveAttribute(0x3968,"????","","Unknown"),
			new SpecialMoveAttribute(0x396C,"????","","Unknown"),
			new SpecialMoveAttribute(0x3970,"????","","Unknown"),
			new SpecialMoveAttribute(0x3974,"????","","Unknown"),
			new SpecialMoveAttribute(0x3978,"????","","Unknown").setIsInt(),
			new SpecialMoveAttribute(0x397C,"????","","Unknown"),
			new SpecialMoveAttribute(0x3980,"????","","Unknown").setIsInt(),
			new SpecialMoveAttribute(0x398C,"????","","Unknown"),
			new SpecialMoveAttribute(0x3990,"????","","Unknown"),
			new SpecialMoveAttribute(0x3994,"????","","Unknown"),
			new SpecialMoveAttribute(0x3998,"????","","Unknown"),
			new SpecialMoveAttribute(0x399C,"????","","Unknown"),
			new SpecialMoveAttribute(0x39A0,"????","","Unknown"),
			new SpecialMoveAttribute(0x39A4,"????","","Unknown"),
			new SpecialMoveAttribute(0x39A8,"????","","Unknown"),
			new SpecialMoveAttribute(0x39AC,"????","","Unknown"),
			new SpecialMoveAttribute(0x39B0,"????","","Unknown"),
			new SpecialMoveAttribute(0x39B4,"????","","Unknown"),
			new SpecialMoveAttribute(0x39B8,"????","","Unknown"),
			new SpecialMoveAttribute(0x39BC,"????","","Unknown"),
			new SpecialMoveAttribute(0x39FC,"????","","Unknown"),
			new SpecialMoveAttribute(0x3A00,"????","","Unknown"),
			new SpecialMoveAttribute(0x3A04,"????","","Unknown"),
			new SpecialMoveAttribute(0x3A08,"????","","Unknown"),
			new SpecialMoveAttribute(0x3A0C,"????","","Unknown").setIsInt(),
			new SpecialMoveAttribute(0x3A10,"????","","Unknown"),
			new SpecialMoveAttribute(0x3A14,"????","","Unknown"),
			new SpecialMoveAttribute(0x3A18,"????","","Unknown").setIsInt(),
		};
		
		
		public SpecialMoveAttribute[] gnwSpecialMoveAttributes = {
			new SpecialMoveAttribute(0x37B0,"Frame repeated hit starts from", "","Neutral-B"),
			new SpecialMoveAttribute(0x37B4,"Max number of sausage per rotation", "","Neutral-B"),
			new SpecialMoveAttribute(0x435C,"Size of sausage", "","Neutral-B"),
			new SpecialMoveAttribute(0x439C,"Sausage bounce against wall", "","Neutral-B"),
			new SpecialMoveAttribute(0x43A0,"Duration of sausage(frames)", "","Neutral-B"),
			new SpecialMoveAttribute(0x43A4,"Duration of sausage on ground(frames)", "","Neutral-B"),
			new SpecialMoveAttribute(0x43A8,"Horizontal velocity of sausage 1", "","Neutral-B"),
			new SpecialMoveAttribute(0x43AC,"Vertical velocity of sausage 1", "","Neutral-B"),
			new SpecialMoveAttribute(0x43B0,"Gravity of sausage 1", "","Neutral-B"),
			new SpecialMoveAttribute(0x43B4,"Spin of sausage 1", "","Neutral-B"),
			new SpecialMoveAttribute(0x43B8,"Also spin of sausage 1?", "","Neutral-B"),
		};
		
		//WIP
		public Attribute[] youngLinkAtts = {
			//new SpecialMoveAttribute(0x3724, "Arrow property", "Frames until max arrow angle?")
			
		};
		
		public SpecialMoveAttribute[] zeldaSpecialMoveAttributes = {
			new SpecialMoveAttribute(0x574e, "ss", "You know").setIsInt(),
		};
		
		public static class SpecialMoveAttribute{
			int loc;
			String name;
			String info;
			boolean isInt = false;
			public String associatedMove;
			public SpecialMoveAttribute(int i, String n, String s, String m)
			{
				this.loc=i;
				this.name=n;
				this.info=s;
				this.associatedMove = m;
			}
			
			public SpecialMoveAttribute(int i, String n, String s)
			{
				this(i,n,s,"Undefined");
			}
			
			public SpecialMoveAttribute setIsInt(){
				this.isInt = true;
				return this;
			}
			
		}
}
