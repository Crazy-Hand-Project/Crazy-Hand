package com;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimationNode extends JPanel{
	
	public AnimationNode(int k, int pointer){
		super();
		
		
		JPanel t= new JPanel();
        t.setLayout(new BoxLayout(t, BoxLayout.LINE_AXIS));
		t.add(new JLabel("Placeholder, "));
		t.add(new JLabel("Will finish soon!"));
		this.add(t);
		
	}

}
