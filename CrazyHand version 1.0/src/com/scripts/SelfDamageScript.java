package com.scripts;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;



//TODO for now, this is unused due to my inability to inject gecko code into start.dol

public class SelfDamageScript extends Script {
	
	public JComboBox<String> scriptTypeOptions;
	public JFormattedTextField damageAmount = new JFormattedTextField(0);
	
	public SelfDamageScript(String n, int[] d, int l) {
		super(n, d, l);
		this.removeAll();
    	hexField=false;
    	init(n,d,l);
    	
    	String[] tmp = {
    			"Damage self",
    			"Heal self*"
    	};
    	
    	scriptTypeOptions = new JComboBox<String>(tmp);
    	scriptTypeOptions.setToolTipText("Heal self will only work if you apply the ISO patch!");
    	
    	scriptTypeOptions.setSelectedIndex(this.setBits(8, 15));
    	
    	damageAmount.setValue(this.setBits(16, 31));
    	
    	Box b = Box.createHorizontalBox();
    	Box b2 = Box.createHorizontalBox();
    	
	    	b.add(new JLabel("Script type: "));
	    	b.add(scriptTypeOptions);
	    	
	    	b2.add(new JLabel("Damage amount: "));
	    	b2.add(damageAmount);
    	
    	this.add(b);
    	this.add(b2);
    	updateData();
	}
	
	public void updateData(){
		this.data[1] = scriptTypeOptions.getSelectedIndex();
		System.out.println("DMG:"+(int)damageAmount.getValue());
		if((int)damageAmount.getValue()>511){
			damageAmount.setValue(511);
		}
		this.saveBits(16, 31, (int)damageAmount.getValue());
		System.out.println("DMGAGAIN:"+this.setBits(16, 31));
	}

}
