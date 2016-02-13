package com.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.Character;
import com.FileIO;
import com.MeleeEdit;
import com.TextReader;


public class SpecialAttributePanel extends JPanel{
	
	public JTable attributeTable2;
	public JScrollPane SApane;

	
	public SpecialAttributePanel(){
		//super();//TODO look up when super is and isn't needed

		
		attributeTable2 = new JTable(new SpecialAttributeTable());
		update();
		attributeTable2.getColumnModel().getColumn(0).setMaxWidth(150);//TODO scale this with window size
		attributeTable2.getColumnModel().getColumn(0).setMinWidth(100);//TODO these are both pretty jank right now.
		attributeTable2.getColumnModel().getColumn(2).setMaxWidth(75);
		
		//attributeTable2.setRowHeight(17, 100);
		SApane = new JScrollPane(attributeTable2);
		SApane.setPreferredSize(new Dimension(600, 500));

		add(SApane);
	}
	
	public void update() {
	}
	
	public void save(){
		String[][] data = TextReader.getData("vals/characterSpecificAttributes/" + Character.characters[MeleeEdit.selected].name + " Attributes.txt",5);
		for(int i = 0; i < data.length; i ++){
			FileIO.f.position(Integer.decode(data[i][0]));
			if(data[i][3].equals("I")){
				
				int val = ((Number)attributeTable2.getValueAt(i, 0)).intValue();
				FileIO.f.putInt(val);
			}
			else{
				FileIO.f.putFloat((float) attributeTable2.getValueAt(i, 0));
			}
		}
		
		FileIO.finalizeSave();
	}
	
	class SpecialAttributeTable extends AbstractTableModel {
		public String[] columnNames = {"Value","Attribute", "Used for", "Info"};
		public Object[][] data = initGrid();
		
		
		public Object[][] initGrid() {
			FileIO.init();
			String[][] data = TextReader.getData("vals/characterSpecificAttributes/" + Character.characters[MeleeEdit.selected].name + " Attributes.txt",5);
			

			System.out.println("Loading special move attributes");
			Object[][] tmp = new Object[data.length][4];
			for (int i = 0; i < data.length; i++) {
				tmp[i][1] = data[i][1];

				FileIO.setPosition(Integer.decode(data[i][0]));
				if(data[i][3].equals("I")){
					tmp[i][0] = Float.parseFloat(""+FileIO.readInt());
				}
				else{
					tmp[i][0] = FileIO.readFloat();
				}
					
				tmp[i][2] = data[i][4];

				tmp[i][3] = data[i][2];
					
				if(data[i][3].equals("I")){
					tmp[i][3] += " (Integer values only!)";
				}	
					
			}
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

