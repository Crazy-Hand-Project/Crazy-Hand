package com.panels;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.FileIO;
import com.TextReader;

public class PlCoEditPane extends JPanel {
	
	public PlCoEditPane() {
		super();
		this.setPreferredSize(new Dimension(800, 500));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.init();
	}
	
	private plCoNode[] nodes;
	
	public void init(){
		this.removeAll();
		
		
		
		int[] pointers;
		String[] names;
		boolean[] floats;
		
		String[][] data = TextReader.getData("vals/PlCoValues.txt",3);
		pointers = new int[data.length];
		names = new String[data.length];
		floats = new boolean[data.length];
		for(int i = 0; i < data.length; i++){
			pointers[i] = Integer.decode(data[i][0]);
			names[i] = (data[i][1]);
			if(data[i][2].equals("f")){
				floats[i] = true;
			}
			else{
				floats[i] = false;
			}
				
		}
		
		nodes = new plCoNode[pointers.length];
		
		FileIO.initPlCo();
		
		for(int i = 0; i < pointers.length; i ++){
			plCoNode nerd = new plCoNode(pointers[i],names[i],floats[i]);
			nodes[i]=nerd;
			this.add(nerd);
		}
		this.updateUI();
	}
	
	public void save(){
		FileIO.initPlCo();
		for(int i = 0; i < nodes.length; i ++){
			nodes[i].save();
		}
		
		System.out.println("Finished saving PlCo data, re-initializing PlCoEditPanel...");
		init();
	}
	
	class plCoNode extends JPanel{
		private int pointer;
		private boolean isFloat = true;
		private String name="error";
		JFormattedTextField valueBox;
		plCoNode(int p, String s){
			this(p,s,true);
		}
		plCoNode(int p, String s, boolean b){
			this.pointer=p;
			this.name=s;
			this.isFloat=b;
			FileIO.setPosition(pointer);
			this.valueBox= new JFormattedTextField();
			this.valueBox.setValue(FileIO.readFloat());
			this.valueBox.setSize(320, this.valueBox.getHeight());
			this.add(new JLabel(name));
			this.add(valueBox);
			this.setSize(400, 75);
		}
		
		public void save(){
			FileIO.setPosition(pointer);
			if(isFloat){
				System.out.println("0x"+Integer.toHexString(pointer).toUpperCase()+"|"+Float.parseFloat(valueBox.getText()));
				FileIO.writeFloat( Float.parseFloat(valueBox.getText() ) );
			}
			else{
				
			}
		}
	}
}
