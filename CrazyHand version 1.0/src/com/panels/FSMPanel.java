package com.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.events.Characters;

import com.FileIO;
import com.MeleeEdit;
import com.BitWork;
import com.Character;

public class FSMPanel extends JPanel {
	
	
	public LinkedList<FSMData> modifiers = new LinkedList<FSMData>();
	public LinkedList<FSMData> modifiersShortList = new LinkedList<FSMData>();
	
	
	public ArrayList<FSMNode> nodes = new ArrayList<FSMNode>();
	
	
	public JPanel j;//a temporary panel
	public JButton addNew, importFrom,exportTo;

	public FSMPanel() {
		super();
		
		refresh();
		

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(700, 500));
		
	}
	
	public void refresh(){



		
		FileIO.initDOL();
		
		
		int pointer = 0x4089b0;
		
		
		System.out.println("FSM Panel refresh at loc 0x"+Integer.toHexString(pointer));
		
		
		FileIO.setPosition(pointer);
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
			
			modifiers.add(new FSMData(b,flt));
			
		}
		
		Collections.sort(modifiers);
		
		System.out.println("\n\n\nALL SCRIPTS");
		for(int i = 0; i < modifiers.size(); i++){
			FSMData n = modifiers.get(i);
			printModifier(n);
			
			
			
			if(n.charId == Character.characters[MeleeEdit.selected].characterID){
				modifiersShortList.add(modifiers.get(i));
				modifiers.remove(i);
				i--;	
			}
			

			
			
		}

		
		System.out.println("\n\n\nFILTERED SCRIPTS");
		printModifiers(modifiersShortList);
		System.out.println("\n\n\nOTHER SCRIPTS");
		printModifiers(modifiers);
		
		
		
		System.out.println("\n\n\nBREAKIN' IT UP");
		LinkedList<FSMData> singleList = new LinkedList<FSMData>();
		int currentID=-1;
		if(modifiersShortList.size()>0){
			currentID=modifiersShortList.get(0).actionID;
		}
		
		for(int i = 0; i < modifiersShortList.size(); i++){
			FSMData n = modifiersShortList.get(i);
			
			
			
			if(n.actionID != currentID){
				nodes.add(new FSMNode(singleList));
				printModifiers(singleList);
				singleList.clear();
				currentID = n.actionID;
				System.out.println();
			}
			
			singleList.add(n);
			
		}
		if(modifiersShortList.size()>0){
			nodes.add(new FSMNode(singleList));
			printModifiers(singleList);
			singleList.clear();
			System.out.println();
		}
		
		
		
		
		
		
		
		update();

	}

	public void printModifier(FSMData n){
		System.out.print(Character.getNameFromID(n.charId));
		System.out.print("  charID=" + n.charId);
		System.out.print("  frame=" + n.startFrame);
		System.out.print("  actID=" + n.actionID);
		System.out.print("  mul=" + n.multiplier);
		System.out.println("  action=" + n.isAction);
	}
	public void printModifiers(LinkedList<FSMData> n){
		for(int i = 0; i < n.size(); i++){
			printModifier(n.get(i));
		}	
	}
	
	public void update(){
		
		int scroll = 0;
		
		if(an!=null){
			scroll=an.getVerticalScrollBar().getValue();
		}
		
		//System.out.println(j.getParent() instanceof JScrollPane);
		
		this.removeAll();
		
		j = new JPanel();
		j.setLayout(new BoxLayout(j, BoxLayout.PAGE_AXIS));
		//j.setBackground(new Color(.8f,.8f,.8f));
		j.setBackground(new Color(1f,1f,1f));
		
		
		for(int i = 0; i < nodes.size(); i++)
		{
			if(nodes.get(i).active == false)
			{
				nodes.remove(i);
				i--;
			}
		}
		
		j.add(Box.createVerticalStrut(5));
		for(FSMNode n: nodes){
			n.reconstitute();
			n.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			j.add(n);
			j.add(Box.createVerticalStrut(15));
			
		}
		
		//System.out.println(scroll);
		
		an = new JScrollPane(j);
		an.getVerticalScrollBar().setUnitIncrement(10);
	    an.setPreferredSize(new Dimension(700,500));
		an.getVerticalScrollBar().setValue(scroll*10);
		an.getVerticalScrollBar().updateUI();
        
        AddListener addl = new AddListener();
        
        importFrom = new JButton("Import FSM data from file");
        importFrom.setActionCommand("import");
        importFrom.addActionListener(addl);
        
        exportTo = new JButton("Export FSM data");
        exportTo.setActionCommand("export");
        exportTo.addActionListener(addl);
        
        addNew = new JButton("Add New Modifier");
        addNew.setActionCommand("add");
        addNew.addActionListener(addl);
        
        //JScrollBar sb = an.getVerticalScrollBar();
        //sb.setValue(10000000);
        //System.out.println(an.getVerticalScrollBar().getMaximum());
        //System.out.println(an.getVerticalScrollBar().getValue());
        
        Box hbox = Box.createHorizontalBox();
        hbox.add(addNew);
        hbox.add(Box.createHorizontalStrut(5));
        hbox.add(importFrom);
        hbox.add(Box.createHorizontalStrut(5));
        hbox.add(exportTo);
        this.add(hbox);
        this.add(Box.createVerticalStrut(10));
		this.add(an);
		
		
		
		MeleeEdit.frame.pack();
		
		FileIO.loadedISOFile.close();
	}
	
	JScrollPane an;
	
	public void save(){

		
		int pointer = 0x4089b0;
		
		
		FileIO.initDOL();
		System.out.println("po1");
		FileIO.setPosition(pointer);
		System.out.println("po2");
		int i = 0;
		//for(FSMNode n: nodes){
		//	n.save(0);
		//	i++;
		//	System.out.println("po3");
		//}
		while(i<150){
			for(int k = 0; k < 8; k++)
				FileIO.writeByte(0);
			i++;
		}
		
		injectCode();
		update();
		
		
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
			String cmd = arg0.getActionCommand();
			if(cmd=="add"){
				int[] b = {0,0,0x80,0x2c};
				//nodes.add(new FSMNode(b,1.f));
				
				update();
			}
			if(cmd=="import"){
				importFrom();
			}
			if(cmd=="export"){
				exportFSMData();
			}
		}
	}
	
	public void importFrom(){
		JFileChooser loadFileDialog = new JFileChooser();
		System.out.println("Exporting FSM data");
		FileNameExtensionFilter datFilter = new FileNameExtensionFilter(
				"Text Files", "txt");
		loadFileDialog.setCurrentDirectory(new File(System.getProperty("user.dir")));
		loadFileDialog.addChoosableFileFilter(datFilter);
		loadFileDialog.setFileFilter(datFilter);
	loadFileDialog.setDialogType(JFileChooser.OPEN_DIALOG);

	
	int returnVal = loadFileDialog.showSaveDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if(loadFileDialog.getSelectedFile() != null){
				try{
					File fi = loadFileDialog.getSelectedFile();
					
					try {
						List<String> lines = Files.readAllLines(fi.toPath(), StandardCharsets.UTF_8);
						for(int i = 0; i < lines.size(); i ++)
						{
							String o = lines.get(i);
							System.out.println(o);
							
							String[] params = o.split(",");
							
							int[] data = new int[4];
							for(int c = 0; c < data.length; c++){
								data[c]=Integer.parseInt(params[c]);
							}
							float val = Float.parseFloat(params[4]);
							
							//FSMNode n = new FSMNode(data, val);
							//nodes.add(n);
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		update();
	}
	
	public void exportFSMData(){
		JFileChooser saveFileDialog = new JFileChooser();
		System.out.println("Exporting FSM data");
		FileNameExtensionFilter datFilter = new FileNameExtensionFilter(
				"Text Files", "txt");
		saveFileDialog.setCurrentDirectory(new File(System.getProperty("user.dir")));
		saveFileDialog.addChoosableFileFilter(datFilter);
		saveFileDialog.setFileFilter(datFilter);
	saveFileDialog.setDialogType(JFileChooser.SAVE_DIALOG);

	
	int returnVal = saveFileDialog.showSaveDialog(MeleeEdit.frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if(saveFileDialog.getSelectedFile() != null){
				try{
					File fi = saveFileDialog.getSelectedFile();
					
					String filePath = fi.getAbsolutePath();
					if(!filePath.endsWith(".txt")) {
					    fi = new File(filePath + ".txt");
					}
					
					if(!fi.exists()){
							fi.createNewFile();
					}
					
					PrintWriter out = new PrintWriter(fi);
					
					String ln = System.lineSeparator();
					String output="";
					
					//Collections.sort(nodes);
					
					//for(FSMNode n : nodes){
					//	n.reconstitute();
					//	for(int i = 0; i < n.data.length; i ++){
					//		output+=n.data[i];
					//		output+=",";
					//	}
					//	output+=((Number)n.value.getValue()).floatValue();
					//	if(nodes.get(nodes.size()-1)!=n){
					//		output+=ln;
					//	}
					//}
					
					out.write(output);
					out.close();
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}




