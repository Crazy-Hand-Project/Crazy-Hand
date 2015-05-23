package com.scripts;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;

import com.FileIO;

public class LoopScript extends Script {
	
	JFormattedTextField repeatAmount = new JFormattedTextField(0),
    repeatFrames = new JFormattedTextField(0);
	JComboBox timerType;
	
	public LoopScript(String n, int[] d, int l)
	{
		super(n, d, l);
		
		this.removeAll();
    	hexField=false;
    	init(n,d,l);
    	
    	/*
    	
    	timerType = new JComboBox(new String[]{"Synchronous","Asynchronous"});
    	
    	int h = (d[4]/0x4)-1;
    	
    	if(h>1)h=1;
    	
    	timerType.setSelectedIndex(h);
    	
    	*/
    	
    	repeatAmount.setMinimumSize(new Dimension(2000, 200));
    	repeatAmount.setMaximumSize(new Dimension(2000, 200));
    	
    	repeatFrames.setMinimumSize(new Dimension(2000, 200));
    	repeatFrames.setMaximumSize(new Dimension(2000, 200));
    	this.add(Box.createVerticalStrut(5));
    	Box a = Box.createHorizontalBox();
    	a.add(new JLabel("Repeat loop "));
    	a.add(Box.createHorizontalStrut(7));
    	a.add(repeatAmount);
    	a.add(new JLabel(" times"));
    	a.add(Box.createHorizontalGlue());
    	
    	/*
    	Box b = Box.createHorizontalBox();
    	b.add(new JLabel("Repeat every "));
        b.add(repeatFrames);
        b.add(new JLabel(" frames"));
        b.add( Box.createHorizontalGlue() );
        */
        
        
        Box c = Box.createVerticalBox();
        Box e = Box.createHorizontalBox();
        c.add(a);
//        c.add(b);
        e.add(c);
//        e.add(timerType);
        
        this.add(e);
        
        repeatAmount.setValue(data[3]);
//        repeatFrames.setValue(data[7]);
	}
	
	public void scramble(){
		data[3]=FileIO.randInt(1,data[3]*2-((data[3]*2)/2));
	}
	
	public void updateData(){
    	data[3]=((Number)repeatAmount.getValue()).intValue();
//    	data[4]=(timerType.getSelectedIndex()+1)*0x4;
//    	System.out.println("dat:"+data[4]);
//    	data[7]=((Number)repeatFrames.getValue()).intValue();
    }

}
