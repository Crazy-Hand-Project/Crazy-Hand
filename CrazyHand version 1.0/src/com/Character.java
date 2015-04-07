package com;

import javax.swing.ImageIcon;

public class Character {
	
	public static Character[] characters = {
		new Character("Bowser","Kp",0x3644,0x007b40,0x0098e0),
		new Character("Captain Falcon","Ca",0x3774,0x007a98,0x009868),
		new Character("Doctor Mario","Dr",0x3540,0x007264,0x008ecc),
		new Character("Donkey Kong","Dk",0x39B0,0x007e78,0x009e10),
		new Character("Falco","Fc",0x37E4,0x007804,0x0096ac),
		new Character("Fox","Fx",0x3714,0x00771c,0x0095c4),
		new Character("Game & Watch","Gw",0x3614,0x007c58,0x009aa0),
		new Character("Ganondorf","Gn",0x371c,0x0075f0,0x0093c0),
		new Character("Ice Climbers (Popo)","Pp",0x32A4,0x006f98,0x008db0),
		new Character("Ice Climbers (Nana)","Nn",0x1188,0x004d30,0x006b48),
		new Character("Jigglypuff","Pr",0x38BC,0x006f3c,0x008de4),
		new Character("Kirby", "Kb",0x4CE8,0x00b280,0x00df68),
		new Character("Luigi","Lg",0x33A0,0x006f40,0x008c80),
		new Character("Link","Lk",0x33FC,0x007d90,0x009b00),
		new Character("Mario","Mr",0x32D8,0x006f20,0x008b88),
		new Character("Marth","Ms",0x3744,0x007cf0,0x009b98),
		new Character("Mewtwo","Mt",0x3750,0x0075a0,0x009310),
		new Character("Ness","Ns",0x34E0,0x007674,0x009504),
		new Character("Peach","Pe",0x3894,0x008680,0x00a450),
		new Character("Pichu","Pc",0x3454,0x0075c8,0x0093c8),
		new Character("Pikachu","Pk",0x3584,0x0075f4,0x0093f4),
		new Character("Roy","Fe",0x389C,0x00800c,0x009eb4),
		new Character("Samus","Ss",0x3484,0x0079a8,0x009700),
		new Character("Sheik","Sk",0x3418,0x007420,0x0091d8),
		new Character("Yoshi","Ys",0x335C,0x007320,0x009090),
		new Character("Young Link","Cl",0x35A0,0x00813c,0x009eac),
		new Character("Zelda","Zd",0x37F4,0x007cc8,0x0099f0),
		new Character("Wireframe (Male)","Bo",0x31C8,0x006214,0x7DBC),
		new Character("Wireframe (Female)","Gl",0x2FF4,0x006130,0x7CD8),
		new Character("Giga Bowser","Gk",0x34D0,0x007c40,0x000099E0),
		new Character("Sandbag","Sb",0x1464,0x001eb4,0x00003A74),
		new Character("Master Hand","Mh",0x914+0x20,0x00002824,0x0000487C),
		new Character("Crazy Hand","Ch",0x898+0x20,0x00002C40,0x00004C80),
	};

	public String name,id;
	public int offset,subOffset,subEnd;
	public ImageIcon characterIcon;
	public Character(String nm, String i, int off, int sub, int sEnd){
		name=nm;
		id=i;
		offset=off;
		subOffset=sub;
		subEnd=sEnd;
		String s = "img/icons/"+this.name+".png";
		this.characterIcon = new ImageIcon(s);
	}
	public int getPlaceInArray() {
		for(int i = 0; i < Character.characters.length; i ++){
			if(Character.characters[i] == this){
				return i;
			}
		}
		return 0;
	}
}
