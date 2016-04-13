package com;

import javax.swing.ImageIcon;

public class Character {
	
	public static final int
	BOWSER_ID=0,FALCON_ID=1,DRMARIO_ID=2,DK_ID=3,FALCO_ID=4,FOX_ID=5,GAMEWATCH_ID=6,GANON_ID=7,
	POPO_ID=8,NANA_ID=9,JIGGLYPUFF_ID=10,KIRBY_ID=11,LUIGI_ID=12,LINK_ID=13,MARIO_ID=14,MARTH_ID=15,
	MEWTWO_ID=16,NESS_ID=17,PEACH_ID=18,PICHU_ID=19,PIKACHU_ID=20,ROY_ID=21,SAMUS_ID=22,SHEIK_ID=23,
	YOSHI_ID=24,YOUNGLINK_ID=25,ZELDA_ID=26,WIREBOY_ID=27,WIREGIRL_ID=28,GIGABOWSER_ID=29,SANDBAG_ID=30,
	MASTERHAND_ID=31,CRAZYHAND_ID=32,
	OTHER_ID=0xff;//used to handle projectiles not assigned to players.
	
	

	public static ImageIcon otherIcon = new ImageIcon("img/icons/Other.png");
	
	public static Character[] characters = {
		new Character(0x5,"Bowser","Kp",0x3644,0x007b40,0x0098e0),
		new Character(0x0,"Captain Falcon","Ca",0x3774,0x007a98,0x009868),
		new Character(0x16,"Doctor Mario","Dr",0x3540,0x007264,0x008ecc),
		new Character(0x1,"Donkey Kong","Dk",0x39B0,0x007e78,0x009e10),
		new Character(0x14,"Falco","Fc",0x37E4,0x007804,0x0096ac),
		new Character(0x2,"Fox","Fx",0x3714,0x00771c,0x0095c4),
		new Character(0x3,"Game & Watch","Gw",0x3614,0x007c58,0x009aa0),
		new Character(0x19,"Ganondorf","Gn",0x371c,0x0075f0,0x0093c0),
		new Character(0xe,"Ice Climbers (Popo)","Pp",0x32A4,0x006f98,0x008db0),
		new Character(0x9999,"Ice Climbers (Nana)","Nn",0x1188,0x004d30,0x006b48),
		new Character(0xf,"Jigglypuff","Pr",0x38BC,0x006f3c,0x008de4),
		new Character(0x4,"Kirby", "Kb",0x4CE8,0x00b280,0x00df68),
		new Character(0x7,"Luigi","Lg",0x33A0,0x006f40,0x008c80),
		new Character(0x6,"Link","Lk",0x33FC,0x007d90,0x009b00),
		new Character(0x8,"Mario","Mr",0x32D8,0x006f20,0x008b88),
		new Character(0x9,"Marth","Ms",0x3744,0x007cf0,0x009b98),
		new Character(0xa,"Mewtwo","Mt",0x3750,0x0075a0,0x009310),
		new Character(0xb,"Ness","Ns",0x34E0,0x007674,0x009504),
		new Character(0xc,"Peach","Pe",0x3894,0x008680,0x00a450),
		new Character(0x18,"Pichu","Pc",0x3454,0x0075c8,0x0093c8),
		new Character(0xd,"Pikachu","Pk",0x3584,0x0075f4,0x0093f4),
		new Character(0x17,"Roy","Fe",0x389C,0x00800c,0x009eb4),
		new Character(0x10,"Samus","Ss",0x3484,0x0079a8,0x009700),
		new Character(0x13,"Sheik","Sk",0x3418,0x007420,0x0091d8),
		new Character(0x11,"Yoshi","Ys",0x335C,0x007320,0x009090),
		new Character(0x15,"Young Link","Cl",0x35A0,0x00813c,0x009eac),
		new Character(0x12,"Zelda","Zd",0x37F4,0x007cc8,0x0099f0),
		new Character(0x1b,"Wireframe (Male)","Bo",0x31C8,0x006214,0x7DBC),
		new Character(0x1c,"Wireframe (Female)","Gl",0x2FF4,0x006130,0x7CD8),
		new Character(0x1d,"Giga Bowser","Gk",0x34D0,0x007c40,0x000099E0),
		new Character(0x1f,"Sandbag","Sb",0x1464,0x001eb4,0x00003A74),
		new Character(0x1a,"Master Hand","Mh",0x914+0x20,0x00002824,0x0000487C),
		new Character(0x1e,"Crazy Hand","Ch",0x898+0x20,0x00002C40,0x00004C80),
	};

	public String name,id;
	public int offset,subOffset,subEnd,characterID;
	public ImageIcon characterIcon;
	public Character(int cID, String nm, String i, int off, int sub, int sEnd){
		characterID = cID;
		name=nm;
		id=i;
		offset=off;
		subOffset=sub;
		subEnd=sEnd;
		String s = "img/icons/"+name+".png";
		this.characterIcon = new ImageIcon(s);
	}
	//TODO dafuq is this
	public int getPlaceInArray() {
		for(int i = 0; i < Character.characters.length; i ++){
			if(Character.characters[i] == this){
				return i;
			}
		}
		return 0;
	}
	
	
	//TODO this is really inefficient
	public static String getNameFromID(int id){
		for(int i = 0; i < characters.length; i++){
			if(characters[i].characterID == id){
				return characters[i].name;
			}
		}
		
		return "name";
	}
	
	public static int getIndexFromID(int id){
		for(int i = 0; i < characters.length; i++){
			if(characters[i].characterID == id){
				return i;
			}
		}
		
		return -1;
	}
}
