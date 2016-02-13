package com.panels;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.Character;
import com.FileIO;
import com.scripts.HitboxScript;

public class ProjectileEditNode extends JPanel {
	public int charId;
	public HitboxScript projectile;
	public String prname;
	
	public ProjectileEditNode(String projName, int chr, int pointer){
		super();
		prname=projName;
		charId = chr;
		if(charId==255){
			FileIO.initItCo();
		}
		else{
			FileIO.init(charId);
		}
		
		FileIO.setPosition(pointer);
		int[] d = new int[0x20];
		
		for(int i = 0; i < d.length; i ++){
			int bt = FileIO.readByte();
			if(i==0 && bt != 0x2C){
				System.out.println("ERROR with ProjectileEditNode for [CID:"+chr+"/PROJ:"+projName+"]! No hitbox at given pointer 0x"+Integer.toHexString(pointer));
				return;
			}
			d[i]=bt;
		}
		
		String name;
		if(charId != 255){
			name = Character.characters[chr].name + " - " + projName;
		}
		else{
			name = projName;
		}
		projectile = new HitboxScript(name, d, pointer);
		projectile.updateScriptBoxInfo();
		projectile.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		projectile.setBorder(BorderFactory.createBevelBorder(0));
		//projectile.setPreferredSize(new Dimension(500,projectile.getPreferredSize().height));
		this.add(projectile);
	}
	
	public void save(){
		
		try {
			FileIO.loadedISOFile.reload();

			if(charId==255){
				FileIO.initItCo();
			}
			else{
				FileIO.init(charId);
			}
			
			projectile.save();
			
			//FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), FileIO.f.array());
			//FileIO.loadedISOFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
