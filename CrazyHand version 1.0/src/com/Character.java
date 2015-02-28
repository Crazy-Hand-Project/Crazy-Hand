package com;

public class Character {
	//Ampers github test #2, ignore this
	public static Character[] characters = {
		new Character("Bowser","Kp",0x3644,0x007b40),
		new Character("Captain Falcon","Ca",0x3774,0x007a98),
		new Character("Doctor Mario","Dr",0x3540,0x007264),
		new Character("Donkey Kong","Dk",0x39B0,0x007e78),
		new Character("Falco","Fc",0x37E4,0x007804),
		new Character("Fox","Fx",0x3714,0x00771c),
		new Character("Game & Watch","Gw",0x3614,0x007c58),
		new Character("Ganondorf","Gn",0x371c,0x0075f0),
		new Character("Ice Climbers (Popo)","Pp",0x32A4,0x006f98),
		new Character("Ice Climbers (Nana)","Nn",0x1188,0x004d30),
		new Character("Jigglypuff","Pr",0x38BC,0x006f3c),
		new Character("Kirby", "Kb",0x4CE8,0x00b280),
		new Character("Luigi","Lg",0x33A0,0x006f40),
		new Character("Link","Lk",0x33FC,0x007d90),
		new Character("Mario","Mr",0x32D8,0x006f20),
		new Character("Marth","Ms",0x3744,0x007cf0),
		new Character("Mewtwo","Mt",0x3750,0x0075a0),
		new Character("Ness","Ns",0x34E0,0x007674),
		new Character("Peach","Pe",0x3894,0x008680),
		new Character("Pichu","Pc",0x3454,0x0075c8),
		new Character("Pikachu","Pk",0x3584,0x0075f4),
		new Character("Roy","Fe",0x389C,0x00800c),
		new Character("Samus","Ss",0x3484,0x0079a8),
		new Character("Sheik","Sk",0x3418,0x007420),
		new Character("Yoshi","Ys",0x335C,0x007320),
		new Character("Young Link","Cl",0x35A0,0x00813c),
		new Character("Zelda","Zd",0x37F4,0x007cc8),
		new Character("Wireframe (Male)","Bo",0x31C8,0x006214),
		new Character("Wireframe (Female)","Gl",0x2FF4,0x006130),
		new Character("Giga Bowser","Gk",0x34D0,0x007c40),
		new Character("Sandbag","Sb",0x1464,0x001eb4),
	};

	public String name,id;
	public int offset,subOffset;
	public Character(String nm, String i, int off, int sub){
		name=nm;
		id=i;
		offset=off;
		subOffset=sub;
	}
}
