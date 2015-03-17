package com;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;

public class LoopScript extends Script {
	
	JFormattedTextField repeatAmount = new JFormattedTextField(0),
    repeatFrames = new JFormattedTextField(0);
	
	public LoopScript(String n, int[] d, int l)
	{
		super(n, d, l);
		
		this.removeAll();
    	hexField=false;
    	init(n,d,l);
    	this.add(Box.createVerticalStrut(5));
    	Box a = Box.createHorizontalBox();
    	a.add(new JLabel("Repeat loop "));
    	a.add(Box.createHorizontalStrut(7));
    	a.add(repeatAmount);
    	a.add(new JLabel(" times"));
    	a.add(Box.createHorizontalStrut(509));
    	a.add(Box.createHorizontalGlue());
    	 Box b = Box.createHorizontalBox();
    	 b.add(new JLabel("Repeat every "));
        b.add(repeatFrames);
        b.add(new JLabel(" frames"));
        b.add(Box.createHorizontalStrut(500));
        b.add( Box.createHorizontalGlue() );
        this.add(a);
        this.add(b);
        repeatAmount.setValue(data[3]);
        repeatFrames.setValue(data[7]);
	}
	
	public void updateData(){
    	data[3]=((Number)repeatAmount.getValue()).intValue();
    	data[7]=((Number)repeatFrames.getValue()).intValue();
    }

}
