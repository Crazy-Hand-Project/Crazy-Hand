package com;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

class ThrowScript extends Script {
	
	
	JFormattedTextField
			angle = new JFormattedTextField(0),
			baseKnockback = new JFormattedTextField(0),
			damage = new JFormattedTextField(0),
			knockbackGrowth = new JFormattedTextField(0),
			weightKnockback = new JFormattedTextField(0);
	public JComboBox<String> Attribute, throwType, hurtboxInteraction;

    public ThrowScript(String n, int[] d, int l){
    	super(n,d,l);
    	
    	this.removeAll();
    	hexField=false;
    	init(n,d,l);
    	
    	String[] tmp = new String[] {"Throw", "Release" };
    			
    			
        String[] tmp2 = new String[HitboxEffect.hitboxEffect.length];
        for(int i = 0; i < tmp2.length; i++){
        	tmp2[i]=HitboxEffect.hitboxEffect[i].name;
        }
        String[] tmp3 = new String[5];
        for(int i = 0; i < 5; i++){
        	tmp3[i]="Option " + (i+1);
        }

        damage.setValue(this.setBits(23,31));
        
        angle.setValue(this.setBits(32,40));
        knockbackGrowth.setValue(this.setBits(41,49));
        baseKnockback.setValue(this.setBits(64,72));
    	
        int elem = this.setBits(73,76);
        elem= bit(elem,0,0);
        elem= bit(elem,1,0);
        
        int throwT = this.setBits(6,8);
        
        
        
    	throwType = new JComboBox(tmp);
    	throwType.setSelectedIndex(throwT == 0 ? 0 : 1);
    	
    	System.out.println(throwT);
    	
    	
        Attribute = new JComboBox<String>(tmp2);
        Attribute.setSelectedIndex(HitboxEffect.getSelect(elem));
        hurtboxInteraction = new JComboBox(tmp3);
        hurtboxInteraction.setSelectedIndex(0);
        
        

        
        
        
        JPanel tempPanel= new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
        
        tempPanel.add(new JLabel("Angle: "));
        tempPanel.add(angle);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        
        //damage.getValue()
        tempPanel.add(new JLabel("Base Knockback: "));
        tempPanel.add(baseKnockback);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        tempPanel.add(new JLabel("Damage: "));
        tempPanel.add(damage);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        tempPanel.add(new JLabel("Knockback Growth: "));
        tempPanel.add(knockbackGrowth);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        tempPanel.add(new JLabel("WDSK: "));
        tempPanel.add(weightKnockback);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        
        this.add(Box.createVerticalStrut(5));
        this.add(tempPanel);
        this.add(Box.createVerticalStrut(5));
        tempPanel= new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
        
        tempPanel.add(new JLabel("Throw type: "));
        tempPanel.add(throwType);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        tempPanel.add(new JLabel("Attribute: "));
        tempPanel.add(Attribute);
        tempPanel.add(Box.createHorizontalStrut(5));
        tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
        tempPanel.add(Box.createHorizontalStrut(5));
        
        
        this.add(Box.createVerticalStrut(5));
        this.add(tempPanel);
        this.add(Box.createVerticalStrut(5));
        tempPanel= new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
        
        
        
        this.add(Box.createVerticalStrut(5));
        this.add(tempPanel);
        this.add(Box.createVerticalStrut(5));
        
        
        
        
        
        
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

    }
    public static int bit(int num, int bit, int set) {
    	if(set==1){
    		return num | (1 << bit);
    	}
    	else{
    		return num & ~(1 << bit);
    	}

     }
    public static int getBit(int num, int bit) {
    	return (num >> bit) & 1;

     }
    
    public int setBits(int start, int end){
    	int tmp = 0, length=end-start;
    	
    	for(int i = 0; i <= length; i++){
    		tmp = bit(tmp,length-i,getBit(data[(i+start)/8],7-(i+start)%8));
    	}
    	
    	
    	return tmp;
    }
    public void saveBits(int start, int end, int val){
    	int length=end-start;
    	for(int i = 0; i <= length; i++){
    		data[(i+start)/8] = bit(data[(i+start)/8], 7-(i+start)%8, getBit(val,length-i));
    	}
    }
    
    
    public void updateData(){
    	int num = throwType.getSelectedIndex() == 0 ? 0 : 1;
    	
    	this.saveBits(6,8,num);
    	this.saveBits(23,31,((Number)damage.getValue()).intValue());
    	
    	this.saveBits(32,40,((Number)angle.getValue()).intValue());
    	this.saveBits(41,49,((Number)knockbackGrowth.getValue()).intValue());
    	this.saveBits(50,58,((Number)weightKnockback.getValue()).intValue());
    	this.saveBits(64,72,((Number)baseKnockback.getValue()).intValue());
    	
    	int bit0=getBit(data[9],0),bit1=getBit(data[9],1);

    	int el = HitboxEffect.hitboxEffect[ Attribute.getSelectedIndex()].id;
    	el=bit(el,0,bit0);
    	el=bit(el,1,bit1);
    	this.saveBits(73,76,el);
    	

		
    }
}
