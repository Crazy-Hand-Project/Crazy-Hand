package com.panels;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.Character;
import com.FileIO;
import com.MeleeEdit;
import com.TextReader;






public class PlCoPanel extends JPanel{
	//TODO this might not ending up being a panel?
	
	
	public JTable attributeTable;
	public static JScrollPane aPane;
	
	
	static int[] pointers;
	static String[] names;
	static boolean[] floats;
	
	
	public static void init(){
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
	}
	
	
	
	
	
	public PlCoPanel(){
		
		FileIO.init();
		
		attributeTable = new JTable(new AttributeTable());
		update();
		attributeTable.getColumnModel().getColumn(0).setMaxWidth(300);//TODO scale this with window size
		attributeTable.getColumnModel().getColumn(0).setMinWidth(200);//TODO these are both pretty jank right now.
		
		
		// Create the scroll pane and add the table to it.
		aPane = new JScrollPane(attributeTable);
		aPane.setPreferredSize(new Dimension(500, 500));
		//aPane.setBorder(BorderFactory.createEmptyBorder(3, 125, 3, 125));//TODO make these values scale with window size so the table is always the same width
		add(aPane);
				
				
				
	}
	
	public  void update() {
		FileIO.initPlCo();
		
		for (int i = 0; i < PlCoPanel.pointers.length; i++) {
			FileIO.setPosition(pointers[i]);
			if(floats[i]==true){
				attributeTable.setValueAt(FileIO.readFloat(), i, 0);
			}
			else{
				attributeTable.setValueAt(FileIO.readInt(), i, 0);
			}
			
			
			attributeTable.setValueAt(names[i], i, 1);
		}
	}
	
	public void save() {
		FileIO.initPlCo();
		for (int i = 0; i < PlCoPanel.pointers.length; i++) {
			System.out.println(i);
			FileIO.setPosition(pointers[i]);
			if(floats[i]==true){
				FileIO.writeFloat((float)attributeTable.getValueAt( i, 0));

			}
			else{
				FileIO.writeInt(((Number)attributeTable.getValueAt(i,0)).intValue());
				
			}

		}
		
		
		//FileIO.f.position(Character.characters[MeleeEdit.selected].offset);
		//for (int i = 0; i < PlCoPanel.attributes.length; i++) {
		///	FileIO.f.putFloat((float) attributeTable.getValueAt(i, 0));
		//}
		
		FileIO.finalizeSave();
	}
	
	
	
	public class AttributeTable extends AbstractTableModel {
		public String[] columnNames = { "Value", "Attribute",};
		public Object[][] data = initGrid();

		public Object[][] initGrid() {
			
			PlCoPanel.init();
			
			FileIO.setPosition(Character.characters[MeleeEdit.selected].offset);
			Object[][] tmp = new Object[PlCoPanel.pointers.length][2];
			
			return tmp;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			if (col == 0)
				return true;
			else
				return false;
		}

		public void setValueAt(Object value, int row, int col) {
			if (value == null)
				return;
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		
	}
	
	

}
