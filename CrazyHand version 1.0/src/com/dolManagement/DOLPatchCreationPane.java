package com.dolManagement;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.FileIO;
import com.MeleeEdit;
import com.Options;


//Currently unused. Will be polished and implemented once there is more work done on a dol with a gecko codehandler built in.

public class DOLPatchCreationPane extends JPanel implements ActionListener{
	
	public JButton okBtn;
	public JTextField code, patchName;
	public DOLPatchManager parent;
	
	public DOLPatchCreationPane(DOLPatchManager p){
		this(p,false);
	}
	
	public DOLPatchCreationPane(DOLPatchManager p, boolean e) {
		super();
		parent=p;
		this.isEditing = e;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension dim = new Dimension(600,400);
		setPreferredSize(dim);
		setMaximumSize(dim);
		setMinimumSize(dim);
		okBtn = new JButton(e ? "Apply edits" : "Add Code");
		okBtn.setActionCommand("addcode");
		okBtn.addActionListener(this);
		code = new JTextField();
		
		patchName = new JTextField("Enter name here...");
		patchName.setPreferredSize(new Dimension(600,75));
		
		Box btnBox = Box.createHorizontalBox();
		
		btnBox.add(okBtn);
		
		this.add(patchName);
		this.add(Box.createVerticalStrut(15));
		this.add(code);
		this.add(Box.createVerticalStrut(15));
		this.add(btnBox);
		
	}
	
	public String[] geckoStart={
			"00", "D0", "C0", "DE", "00", "D0", "C0", "DE"
		};
	
	public String[] geckoEnd={
			"F0","00","00","00"
		};
	public boolean isEditing=false;
	
	public DOLPatch convertTextToCode(String patchName, String text, int pointer){
	
		
		//debugging tool
		boolean printres=true;
		String output="";
		//
		
		String preppedCode ="";
		
		for(int i = 0; i < geckoStart.length; i ++){
			//preppedCode+=geckoStart[i];
		}
		
		preppedCode+=text.replaceAll(" ", "");
		
		for(int i = 0; i < geckoEnd.length; i ++){
			//preppedCode+=geckoEnd[i];
		}
		
		System.out.println("Csadnj"+preppedCode);
		
		int[] pnt={0x3F712C};//Not used, just filler
		
		int sz =0;// geckoStart.length+geckoEnd.length;
				
		sz+=preppedCode.length()/2;
		//sz++;
		int[][] defaults=new int[sz][sz], newvals=new int[sz][sz];
		
		String built = "";
		System.out.println("\n");
		for(int i = 0; i < preppedCode.length()-1; i +=2){
			int bt = Integer.parseInt(preppedCode.substring(i,i+2),16);
			if(bt <=0xF){
				if(!preppedCode.substring(i,i+2).endsWith("0"))
					built +="0";
				else
					built = built+"0";
			}
			built += Integer.toHexString(bt);
			if(i % 8 == 0){
				built +="\n";
			}
			else{
				built += " ";
			}
		}
		
		built=built.toUpperCase();
		
		System.out.println(built);
		
		//if(1==1)return null;
		
		FileIO.initDOL();
		
		built = built.replaceAll(" ", "");
		built = built.replaceAll("\n", "");
		
		for(int i = 0; i < built.length()-1; i += 2){
			FileIO.setPosition(pointer+i);
			
			int defbt = FileIO.readByte();
			
			String bt = built.substring(i, i+2);
			if(bt.length()<2){
				bt="0"+bt;
			}
			
			System.out.println(bt);
			
			int newbt = Integer.parseInt(bt, 16);
			
			
			
			if(printres){
				output+=("["+defbt+"]~"+"[\""+bt+"\"->0x"+Integer.toHexString(newbt)+"]");
				output+="\n";
			}
			
			defaults[0][i/2]=defbt;
			newvals[0][i/2]=newbt;
		}
		
		DOLPatch patch = new DOLPatch(patchName, pointer, pnt, defaults, newvals);
		patch.setIsGecko();
		
		if(printres){
			System.out.println("=====Start~~Code====="+"\n");
			System.out.println(output);
			System.out.println("=====End~~Code====="+"\n");
		}
		
		return patch;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		System.out.println("jasd");
		if(cmd=="addcode"){
			addCode();
		}
	}

	public void addCode() {
		System.out.println("Trying to add code...");
		DOLPatch patch = convertTextToCode(patchName.getText(),code.getText(),parent.patchOffset+1);
		
		if(patch!=null){
			//patch.applyPatch();
			
			DOLPatchDisplayNode node = new DOLPatchDisplayNode(parent, patch);
			if(!isEditing){
				System.out.println("Not editing! null node:" + (node==null));
				parent.addToPatchList(node);
			}else{
				System.out.println("edit only!");
				parent.updateUI();
			}
		}
		else{
			System.out.println("Patch isn't doing the thing right.");
		}
	}

}
