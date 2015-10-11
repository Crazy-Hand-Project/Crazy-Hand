package com.dolManagement;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.text.MaskFormatter;

import com.Character;
import com.FileIO;
import com.MeleeEdit;
@SuppressWarnings("unused")
public class MoveLogicEditPane extends JPanel implements ActionListener {
	
	//Formula is (very start of move interrupt table)+0x14(bytes down to first subaction interrupt line), then += 0x20 for each subaction added
	
	/*
	int bowserBaseOff=0x3CBDC0+0x14;//-0x20;
	int captainFalconBaseOff=0x3C42B8+0x14;
	int nessBaseOff=0x3C96A0;//-0x20;//TODO remove +0x80, add yoyo subactions
	*/
	
	/*
	 * All??? ......... 3C4160
Captain Falcon . 3C42B8
Fox ............ 3C4788
Link ........... 3C4E18
Kirby .......... 3C5368
Donkey Kong .... 3C8838
Sheik .......... 3C9060
Ness ........... 3C9690
Peach .......... 3C9CB8
Popo ........... 3CA2D0
Nana ........... 3CA838
Pikachu ........ 3CAD78
Samus .......... 3CB2D0
Yoshi .......... 3CB770
Bowser ......... 3CBDC0
Marth .......... 3CC420
Zelda .......... 3CCA58
Jigglypuff ..... 3CCEF0
Luigi .......... 3CD628
Mewtwo ......... 3CDB00
Young Link ..... 3CDFA0
Dr Mario ....... 3CE4D8
Falco .......... 3CE848
Pichu .......... 3CEEA8
Ganondorf ...... 3CF568
Roy ............ 3CF380
M Wireframe ....
F Wireframe ....
Giga Bowser .... 3D05E8
Sandbag ........ 3D0998
Master Hand .... 3D0A30
Crazy Hand ..... 3D11F8
	 * 
	 */
	
	
	int[] charOffsets={
		0x3CBDC0,//Bowser
		0x3C42B8,//Capt. Falcon
		0x3CE4D8,//Dr. Mario
		0x3C8838,//DK
		0x3CE848,//Falco
		0x3C4788,//Fox
		0x3CF3E8,//Mr. Game & Watch
		0x3CFAB8,//Ganondorf
		0x3CA2D0,//Popo
		0x3CA838,//Nana
		0x3CCEF0,//Jigglypuff
		0x3C5368,//Kirby(Unused)
		0x3CD628,//Luigi
		0x3C4E18,//Link
		0x3C4160,//Mario(?)
		0x3CC420,//Marth
		0x3CDB00,//Mewtwo
		0x3C9690,//Ness
		0x3C9CB8,//Peach
		0x3CEEA8,//Pichu
		0x3CAD78,//Pikachu
		0x3CF380,//Roy
		0x3CB2D0,//Samus
		0x3C9060,//Sheik
		0x3CB770,//Yoshi
		0x3CDFA0,//Young Link
		0x3CCA58,//Zelda
	};
	
	int[][] charNames = {								//.    d    a    t
														//0x2E,0x64,0x61,0x74
			new int[]{0x50,0x6C,0x4B,0x70},//Bowser
			new int[]{0x50,0x6C,0x43,0x61},//Capt. Falcon
			new int[]{0x50,0x6C,0x44,0x72},//Dr. Mario
			new int[]{0x50,0x6C,0x44,0x6B},//DK
			new int[]{0x50,0x6C,0x46,0x63},//Falco
			new int[]{0x50,0x6C,0x46,0x78},//Fox
			new int[]{0x50,0x6C,0x47,0x77},//Game and watch
			new int[]{0x50,0x6C,0x47,0x6E},//Ganondorf
			new int[]{0x50,0x6C,0x50,0x70},//Popo
			new int[]{0x50,0x6C,0x4E,0x6E},//Nana
			new int[]{0x50,0x6C,0x50,0x72},//Jigglypuff
			new int[]{0x50,0x6C,0x4B,0x62},//Kirby
			new int[]{0x50,0x6C,0x4C,0x67},//Luigi
			new int[]{0x50,0x6C,0x4C,0x6B},//Link
			new int[]{0x50,0x6C,0x4D,0x72},//Mario
			new int[]{0x50,0x6C,0x4D,0x73},//Marth
			new int[]{0x50,0x6C,0x4D,0x74},//Mewtwo
			new int[]{0x50,0x6C,0x4E,0x73},//Ness
			new int[]{0x50,0x6C,0x50,0x65},//Peach
			new int[]{0x50,0x6C,0x50,0x63},//Pichu
			new int[]{0x50,0x6C,0x50,0x6B},//Pikachu
			new int[]{0x50,0x6C,0x46,0x65},//Roy
			new int[]{0x50,0x6C,0x53,0x73},//Samus
			new int[]{0x50,0x6C,0x53,0x6B},//Sheik
			new int[]{0x50,0x6C,0x59,0x73},//Yoshi
			new int[]{0x50,0x6C,0x43,0x6C},//Young Link
			new int[]{0x50,0x6C,0x5A,0x64},//Zelda
			
	};
		int selectedNode,selectedOption;
		
