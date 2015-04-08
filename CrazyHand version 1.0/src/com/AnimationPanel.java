package com;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class AnimationPanel extends JPanel{
	public static String[] anims,animNames;
	public static String[] defaultNames;
	public ArrayList<AnimationNode> nodes = new ArrayList<AnimationNode>();
	
	public AnimationPanel(){
		super();
		
		
		refresh();
		
	}
	public void refresh(){
		this.removeAll();
		nodes.clear();
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		
		
		JPanel j = new JPanel();
		j.setLayout(new BoxLayout(j, BoxLayout.PAGE_AXIS));
		//names = FileIO.getSubactions();
		defaultNames = FileIO.getDefaultSubactions();
		anims = getAnims();
		animNames = getNames();
		
		FileIO.init();
		for(int i = 0; i <SubAction.getNum(); i++){
			
			int offTmp = i*6*4;
        	int pointerLoc = Character.characters[MeleeEdit.selected].subOffset+0x20 +4*1+offTmp;
        	FileIO.setPosition(pointerLoc);
        	int pointer = FileIO.readInt();
        	
        	if(pointer != 0){
        		nodes.add(new AnimationNode(pointerLoc,pointer,FileIO.readInt(),i));
        		
        	}
        	
        	

        	
        	
        	
        	
        	
		}
		for(AnimationNode n: nodes){
    		j.add(n);
    		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
			//sep.setBackground(Color.BLUE);
			j.add(sep);
    		
    		
    	}
		JScrollPane an = new JScrollPane(j);
		an.getVerticalScrollBar().setUnitIncrement(10);
        an.setPreferredSize(new Dimension(700,500));
		this.add(an);
	}
	
	
	
	
	public static String[] getNames(){
		
		String[] tmp = new String[AnimationPanel.anims.length];
		for(int i = 0; i < AnimationPanel.anims.length; i++){
			String[] t = AnimationPanel.anims[i].split(" ");
			tmp[i] = t[0];
			
			for(int k = 0; k < AnimationPanel.anims.length; k++){
				
			}
		}
		return tmp;
	}
	
	public static String[] getAnims(){
		BufferedReader br = null;
		int len=0;
		try {
			String s;
			br = new BufferedReader(new FileReader("anm/" + Character.characters[MeleeEdit.selected].id + ".anm"));
			while ((s = br.readLine()) != null){len++;}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this stuff above is to find the length, down below it actually does stuff.
		
		
		
		
		
		
		 String[] tmp = new String[len];
		 int i=0;
		try {
 
			String s;
 
			br = new BufferedReader(new FileReader("anm/" + Character.characters[MeleeEdit.selected].id + ".anm"));
 
			while ((s = br.readLine()) != null) {
				tmp[i]=s;
				i++;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return tmp;
	}
}
