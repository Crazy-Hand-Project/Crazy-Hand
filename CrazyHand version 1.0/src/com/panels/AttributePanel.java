package com.panels;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.Character;
import com.FileIO;
import com.MeleeEdit;
import com.TextReader;






public class AttributePanel extends JPanel{
	
	
	public JTable attributeTable;
	public static JScrollPane aPane;
	public static String[] attributes;
	
	
	public static void initAttributes(){
		
		String[][] data = TextReader.getData("vals/attributes.txt",1);
		attributes = new String[data.length];
		for(int i = 0; i < data.length; i++){
			attributes[i] = " " + data[i][0];
		}
	}
	
	
	
	
	
	public AttributePanel(){
		
		FileIO.init();
		
		attributeTable = new JTable(new AttributeTable());
		attributeTable.addMouseListener(new Listener(0));
		
		
		update();
		//attributeTable.getColumnModel().getColumn(0).setMaxWidth(300);//TODO scale this with window size
		//attributeTable.getColumnModel().getColumn(0).setMinWidth(200);//TODO these are both pretty jank right now.
		
		
		// Create the scroll pane and add the table to it.
		aPane = new JScrollPane(attributeTable);
		//aPane.setPreferredSize(new Dimension(500, 500));
		//aPane.setBorder(BorderFactory.createEmptyBorder(3, 125, 3, 125));//TODO make these values scale with window size so the table is always the same width
		add(aPane);
				
				
				
	}
	
	
	
	public  void update() {
		FileIO.init();
		FileIO.setPosition(Character.characters[MeleeEdit.selected].offset);
		for (int i = 0; i < AttributePanel.attributes.length; i++) {
			attributeTable.setValueAt(FileIO.readFloat(), i, 0);

		}
	}
	
	public void save() {
		FileIO.f.position(Character.characters[MeleeEdit.selected].offset);
		for (int i = 0; i < AttributePanel.attributes.length; i++) {
			FileIO.f.putFloat((float) attributeTable.getValueAt(i, 0));
		}
		
		FileIO.finalizeSave();
	}
	
	
	
	public class AttributeTable extends AbstractTableModel {
		public String[] columnNames = { "Value", "Attribute",};
		public Object[][] data = initGrid();

		public Object[][] initGrid() {
			
			
			FileIO.setPosition(Character.characters[MeleeEdit.selected].offset);//TODO probably not needed line of code
			Object[][] tmp = new Object[AttributePanel.attributes.length][3];
			for (int i = 0; i < AttributePanel.attributes.length; i++) {
				//System.out.println(AttributePanel.attributes[i]);
				tmp[i][1] = AttributePanel.attributes[i];
				tmp[i][0] = -1;
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





// this is only used to track the save button!
// attributeTable.addMouseListener(
class Listener implements MouseListener {
	public int column,loggedRow=-1;
	public float loggedValue = 0;
	public Listener(int col){
		super();
		column = col;
	}

	public void mouseClicked(MouseEvent e) {
		JTable target = (JTable) e.getSource();
		if(loggedRow>0){
			if(loggedValue != (float)target.getValueAt(loggedRow, column)){
				MeleeEdit.toolBar.setButton(true);
			}
		}
		

					

		loggedRow =  target.getSelectedRow();
		loggedValue = (float)target.getValueAt(target.getSelectedRow(),column);
		System.out.println(loggedRow);
		System.out.println(loggedValue);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
