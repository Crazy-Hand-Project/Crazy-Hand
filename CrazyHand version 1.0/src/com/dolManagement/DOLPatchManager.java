package com.dolManagement;

import isotool.filesystem.ISOFile;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.FileIO;
import com.MeleeEdit;
import com.Options;

public class DOLPatchManager extends JPanel implements ActionListener{
	
	public JButton convBtn, finalBtn, restoreBtn, saveDolBtn, replaceBtn, geckoBtn,
				   clearCodesBtn,saveToTxtBtn,loadFromTxtBtn;
	public JTextField code;
	public JPanel panetst;
	public ArrayList<DOLPatchDisplayNode> patchList = new ArrayList<DOLPatchDisplayNode>();
	
	public int endingPointer, patchOffset;
	
	public DOLPatchManager() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension dim = new Dimension(600,400);
		setPreferredSize(dim);
		setMaximumSize(dim);
		setMinimumSize(dim);
		//System.out.println(isGeckoSupported());
		convBtn = new JButton("Convert code");
		convBtn.setActionCommand("convertcode");
		convBtn.addActionListener(this);
		
		finalBtn = new JButton("Apply all patches");
		finalBtn.setActionCommand("finalize");
		finalBtn.addActionListener(this);
		
		geckoBtn = new JButton("Replace Start.dol with gecko-compatible version");
		geckoBtn.setActionCommand("replacegecko");
		geckoBtn.addActionListener(this);
		
		clearCodesBtn = new JButton("Clear codes list");
		clearCodesBtn.setActionCommand("clearcodes");
		clearCodesBtn.addActionListener(this);
		
		saveToTxtBtn = new JButton("Save code list to .TXT file");
		saveToTxtBtn.setActionCommand("savecodes");
		saveToTxtBtn.addActionListener(this);
		
		loadFromTxtBtn = new JButton("Load code list from .TXT file");
		loadFromTxtBtn.setActionCommand("loadcodes");
		loadFromTxtBtn.addActionListener(this);
		
		//ripped from debug
				saveDolBtn = new JButton("Save Start.dol to file");
				saveDolBtn.setActionCommand("savemain");
				saveDolBtn.addActionListener(this);
				restoreBtn = new JButton("Restore Start.dol to original settings");
				restoreBtn.setActionCommand("fixdol");
				restoreBtn.addActionListener(this);
				replaceBtn = new JButton("Replace Start.dol");
				replaceBtn.setActionCommand("replacemain");
				replaceBtn.addActionListener(this);
		
		
		code = new JTextField();
		
		Box btnBox = Box.createHorizontalBox();
		Box btnBox2 = Box.createVerticalBox();
		
		btnBox2.add(saveToTxtBtn);
		btnBox2.add(loadFromTxtBtn);
		btnBox2.add(restoreBtn);
		btnBox2.add(saveDolBtn);
		btnBox2.add(replaceBtn);
		btnBox2.add(geckoBtn);
		btnBox2.add(clearCodesBtn);
		
		btnBox.add(btnBox2);
		btnBox.add(Box.createHorizontalStrut(10));
		btnBox.add(convBtn);
		btnBox.add(finalBtn);
		
		this.add(btnBox);
		
