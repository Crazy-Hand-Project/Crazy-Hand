package com.dolManagement;

import static com.Character.BOWSER_ID;
import static com.Character.FALCON_ID;
import static com.Character.DRMARIO_ID;
import static com.Character.DK_ID;
import static com.Character.FALCO_ID;
import static com.Character.FOX_ID;
import static com.Character.GANON_ID;
import static com.Character.GAMEWATCH_ID;
import static com.Character.GIGABOWSER_ID;
import static com.Character.JIGGLYPUFF_ID;
import static com.Character.KIRBY_ID;
import static com.Character.LINK_ID;
import static com.Character.LUIGI_ID;
import static com.Character.MARIO_ID;
import static com.Character.MARTH_ID;
import static com.Character.MASTERHAND_ID;
import static com.Character.MEWTWO_ID;
import static com.Character.NANA_ID;
import static com.Character.NESS_ID;
import static com.Character.PEACH_ID;
import static com.Character.PICHU_ID;
import static com.Character.PIKACHU_ID;
import static com.Character.POPO_ID;
import static com.Character.ROY_ID;
import static com.Character.SAMUS_ID;
import static com.Character.SANDBAG_ID;
import static com.Character.SHEIK_ID;
import static com.Character.WIREBOY_ID;
import static com.Character.WIREGIRL_ID;
import static com.Character.YOSHI_ID;
import static com.Character.YOUNGLINK_ID;
import static com.Character.ZELDA_ID;
import isotool.filesystem.ISOFileSystem;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.Character;
import com.FileIO;

@SuppressWarnings("unused")
public class SubactionInterruptEditPane extends JPanel implements ActionListener {
	
	int bowserBaseOff=0x3CBDD0-0x20;
	int nessBaseOff=0x3C96A0+0x160-0x20;//TODO remove +0x80, add yoyo subactions
	
