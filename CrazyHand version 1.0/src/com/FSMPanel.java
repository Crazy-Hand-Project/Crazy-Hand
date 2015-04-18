package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FSMPanel extends JPanel {
	
	public static FSMData[] characters = {
		new FSMData("Captain Falcon",0x00),
		new FSMData("Donkey Kong",0x01),
		new FSMData("Fox",0x02),
		new FSMData("Game & Watch",0x03),
		new FSMData("Kirby",0x04),
		new FSMData("Bowser",0x05),
		new FSMData("Link",0x06),
		new FSMData("Luigi",0x07),
		new FSMData("Mario",0x08),
		new FSMData("Marth",0x09),
		new FSMData("Mewtwo",0x0A),
		new FSMData("Ness",0x0B),
		new FSMData("Peach",0x0C),
		new FSMData("Pikachu",0x0D),
		new FSMData("Ice Climbers",0x0E),
		new FSMData("Jigglypuff",0x0F),
		new FSMData("Samus",0x10),
		new FSMData("Yoshi",0x11),
		new FSMData("Zelda",0x12),
		new FSMData("Sheik",0x13),
		new FSMData("Falco",0x14),
		new FSMData("Young Link",0x15),
		new FSMData("Dr Mario",0x16),
		new FSMData("Roy",0x17),
		new FSMData("Pichu",0x18),
		new FSMData("Ganondorf",0x19),
		new FSMData("Master Hand",0x1A),
		new FSMData("M Wireframe",0x1B),
		new FSMData("F Wireframe",0x1C),
		new FSMData("Giga Bowser",0x1D),
		new FSMData("Crazy Hand",0x1E),
		new FSMData("Sandbag",0x1F),
		new FSMData("ALL",0xFF),
	};
	public static String[] characterNames = new String[characters.length];
	
	public static FSMData[] actions = {
		new FSMData(0xFFF,"UNKNOWN"),
		new FSMData(0x02C,"Attack11"), 
		new FSMData(0x02D,"Attack12"), 
		new FSMData(0x02E,"Attack13"), 
		new FSMData(0x02F,"Attack100Start"), 
		new FSMData(0x030,"Attack100Loop"), 
		new FSMData(0x031,"Attack100End"), 
		new FSMData(0x032,"AttackDash"), 
		new FSMData(0x033,"AttackS3Hi"), 
		new FSMData(0x034,"AttackS3HiS"), 
		new FSMData(0x035,"AttackS3S"), 
		new FSMData(0x036,"AttackS3LwS"), 
		new FSMData(0x037,"AttackS3Lw"), 
		new FSMData(0x038,"AttackHi3"), 
		new FSMData(0x039,"AttackLw3"), 
		new FSMData(0x03A,"AttackS4Hi"), 
		new FSMData(0x03B,"AttackS4HiS"), 
		new FSMData(0x03C,"AttackS4S"), 
		new FSMData(0x03D,"AttackS4LwS"), 
		new FSMData(0x03E,"AttackS4Lw"), 
		new FSMData(0x03F,"AttackHi4"), 
		new FSMData(0x040,"AttackLw4"), 
		new FSMData(0x041,"AttackAirN"), 
		new FSMData(0x042,"AttackAirF"), 
		new FSMData(0x043,"AttackAirB"), 
		new FSMData(0x044,"AttackAirHi"), 
		new FSMData(0x045,"AttackAirLw"), 
		new FSMData(0x0D4,"Catch"), 
		new FSMData(0x0D5,"CatchPull"), 
		new FSMData(0x0D6,"CatchDash"), 
		new FSMData(0x0D7,"CatchDashPull"), 
		new FSMData(0x0D8,"CatchWait"), 
		new FSMData(0x0D9,"CatchAttack"), 
		new FSMData(0x0DA,"CatchCut"), 
		new FSMData(0x0DB,"ThrowF"), 
		new FSMData(0x0DC,"ThrowB"), 
		new FSMData(0x0DD,"ThrowHi"), 
		new FSMData(0x0DE,"ThrowLw"),
		
		
	};
	public static String[] actionNames = new String[actions.length];
	public ArrayList<FSMNode> nodes = new ArrayList<FSMNode>();
	public JPanel j;//a temporary panel
	public JButton addNew;

	public FSMPanel() {
		super();
		
		refresh();
		

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		this.setPreferredSize(new Dimension(700, 500));
	}
	
	public void refresh(){
		nodes.clear();
		
		
		
		for(int i = 0; i < characters.length; i++){
			characterNames[i] = characters[i].name;
		}
		for(int i = 0; i < actions.length; i++){
			actionNames[i] = actions[i].name;
		}

		
		FileIO.initDOL();
		FileIO.setPosition(0x4089b0);
		int[] b = new int[4];
		

		while(true){
			boolean tmp=true;
			for(int i = 0; i < 4; i++){
				b[i]=FileIO.readByte();
				if(b[i]!=0){
					tmp=false;
				}
			}
			if(tmp)
				break;
			
			float flt = FileIO.readFloat();
			
			nodes.add(new FSMNode(b,flt));
			
			System.out.println(b[0]);
			
		}
		update();
		
	}
	
	public void update(){
		
		this.removeAll();
		
		j = new JPanel();
		j.setLayout(new BoxLayout(j, BoxLayout.PAGE_AXIS));
		
		for(int i = 0; i < nodes.size(); i++)
		{
			if(nodes.get(i).active == false)
			{
				nodes.remove(i);
				i--;
				
			}
		}
		
		for(FSMNode n: nodes){
			n.reconstitute();
			j.add(n);
			j.add(new JSeparator(SwingConstants.HORIZONTAL));
			
		}

		
		JScrollPane an = new JScrollPane(j);
		an.getVerticalScrollBar().setUnitIncrement(10);
        an.setPreferredSize(new Dimension(700,500));
        
        addNew = new JButton("Add New Modifier");
        addNew.addActionListener(new AddListener());
        
        this.add(addNew);
		this.add(an);
		
		MeleeEdit.frame.pack();
	}
	
	
	
	public void save(){
		Collections.sort(nodes);
		
		FileIO.initDOL();
		FileIO.setPosition(0x4089b0);
		int i = 0;
		for(FSMNode n: nodes){
			n.save(0);
			i++;
		}
		while(i<150){
			for(int k = 0; k < 8; k++)
				FileIO.writeByte(0);
			i++;
		}
		
		update();
		
		injectCode();
	}
	public void injectCode(){

		FileIO.setPosition(0x6FF18);
		FileIO.writeByte(0x48);
		FileIO.writeByte(0x39);
		FileIO.writeByte(0x85);
		FileIO.writeByte(0x78);
		
		String code = "7F63DB788BE3006C3FA0804563BD30841FFF0E907FFDFA14809F00008BFF00082C0400134182001C2C04001240A200202C1F000140A2001838800013480000102C1F000140A2000838800012C03E0894FC20081ED822000080A2000480C3007080E3007460E780003FE0804063FFB9A887BF00082C1D00004182006057BC463E2C1C00FF418200147C1C20004182000C418100484BFFFFDC57BC863E7C1C280041A1FFD057BC043E7C1C30004182000C7C1C38004082FFBC839F00042C1CFFFF41820018C03F00043FE0800663FFF1907FE803A64E800021BB6100144BC679B00000000000000000000000000000000000000000000000000000000000000000";
		
		FileIO.setPosition(0x4088B0);
		for(int i = 0; i < code.length(); i+=2){
			String tmp = new StringBuilder().append("").append(code.charAt(i)).append(code.charAt(i+1)).toString();
			int byt = Integer.decode("0x" + tmp);
			//System.out.println(byt);
			FileIO.writeByte(byt);
		}
		
	}
	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int[] b = {0,0,0x80,0x2c};
			nodes.add(new FSMNode(b,1.f));
			
			update();
		}
	}
	
	
	
}