		endingPointer = getEndOfGeckoChunk();
		patchOffset=0x3F712C;
		panetst = new JPanel();
		this.add(panetst);
	}
	
	public boolean isGeckoSupported(){
		boolean res = false;
		
		FileIO.initDOL();
		
		//Default pointer for gecko support
		FileIO.setPosition(0x003F7124);
		//Check if the D0C0DE00D0C0DE line is present
		if(FileIO.readByte()==0x00&&FileIO.readByte()==0xD0&&FileIO.readByte()==0xC0&&FileIO.readByte()==0xDE){
			FileIO.setPosition(0x003F7129);
			if(FileIO.readByte()==0xD0&&FileIO.readByte()==0xC0&&FileIO.readByte()==0xDE){
				//System.out.println("huh?");
				return true;
			}
		}
		
		return false;
	}
	
	public int[] geckoStart={
		0x00, 0xD0, 0xC0, 0xDE, 0x00, 0xD0, 0xC0, 0xDE
	};
	
	public int[] geckoEnd={
	//    E    N    D    G    E    C    K    O
		  0x45,0x4E,0x44,0x47,0x45,0x43,0x4B,0x4F
	};
	
	public DOLPatch convertTextToCode(String patchName, String text, int pointer){
		if(text.startsWith("04")){
			JOptionPane optionPane = new JOptionPane(
				    JOptionPane.QUESTION_MESSAGE,
				    JOptionPane.OK_OPTION);
			int res = optionPane.showConfirmDialog(MeleeEdit.geckoManagerFrame,
					"Please inject AR codes using the AR code injection menu.", "Wrong code format!",
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			
			if(res!=JOptionPane.OK_OPTION){
				//return;
			}
			return null;
		}
		
		//debugging tool
		boolean printres=true;
		String output="";
		//
		
		String preppedCode = text.replaceAll(" ", "");
		
		int[] pnt={0x3F712C};//Not used, just filler
		
		Integer[][] defaults=new Integer[1][1], newvals=new Integer[1][1];
		Integer[]tmp1=new Integer[1],tmp2=new Integer[1];
		
		ArrayList<Integer> defvl = new ArrayList<Integer>();
		ArrayList<Integer> newvl = new ArrayList<Integer>();
		
		int codeSize = preppedCode.length()/2;
		
		FileIO.initDOL();
		
		for(int i = 0; i < preppedCode.length(); i +=2){
			FileIO.setPosition(pointer+i);
			
			int defbt = FileIO.readByte();
			
			String bt = preppedCode.substring(i, i+2);
			
			int newbt = Integer.parseInt(bt, 16);
			
			if(printres){
				output+=("["+defbt+"]~"+"[\""+bt+"\"->0x"+Integer.toHexString(newbt)+"]");
				output+="\n";
			}
			
			defvl.add(defbt);
			newvl.add(newbt);
		}
		defaults[0] = defvl.toArray(tmp1);
		newvals[0] = newvl.toArray(tmp2);
		
		int[][]a=new int[defaults.length][defaults[0].length];
		int[][]b=new int[newvals.length][newvals[0].length];
		
		
		
		DOLPatch patch = new DOLPatch(patchName, pointer, pnt, a, b);
		
		if(printres){
			System.out.println("=====Start~~Code====="+"\n");
			System.out.println(output);
			System.out.println("=====End~~Code====="+"\n");
		}
		
		return patch;
	}
	
	public int getEndOfGeckoChunk(){
		int pointer = 0x003F7124;
		FileIO.initDOL();
		//Default pointer for gecko support
		FileIO.setPosition(pointer);
		
		
		while(true){
			FileIO.setPosition(pointer);
			if(FileIO.readByte()==0xF0){
				if(FileIO.readByte()==0x00){
					if(FileIO.readByte()==0x00){
						if(FileIO.readByte()==0x00){
							FileIO.setPosition(pointer);
							break;
						}
					}
				}
			}
			pointer++;
		}
		
		System.out.println("End of gecko codes @0x"+Integer.toHexString(FileIO.f.position()));
		
		return 0x3f7123;
	}
	
	
	public int[] getPointersForCode(DOLPatch code){
		int[] res = new int[]{-1};
		FileIO.initDOL();
		int place = 0;
		int off = 0;
		boolean isCodePlacedHere=true;
		//while(place < FileIO.f.limit()){
			//FileIO.setPosition(place);
			
			for(int p = 0; p < code.pointers.length; p ++){
				int pointer = code.pointers[p];
				for(int i = 0; i < code.newValues.length; i ++){
					for(int j = 0; j < code.newValues[i].length; j ++){
						FileIO.setPosition(pointer+j);
						if(FileIO.readByte()==code.newValues[i][j]){
							
						}
						else{
							isCodePlacedHere=false;
							break;
						}
					}
					if(!isCodePlacedHere){
						break;
					}
					off += code.newValues[i].length;
				}
			}
			
			//FileIO.setPosition(place);
			
			//place++;
	//	}
			
			if(isCodePlacedHere){
				res = code.pointers;
				System.out.println("Code found @0x"+Integer.toHexString(code.pointers[0]));
			}
		
		return res;
	}
	
	public void addToPatchList(DOLPatchDisplayNode patch){
		patchList.add(patch);
		patchOffset+=patch.patch.newValues[0].length;
		System.out.println("Next patch will start @ 0x"+Integer.toHexString(patchOffset));
		panetst.add(patch);
		updateUI();
	}
	
	public void finalizePatches(){
		FileIO.initDOL();
		FileIO.setPosition(0x3F7124);
		int i;
		for(i=0;i<geckoStart.length;i++){
			//FileIO.writeByte(geckoStart[i]);
			//System.out.println(Integer.toHexString(FileIO.f.position())+"["+Integer.toHexString(geckoStart[i])+"]HONK");
		}
		
		int tmpSize=0;
		
		int[] docode = {0x00,0xD0,0xC0,0xDE};
		int[] fo = {0xF0,0x00,0x00,0x00};
		
		System.out.println(Integer.toHexString(FileIO.f.position())+"HONK");
		int start = 0x3F7128;
		int patchIndex = 0;
		FileIO.f.position(start);
		
		for(int c=0;c<2;c++){
			for(i = 0; i < docode.length; i ++){
				//FileIO.writeByte(docode[i]);
			}
		}
		
		ArrayList<DOLPatchDisplayNode> patches = new ArrayList<DOLPatchDisplayNode>();
		
		for(DOLPatchDisplayNode n : patchList){
			if(n.shouldApply.isSelected()){
				patches.add(n);
			}
		}
		
		for(DOLPatchDisplayNode patch : patches){
			
			System.out.println("PATCHBEFORE"+Integer.toHexString(patch.patch.defaultPointer));
			
			//if(patchIndex<patches.size()){
			for(i = 0; i < docode.length; i ++){
				FileIO.writeByte(docode[i]);
			}
			tmpSize+=4;
			//For 00 D0 C0 DE
			
		//	}else System.out.println("Stopped at index "+patchIndex);
			
			
			
			patch.patch.defaultPointer=start+(tmpSize);
			System.out.println("PATCHAFTER"+Integer.toHexString(patch.patch.defaultPointer));
			patch.applyPatch();
			tmpSize+=patch.patch.newValues[0].length;
			//For F0 00 00 00
			//tmpSize+=4;
			for(i = 0; i < fo.length; i ++){
				//FileIO.writeByte(fo[i]);
			}
			
			patchIndex ++;
			
		}
		//Close up the gecko section
		
		
		FileIO.writeByte(0xF0);
		FileIO.writeByte(0x00);
		FileIO.writeByte(0x00);
		FileIO.writeByte(0x00);
		FileIO.writeByte(0x00);
		FileIO.writeByte(0x00);
		FileIO.writeByte(0x00);
		FileIO.writeByte(0x00);
		
		//
		
		
		System.out.println("Pinching patches @0x"+ Integer.toHexString(FileIO.f.position()) );
		//FileIO.setPosition(endingPointer);
		
		for(i=0;i<geckoEnd.length;i++){
			//FileIO.writeByte(geckoEnd[i]);
		}
		
					//Save the changes to FileIO
		        	try {
		        		FileIO.loadedISOFile.reload();
						FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), FileIO.f.array());
						FileIO.loadedISOFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        	
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		System.out.println("Gecko support before DOL operations:"+isGeckoSupported());
		if(cmd=="convertcode"){
			JFrame frame = new JFrame();
			frame.setTitle("Add code");
			Dimension dim = new Dimension(800,600);
			frame.setPreferredSize(dim);
			frame.setMaximumSize(dim);
			frame.setMinimumSize(dim);
			frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
			
			DOLPatchCreationPane node = new DOLPatchCreationPane(this,false);
			
			frame.add(node);
			frame.setVisible(true);
		}
		else if(cmd=="finalize"){
			finalizePatches();
		}
		
		if(cmd=="replacegecko"){
			try{
				FileIO.initDOL();
				File fi = new File("def/102/ssbmgecko.dol");
				if(!fi.exists()){System.out.println("Tried to load a file that didn't exist!");return;}
				
				RandomAccessFile raf = new RandomAccessFile(fi, "r");
				
				byte[] characterFileData = new byte[(int)raf.length()];
				raf.readFully(characterFileData);
				
				ByteBuffer charData = ByteBuffer.wrap(characterFileData);
				charData.position(0);
				FileIO.f.position(0);
				FileIO.f = charData;
				FileIO.loadedISOFile.reload();
				FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), charData.array());
				
				
				FileIO.loadedISOFile.close();
				raf.close();
				
				FileIO.loadedISOFile.reload();
				FileIO.initDOL();
				}
				catch(Exception e){;}
		}
		
		if(cmd=="savecodes"){
			String res = "";
			
			String codeStart="[CODE]";
			String codeEnd="[/CODE]\n";
			String patchStart = "[PATCH]\n";
			String patchEnd = "[/PATCH]\n";
			String patchName = "[PATCHNAME=";
			
			for(DOLPatchDisplayNode node : patchList){
				DOLPatch patch = node.patch;
				String tmp = patchStart;
				
				tmp += patchName+patch.name+"]\n";
				
				tmp += codeStart;
				
				tmp+="\n";
				
				for(int i = 0; i < patch.newValues[0].length; i ++){
					if(patch.newValues[0][i]<=0xF){
						tmp+="0";
					}
					tmp += Integer.toHexString(patch.newValues[0][i]).toUpperCase();
					if((i+2) % 8 == 1){
						tmp +="\n";
					}
					else if((i+2) % 4 == 1){
						tmp +=" ";
					}
				}
				
				tmp += codeEnd;
				
				tmp += patchEnd;
				
				
				res += tmp;
				System.out.println(tmp);
			}
			
			
			Options.saveCodeList(res);
		}
		
		if(cmd=="loadcodes"){
			String codeStart="[CODE]";
			String codeEnd="[/CODE]";
			String patchStart = "[PATCH]";
			String patchEnd = "[/PATCH]";
			String patchName = "[PATCHNAME=";
			
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
					"TXT Files", "txt");
			final JFileChooser fc = new JFileChooser();

			fc.setCurrentDirectory(new File("options.cfg"));
			fc.addChoosableFileFilter(txtFilter);
			fc.setFileFilter(txtFilter);
			
			
			int returnVal = fc.showOpenDialog(MeleeEdit.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File fi = fc.getSelectedFile();
				
				String tmpName="";
				String code="";
				int tmpSize=0;
				int startingPointer=0x3F7124;
				
				int[][] defvals=new int[1][1],newvals = new int[1][1];
				System.out.println("Reading codes...");
				//Read codes
				try {
					List<String> lines = Files.readAllLines(fi.toPath(), StandardCharsets.UTF_8);
					boolean readingCodes = false;
					System.out.println(lines.size());
					for(int i = 0; i < lines.size(); i ++)
					{
						String o = lines.get(i).replaceAll("\n", "");
						
						
						
						if(o.equalsIgnoreCase(patchStart)){
							System.out.println("Patch start!");
						}
						else if(o.toUpperCase().startsWith(patchName.toUpperCase())){
							String[] split = o.split("PATCHNAME=");
							tmpName=split[1].replaceAll("]", "");
							System.out.println("Patch name is "+tmpName);
						}
						else if(o.equalsIgnoreCase(codeStart)){
							readingCodes = true;
							System.out.println("Reading patch code...");
						}
						else if(o.equalsIgnoreCase(patchEnd)){
							
							System.out.println(code);
							
							DOLPatchCreationPane pane = new DOLPatchCreationPane(this, false);
							pane.code.setText(code);
							pane.patchName.setText(tmpName);
							pane.addCode();
							//DOLPatch patch = new DOLPatch(patchName, startingPointer+tmpSize, new int[]{}, defvals, newvals);
							
							//DOLPatchDisplayNode node = new DOLPatchDisplayNode(this, patch);
							readingCodes = false;
							code = "";
							tmpName="";
							System.out.println("Patch parsed successfully!");
						}
						else if(o.equalsIgnoreCase(codeEnd)){
							readingCodes = false;
							System.out.println("Done reading patch code!");
						}
						
						else if(readingCodes){
							o = o.replaceAll(" ", "");
							code+=o;
						}
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				
			}
			
		}
		
		
		if(cmd=="clearcodes"){
			patchList.clear();
			panetst.removeAll();
			updateUI();
		}
		
		//Ripped from testing versions
		
				if(cmd.equals("fixdol")){
					FileIO.initDOL();
					try {
						FileIO.loadedISOFile.reload();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					File backupCopyFile = new File("def/102/Start.dol");
					ISOFile f = FileIO.loadedISOFile.getFileSystem()
							.getISOFile("Start.dol");
					
					try {
						FileIO.isoFileSystem.replaceFile(f, Files.readAllBytes(backupCopyFile.toPath()));
						System.out.println("DOL restored!");
					} catch (IOException e) {
						e.printStackTrace();
					}
					FileIO.loadedISOFile.close();
				}
				if(cmd.equals("savemain")){
					FileIO.initDOL();
					
						File fi = new File("testDOL.dol");
						String filePath;
						System.out.println("SAVE:Start.dol");
							try{
								
								filePath = fi.getAbsolutePath();
								if(!filePath.endsWith(".dol")) {
								    fi = new File(filePath + ".dol");
								}
								
								if(!fi.exists()){
										fi.createNewFile();
								}
								
								RandomAccessFile raf = new RandomAccessFile(fi, "rw");
								FileIO.setPosition(0);
								raf.write(FileIO.f.array());
								raf.close();
							}
							catch(Exception e){}
				}
				if(cmd.equals("replacemain")){
					JFileChooser characterChooser;
					characterChooser = new JFileChooser();
					System.out.println("LOAD:Start.dol");
					FileNameExtensionFilter datFilter = new FileNameExtensionFilter(
							"DOL Files", "dol");
					characterChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
					characterChooser.addChoosableFileFilter(datFilter);
					characterChooser.setFileFilter(datFilter);
				characterChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		
				
				int returnVal = characterChooser.showOpenDialog(MeleeEdit.frame);
		
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try{
						FileIO.initDOL();
						File fi = characterChooser.getSelectedFile();
						if(!fi.exists()){System.out.println("Tried to load a file that didn't exist!");return;}
						
						RandomAccessFile raf = new RandomAccessFile(fi, "r");
						
						byte[] characterFileData = new byte[(int)raf.length()];
						raf.readFully(characterFileData);
						
						ByteBuffer charData = ByteBuffer.wrap(characterFileData);
						charData.position(0);
						FileIO.f.position(0);
						FileIO.f = charData;
						FileIO.loadedISOFile.reload();
						FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), charData.array());
						
						
						FileIO.loadedISOFile.close();
						raf.close();
						
						FileIO.loadedISOFile.reload();
						FileIO.initDOL();
						}
						catch(Exception e){;}
					}
				}
		
				System.out.println("Gecko support after DOL operations:"+isGeckoSupported());
	}

}