		JFormattedTextField[] hexFields;
		
		public MoveLogicEditPane(){
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			String[] tmp = new String[Character.characters.length];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = ""+i;
			}
			
	        MaskFormatter formatter = createHexadecimalMaskFormatter();
	        
	        hexFields = new JFormattedTextField[0x20];
	        
	        hexPanelBox = Box.createVerticalBox();
	        
	        for(int i = 0; i < hexFields.length; i ++){
				hexFields[i] = new JFormattedTextField(formatter);
				hexFields[i].setEditable(true);
				hexFields[i].setValue("00 00 01 3F");
				hexFields[i].setPreferredSize(new Dimension(800,40));
				hexFields[i].setMaximumSize(hexFields[i].getPreferredSize());
				hexFields[i].add(Box.createHorizontalGlue());
				
				hexPanelBox.add(hexFields[i]);
	        }
	        
	        this.add(hexPanelBox);
	        
			Box vbox = Box.createVerticalBox();
			
			Box hbox = Box.createHorizontalBox();
			
			hbox.add(Box.createHorizontalGlue());
			
			vbox.add(hbox);
			
			vbox.add(Box.createVerticalStrut(5));
			
			hbox = Box.createHorizontalBox();
			
			vbox.add(hbox);
			
			hbox = Box.createHorizontalBox();
			
			JButton copyButton = new JButton("Copy interrupt data");
			copyButton.setActionCommand("copyinterruptdata");
			copyButton.addActionListener(this);
			
			hbox = Box.createHorizontalBox();
			//hbox.add(copyButton);
			
			vbox.add(Box.createVerticalStrut(15));
			
			vbox.add(hbox);
			
			vbox.add(Box.createVerticalStrut(15));
			
			
			this.add(vbox);
			
			JScrollPane an = new JScrollPane(hexPanelBox);
			an.getVerticalScrollBar().setUnitIncrement(10);
	        an.setPreferredSize(new Dimension(700,500));
			
	        this.add(an);
	        