	public InterruptNode[] bowserInterruptsList={
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Neutral-B start"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Neutral-B loop"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Neutral-B end"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Neutral-B start"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Neutral-B loop"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Neutral-B end"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Side-B start"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Side-B hit"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Side-B hit(2)"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Side-B end forward"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Side-B end backward"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Side-B start"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Side-B hit"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Side-B hit(2)"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Side-B end forward"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Side-B end backward"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Up-B"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Up-B"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Ground)Down-B"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "(Air)Down-B"),
			new InterruptNode(BOWSER_ID, bowserBaseOff+=0x20, "Down-B landing"),
	};
	
	public InterruptNode[] nessInterruptsList ={
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Neutral-B Start"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Neutral-B Hold"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Neutral-B Hold(2)"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Neutral-B End"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Neutral-B Start"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Neutral-B Hold"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Neutral-B Hold(2)"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Neutral-B End"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Side-B"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Side-B"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Up-B Start"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Up-B Hold"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Up-B End"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "Up-B"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Up-B Start"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Up-B Hold"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Up-B End"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "Up-B(2)"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "DamageFall"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Down-B Start"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Down-B Hold"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Down-B Hit"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Ground)Down-B End"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Down-B Start"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Down-B Hold"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Down-B Hit"),
			new InterruptNode(NESS_ID, nessBaseOff+=0x20, "(Air)Down-B End"),
	};
	
	
	public InterruptOption[] interruptOptionsList={
			new InterruptOption(new int[]{0x80,0x0C,0xAE,0xD0}, "Jump Cancel(Grounded)"),
			new InterruptOption(new int[]{0x80,0x0C,0xB8,0x70}, "Jump Cancel(Aerial)"),
			new InterruptOption(new int[]{0x80,0x0C,0xCD,0x34}, "Interruptible by any fall interrupt(?)"),
			new InterruptOption(new int[]{0xD0,0xC0,0xDE,0xFF}, "Restore to default interrupt")
	};
	
	
	public SubactionInterruptEditPane() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(new SubactionInterruptSelectionBox());
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		
	}
	
	class SubactionInterruptSelectionBox extends JPanel implements ActionListener{
		
		JComboBox charSelect,nodeSelect,optionSelect;
		int selectedCharacter=BOWSER_ID,selectedNode,selectedOption;
		
		public SubactionInterruptSelectionBox(){
			
			String[] tmp = new String[Character.characters.length];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = ""+i;
			}

			charSelect = new JComboBox(tmp);
			charSelect.setSelectedIndex(0);
			charSelect.setEditable(false);
			
			ComboBoxRenderer renderer = new ComboBoxRenderer();
			renderer.setPreferredSize(new Dimension(64,58));
			charSelect.setRenderer(renderer);
			charSelect.setPreferredSize(new Dimension(100,70));
			charSelect.setMaximumSize(charSelect.getPreferredSize());
			charSelect.addActionListener(this);
			charSelect.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			
			nodeSelect=new JComboBox(tmp);
			nodeSelect.addActionListener(this);
			nodeSelect.setActionCommand("nodeselect");
			
			int sz = interruptOptionsList.length;
			String[] tmp2 = new String[sz];
			int c = 0;
			for(InterruptOption o : interruptOptionsList){
				tmp2[c]=o.name;
				c++;
			}
			
			optionSelect=new JComboBox(tmp2);
			optionSelect.addActionListener(this);
			optionSelect.setActionCommand("optionselect");
			
			Box hbox = Box.createHorizontalBox();
			
			hbox.add(charSelect);
			hbox.add(Box.createHorizontalStrut(5));
			
			hbox.add(nodeSelect);
			hbox.add(Box.createHorizontalStrut(5));
			
			hbox.add(optionSelect);
			hbox.add(Box.createHorizontalStrut(5));
			
			this.add(hbox);
			
			refresh();
		}
		
		public void refresh(){
			selectedCharacter=charSelect.getSelectedIndex();
			selectedNode=nodeSelect.getSelectedIndex();
			selectedOption=optionSelect.getSelectedIndex();
			
			
			this.removeAll();
			
			updateNodeSelectBox();
			
			charSelect.setSelectedIndex(selectedCharacter);
			nodeSelect.setSelectedIndex(selectedNode);
			optionSelect.setSelectedIndex(selectedOption);
			
			Box hbox = Box.createHorizontalBox();
			
			hbox.add(charSelect);
			hbox.add(Box.createHorizontalStrut(5));
			
			hbox.add(nodeSelect);
			hbox.add(Box.createHorizontalStrut(5));
			
			hbox.add(optionSelect);
			hbox.add(Box.createHorizontalStrut(5));
			
			Box vbox = Box.createVerticalBox();
			vbox.add(hbox);
			JButton button = new JButton("Save");
			button.setActionCommand("save");
			button.addActionListener(this);
			vbox.add(button);
			
			this.add(vbox);
			
		}
		
		public void updateNodeSelectBox(){
			
			System.out.println("Updating node selection box...");
			
			InterruptNode[] nodes=null;
			String[] res = new String[1];
			ArrayList<String> list = new ArrayList<String>();
			
			nodes= getNodeListForCharacter(selectedCharacter);
			
			for(InterruptNode n : nodes){
				list.add(n.subactionName);
			}
			
			res=list.toArray(res);
			System.out.println(res[0]);
			nodeSelect.removeAllItems();
			for(String s : list){
				nodeSelect.addItem(s);
			}
			nodeSelect.setSelectedIndex(selectedNode);
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			
			int ctmp=selectedCharacter;
			
			selectedCharacter=charSelect.getSelectedIndex();
			selectedNode=nodeSelect.getSelectedIndex();
			selectedOption=optionSelect.getSelectedIndex();
			
			System.out.println("MOVEINTERRUPT:["+selectedCharacter+"|"+selectedNode+"|"+selectedOption+"]");
			
			if(cmd=="save"){
				try {
					applyChange(getNodeListForCharacter(selectedCharacter)[selectedNode], interruptOptionsList[selectedOption]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(ctmp!=selectedCharacter){
				updateNodeSelectBox();
			}
		}
	
		private InterruptNode[] getNodeListForCharacter(int s) {
			switch(selectedCharacter){
			case BOWSER_ID :
				return bowserInterruptsList;
			case NESS_ID :
				return nessInterruptsList;
			}
			
			return null;
		}

		public void applyChange(InterruptNode node, InterruptOption IO) throws IOException{
			FileIO.initDOL();
			
			FileIO.loadedISOFile.reload();
			
			boolean resetFlag = IO.data[0]==0xD0&&IO.data[1]==0xC0&&IO.data[2]==0xDE&&IO.data[3]==0xFF;
			
			if(!resetFlag){
				FileIO.setPosition(node.pointer);
				for(int i = 0; i < IO.data.length; i ++){
					FileIO.writeByte(IO.data[i]);
				}
			}else{
				RandomAccessFile raf = new RandomAccessFile("def/102/Start.dol", "r");
				
				byte[] bytes = new byte[1];
				//raf.readFully(bytes);
				//System.out.println("MOVEINTERRUPT:RAFL=["+raf.length()+"]BYTARRL=["+bytes.length+"]");			
				
				raf.skipBytes(node.pointer);
				FileIO.setPosition(node.pointer);
				
				for(int i = 0; i < IO.data.length; i ++){
					
					int val = 0;
					val = raf.read();
					int byt =  ((short) (val & 0xff));
					
					System.out.println("MOVEINTERRUPT:FILEIO @0x"+Integer.toHexString(FileIO.f.position())+" DEFINTVAL=["+Integer.toHexString(byt)+"]@LOC:0x"+Integer.toHexString((int) raf.getFilePointer()-1));
					
					FileIO.writeByte(byt);
				}
			}
			
			
			
			
			FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), FileIO.f.array());
			FileIO.loadedISOFile.close();
			FileIO.initDOL();
			
		}
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
	
	class InterruptNode{
		int charId;
		int pointer;
		String subactionName;
		public InterruptNode(int cid, int poi, String s){
			charId=cid;pointer=poi;subactionName=s;
		}
	}

}
