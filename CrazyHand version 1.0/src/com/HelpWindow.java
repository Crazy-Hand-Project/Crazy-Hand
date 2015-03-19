package com;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class HelpWindow extends JFrame {
	
	JMenuBar menuBar;
	
	public HelpWindow(){
		super();
		this.setPreferredSize(new Dimension(800,600));
		this.setSize(800, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setEnabled(true);
		this.setVisible(true);
		
		menuBar = new JMenuBar();
		
		JMenu menu1 = new JMenu("Scripts");
		
		
		
		this.setJMenuBar(menuBar);

		this.pack();
	}
}