			refresh();
		}
		
		public MaskFormatter createHexadecimalMaskFormatter()
		{
			String hexForm = "";
	        
	        for(int i = 0; i < 0x4; i++){
	        	hexForm=hexForm + "HH";
	        	if(i != 0x20-1){
	        		hexForm=hexForm + " ";
	        	}
	        }
	    	
	    	MaskFormatter formatter = null;
	        try {
	            formatter = new MaskFormatter(hexForm);
	        } catch (java.text.ParseException exc) {
	            System.err.println("formatter is bad: " + exc.getMessage());
	            System.exit(-1);
	        }
	        
	        return formatter;
		}
		
		Box hexPanelBox;
		
		public void refresh(){
				try {
					FileIO.loadedISOFile.reload();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				FileIO.initDOL();
				
				LogicNode[] logicTable = getLogicTableForCharacter(MeleeEdit.selected);
				
				FileIO.setPosition(charOffsets[MeleeEdit.selected]);
				
				int chunkAmount=8;//Each "chunk" is four bytes (ex. 00 11 22 33).
				
				hexPanelBox.removeAll();
				
				hexFields = new JFormattedTextField[ (logicTable.length*chunkAmount)];
				MaskFormatter formatter = createHexadecimalMaskFormatter();
				
				String[] indentLabels={
					"Subaction ID",
					"Flags 1",
					"Flags 2",
					"Animation interrupt function",
					"IASA function",
					"Action physics funtion",
					"Collision interrupt",
					"Camera behavior function",
				};
				
				//TODO Make a JTextField for each chunk of the subaction logic
				//so that they can be properly labeled and interacted with.
				
				for(int i = 0; i < logicTable.length; i ++){
					
					Box bax = Box.createVerticalBox();//Box to contain label box and hex box
					Box hb = Box.createHorizontalBox();//Hex box
					Box lb = Box.createHorizontalBox();//Label box
					
					for(int c = 0; c < chunkAmount; c ++){
						
						String data = "";
						
						int placement= c +( (i)*chunkAmount);
						
						
						for(int d = 0; d < 0x4; d ++){
							data+= Integer.toHexString( FileIO.readByte() );
								
							data+= " ";
						}
					
						hexFields[placement] = new JFormattedTextField(formatter);
						hexFields[placement].setEditable(true);
						hexFields[placement].setValue(data);
						
						Box vbox = Box.createVerticalBox();
						
						vbox.add(new JLabel(indentLabels[c]));
						vbox.add(hexFields[placement]);
						
						hb.add(vbox);
						
						lb.add(Box.createHorizontalStrut(10));
						
					}
					
					JLabel label = new JLabel(logicTable[i].subactionName);
					label.add(Box.createHorizontalGlue());
					
					bax.add(Box.createHorizontalGlue());
					lb.add(Box.createHorizontalGlue());
					bax.add(lb);
					bax.add(hb);
					
					lb.setBorder(BorderFactory.createRaisedBevelBorder());
					bax.setBorder(BorderFactory.createRaisedBevelBorder());
					
					
					JButton resetButton=new JButton("*");
					resetButton.setActionCommand("resetsub"+i);
					resetButton.addActionListener(this);
					resetButton.setToolTipText("Reset this field to its default values");
					
					Box op = Box.createHorizontalBox();//Options box
					op.add(label);
					op.add(Box.createHorizontalStrut(10));
					op.add(resetButton);
					
					op.add(Box.createHorizontalGlue());
					
					hexPanelBox.add(op);
					hexPanelBox.add(Box.createVerticalStrut(30));
					hexPanelBox.add(bax);
					hexPanelBox.add(Box.createVerticalStrut(60));
					
				}
				
				FileIO.setPosition(logicTable[0].pointer);
				
				FileIO.initDOL();
				
				try {
					FileIO.loadedISOFile.reload();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				FileIO.setPosition(charOffsets[MeleeEdit.selected]);
				
				
				for(JFormattedTextField txt : hexFields){
					String data = "";
					
						for(int i = 0; i < 0x4; i ++){
							
							int byt = FileIO.readByte();
							
							if(byt<=0xF){
								data+="0";
							}
							
							data+= Integer.toHexString(byt);
							data+= " ";
						}
						
						txt.setValue(data);
						txt.updateUI();
					
				}
				
				FileIO.setPosition(charOffsets[MeleeEdit.selected]);
			
			hexPanelBox.updateUI();
			hexPanelBox.repaint();
			repaint();
			updateUI();
			
			System.out.println("done");
		}
		
		public void updateNodeSelectBox(){
			
			System.out.println("Updating node selection box...");
			
			LogicNode[] nodes=null;
			String[] res = new String[1];
			ArrayList<String> list = new ArrayList<String>();
			
			nodes= getLogicTableForCharacter(MeleeEdit.selected);
			
			for(LogicNode n : nodes){
				list.add(n.subactionName);
			}
			
			res=list.toArray(res);
			System.out.println(res[0]);
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			
			System.out.println("MOVEINTERRUPT:["+MeleeEdit.selected+"|"+selectedNode+"|"+selectedOption+"]");
			
			if(cmd.startsWith("resetsub")){
				int hexFieldToReset = Integer.parseInt(cmd.split("resetsub")[1]);
				
				RandomAccessFile raf;
				try {
					raf = new RandomAccessFile("def/102/Start.dol", "r");
					
					FileIO.initDOL();
					
					byte[] bytes = new byte[1];
					//raf.readFully(bytes);
					//System.out.println("MOVEINTERRUPT:RAFL=["+raf.length()+"]BYTARRL=["+bytes.length+"]");			
					
					int off = hexFieldToReset*0x20;
					
					raf.skipBytes(charOffsets[MeleeEdit.selected]+off);
					FileIO.setPosition(charOffsets[MeleeEdit.selected]+off);
					
					System.out.println(charOffsets[MeleeEdit.selected]+off);
					
					for(int i = 0; i < 0x20; i ++){
						
						int val = 0;
						val = raf.read();
						int byt =  ((short) (val & 0xff));
						
						System.out.println("MOVEINTERRUPT:FILEIO @0x"+Integer.toHexString(FileIO.f.position())+" DEFINTVAL=["+Integer.toHexString(byt)+"]@LOC:0x"+Integer.toHexString((int) raf.getFilePointer()-1));
						
						FileIO.writeByte(byt);
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				FileIO.setPosition(charOffsets[MeleeEdit.selected]);
				refresh();
			}
			
			if(cmd=="save"){
				try {
					applyChanges();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(cmd=="copyinterruptdata"){
				
				String interruptData="";
				
				FileIO.initDOL();
				
				try {
					FileIO.loadedISOFile.reload();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				FileIO.setPosition(charOffsets[MeleeEdit.selected]);
				
				for(int i = 0; i < 0x20; i ++){
					interruptData+=Integer.toHexString( FileIO.readByte() );
					
					interruptData+="'";
				}
				
				
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				 StringSelection selection = new StringSelection(interruptData);
			    clipboard.setContents(selection, selection);
			}
			
			
			updateNodeSelectBox();
			refresh();
			
		}
	
		private LogicNode[] getLogicTableForCharacter(int s) {
			FileIO.init();
			String[] subs = FileIO.getDefaultSubactions();
			
			FileIO.initDOL();
			
			LogicNode[] res = new LogicNode[1];
			ArrayList<LogicNode> nodes = new ArrayList<LogicNode>();
			

			int logicTableEntries=0;
			
			boolean finishedLooking=false;
			
			int off = 0;
			
			FileIO.setPosition(charOffsets[MeleeEdit.selected]);
			
			off=FileIO.f.position();
			
			
			while(!finishedLooking){
				
				//System.out.println("Pre-Check:0x"+Integer.toHexString(FileIO.f.position()));
				
				
				if(FileIO.readByte()==charNames[MeleeEdit.selected][0]){			//p
					if(FileIO.readByte()==charNames[MeleeEdit.selected][1]){		//l
						if(FileIO.readByte()==charNames[MeleeEdit.selected][2]){	//*
							if(FileIO.readByte()==charNames[MeleeEdit.selected][3]){//*
								if(FileIO.readByte()==0x2E){						//.
									if(FileIO.readByte()==0x64){					//d
										if(FileIO.readByte()==0x61){				//a
											if(FileIO.readByte()==0x74){			//t
												finishedLooking=true;
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				
				FileIO.setPosition(off);
				
				//System.out.println("Post-Check:0x"+Integer.toHexString(FileIO.f.position()));
				
				String tmp = ""+Integer.toHexString(FileIO.readByte())+""+Integer.toHexString(FileIO.readByte())+""+Integer.toHexString(FileIO.readByte())+""+Integer.toHexString(FileIO.readByte());
				
				while(tmp.startsWith("0")){
					tmp=tmp.replaceFirst("0", "");
				}
				
				//System.out.println(tmp);
				
				int i = 0;
				
				for(String sub : subs){
					if(sub.toLowerCase().contains("(0x"+tmp)){
						break;
					}
					i++;
				}
				
				boolean reset=false;
				
				if(i>=subs.length){
					i=subs.length-1;
					reset=true;
				}
				
				nodes.add(new LogicNode(MeleeEdit.selected, FileIO.f.position(), subs[i]+( reset==true ? "*" : "") ));
				
				FileIO.setPosition(off+=0x20);
			}
			
			int bytesSearched = FileIO.f.position()-charOffsets[MeleeEdit.selected]-0x8;//-0x8 to account for Pl**.dat check
			
			System.out.println("Bytes searched for logic table:[0x"+Integer.toHexString(bytesSearched) + "] Node table size:"+nodes.size());
			
			res = nodes.toArray(res);
			
			return res;
		}

		public void applyChanges() throws IOException{
			
				FileIO.initDOL();
				
				FileIO.setPosition(charOffsets[MeleeEdit.selected]);
					
				for(int i = 0; i < hexFields.length; i ++){
					int[] data = new int[0x4];
					String[] tmp = hexFields[i].getText().split(" ");
					
					for(int c = 0; c < tmp.length; c ++){
						String s = tmp[c];
						data[c] = Integer.parseInt(s,16);
					}
					//System.out.println(data[0]+"|"+data[1]+"|"+data[2]+"|"+data[3]);
					
					for(int b : data){
						FileIO.writeByte(b);
					}
				}
				FileIO.setPosition(charOffsets[MeleeEdit.selected]);
	}
	
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		
		public ComboBoxRenderer() {
			setOpaque(true);
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(CENTER);
		}
		
		public Component getListCellRendererComponent(
				                    JList list,
				                    Object value,
				                    int index,
				                    boolean isSelected,
				                    boolean cellHasFocus) {
			
			int selectedIndex = Integer.parseInt(((String)value));
			
			if(selectedIndex<0){return this;}
			
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			
			ImageIcon icon = Character.characters[selectedIndex].characterIcon;
			if (icon != null && icon.getImage() != null) {
				
				//Since Kirby has too many moves to be in the cool kids club
				if(selectedIndex==Character.KIRBY_ID||selectedIndex>Character.ZELDA_ID){
					//icon = new ImageIcon("img/icons/"+Character.characters[selectedIndex].name+"2.png");
					//System.out.println(icon.getDescription());
					//this.setToolTipText("Kirby is currently unavailable");
					
				}
				
				setFont(list.getFont());
				setIcon(icon);
			}
			
				return this;
		}
				
	}
	
	class InterruptOption{
		int[] data;
		String name;
		public InterruptOption(int[] i, String s){
			data=i;name=s;
		}
	}
	
	class LogicNode{
		int charId;
		int pointer;
		String subactionName;
		public LogicNode(int cid, int poi, String s){
			charId=cid;pointer=poi;subactionName=s;
		}
	}

}