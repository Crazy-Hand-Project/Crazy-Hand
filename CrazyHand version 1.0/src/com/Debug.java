package com;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.panels.ProjectileEditPane;



public class Debug {
	//THIS FILE IS ONLY USED FOR TESTING!
	//it also contains code that only needed to ever be run one time (like for generating lists).
	//If this file is causing compile errors when adding/changing code, you can comment out this entire file.
	
	
	public static void act() throws IOException{
			System.out.println("start");
			
			for(int i = 0; i < Character.characters.length; i++){
				System.out.print(Character.characters[i].id + "=" + Character.characters[i].name + ", ");
			}

			
			System.out.println("end");
			System.exit(0);
	}
	
	public static void test() throws IOException{
		BufferedReader r = new BufferedReader(new FileReader("vals/test.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("vals/output.txt"));
		int c;
		while((c=r.read()) != -1){
			writer.write((char)c);
			System.out.println(c);
				
		}
		System.out.println(r.read());
		r.close();
		writer.close();
	}
	
	
	public static void getProjectiles(){
		FileIO.loadISOFile();
		FileIO.init();
		ProjectileEditPane a = new ProjectileEditPane();

		for(int i = 0; i < a.nodes.length; i++){
			String tmp;
			if(a.nodes[i].charId<254){
				tmp = Character.characters[a.nodes[i].charId].id;
			}
			else{
				tmp="xx";
			}
			
			System.out.print(tmp + "~~");
			tmp = "0x" + Integer.toHexString(a.nodes[i].projectile.location);
			System.out.print(tmp + "~~");
			System.out.print(a.nodes[i].prname + "");
			System.out.println();
		}

	}
	/*
	public static void declareAnims() {// one time run to generate a list of all
		// animation offsets/names
try {

// writer.println("The first line");
// writer.println("The second line");
int numSubactions = 0;// DEFINE THIS!
// int tmp = 0;
for (int k = 0; k < Character.characters.length; k++) {
numSubactions = (Character.characters[k].subEnd - Character.characters[k].subOffset) / 6 / 4;

MeleeEdit.selected = k;
init();
String[] names = getDefaultSubactions();

PrintWriter writer = new PrintWriter("anm/"
+ Character.characters[k].id + ".anm", "UTF-8");
for (int i = 0; i < numSubactions; i++) {
int offTmp = i * 6 * 4;
int pointerLoc = Character.characters[k].subOffset + 0x20
+ 4 * 0 + offTmp;
setPosition(pointerLoc);
int p1 = readInt(), p2 = readInt(), p3 = readInt(), dummy = readInt(), p4 = readByte();
if (p2 != 0)
writer.println(names[i] + " " + Integer.toString(p1)
+ " " + Integer.toString(p2) + " "
+ Integer.toString(p3) + " "
+ Integer.toString(p4));
// tmp++;
// System.out.println(tmp);
}
writer.close();
}

} catch (FileNotFoundException e) {
e.printStackTrace();
} catch (UnsupportedEncodingException e) {
e.printStackTrace();
}
}
*/
	
	
	
	
	
	public static void storeAttributes(){
		//note! in this code the string passed in does nothing. It's a relic
		//counter = 0;
		/*
		SpecialAttributeIndex ind = new SpecialAttributeIndex();	
		save(ind.bowserSpecialMoveAttributes,"bowser");
		save(ind.falconSpecialMoveAttributes,"falcon");
		save(ind.doctorMarioSpecialMoveAttributes,"doctorMario");
		save(ind.dkSpecialMoveAttributes,"donkeyKong");
		save(ind.falcoSpecialMoveAttributes,"falco");
		save(ind.foxSpecialMoveAttributes,"fox");
		save(ind.gnwSpecialMoveAttributes,"gameAndWatch");
		save(ind.ganondorfSpecialMoveAttributes,"ganondorf");
		save(ind.iceclimbersSpecialMoveAttributes,"iceClimbers");
		save(ind.iceclimbersSpecialMoveAttributes,"iceClimbers");
		save(ind.jigglypuffSpecialMoveAttributes," ");
		save(ind.kirbySpecialMoveAttributes," ");
		save(ind.luigiSpecialMoveAttributes,"luigi");
		save(ind.linkSpecialMoveAttributes,"link");
		save(ind.marioSpecialMoveAttributes,"mario");
		save(ind.marthSpecialMoveAttributes,"marth");
		save(ind.mewtwoSpecialMoveAttributes,"mewtwo");
		save(ind.nessSpecialMoveAttributes,"ness");
		save(ind.peachSpecialMoveAttributes,"peach");
		save(ind.pichuSpecialMoveAttributes,"pichu");
		save(ind.pikachuSpecialMoveAttributes,"pikachu");
		save(ind.roySpecialMoveAttributes,"roy");
		save(ind.samusSpecialMoveAttributes,"samus");
		save(ind.sheikSpecialMoveAttributes,"sheik");
		save(ind.yoshiSpecialMoveAttributes," ");
		save(ind.younglinkSpecialMoveAttributes,"youngLink");
		save(ind.zeldaSpecialMoveAttributes," ");
		save(ind.none," ");
		save(ind.none," ");
		save(ind.none," ");
		save(ind.none," ");
		save(ind.none," ");
		save(ind.none," ");
		*/
		
	}
	
	/*
	public static int counter = 0;
	public static void save(SpecialMoveAttribute[] att, String name){
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("vals/CharacterSpecificAttributes/" + Character.characters[counter].name + " Attributes.txt"));
				counter++;
				for(int i = 0; i < att.length; i++){
					writer.write("0x");
					writer.write(Integer.toHexString(att[i].loc));
					writer.write("~~");
					writer.write(att[i].name);
					writer.write("~~");
					writer.write(att[i].info);
					writer.write("~~");
					if(att[i].isInt){
						writer.write("I");
					}
					else{
						writer.write("F");
					}
					writer.write("~~");
					writer.write(att[i].associatedMove);
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {e.printStackTrace();}
	}
	*/

}












