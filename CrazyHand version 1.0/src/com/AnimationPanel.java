package com;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AnimationPanel extends JPanel{
	public AnimationPanel(){
		super();
		
		MeleeEdit.selected=0;
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.add(new AnimationNode(0,0));
		this.add(new AnimationNode(0,0));
		this.add(new AnimationNode(0,0));
	}
}
