package com.scripts;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.FileIO;
import com.HitboxEffect;


public class HitboxScript extends Script {
    	
	
    	JFormattedTextField baseKnockback = new JFormattedTextField(0),
    			knockbackGrowth = new JFormattedTextField(0),
    			weightKnockback = new JFormattedTextField(0),
    			damage = new JFormattedTextField(0),
    			shieldDamage = new JFormattedTextField(0),
    			size = new JFormattedTextField(0),
    			angle = new JFormattedTextField(0),
    			zOff = new JFormattedTextField(0),
    			yOff = new JFormattedTextField(0),
    			xOff = new JFormattedTextField(0),
    			sound = new JFormattedTextField(0),
    			hurtboxInteraction = new JFormattedTextField(0),
    			boneAttachment = new JFormattedTextField(0);
    	
    	public JComboBox<String> Attribute;

        public HitboxScript(String n, int[] d, int l){
        	super(n,d,l);
        	
        	this.removeAll();
        	hexField=false;
        	init(n,d,l);
        	
        	String[] tmp = new String[5];
            for(int i = 0; i < 5; i++){
            	tmp[i]="SFX-" + i;
            }
            String[] tmp2 = new String[HitboxEffect.hitboxEffect.length];
            for(int i = 0; i < tmp2.length; i++){
            	tmp2[i]=HitboxEffect.hitboxEffect[i].name;
            }
            String[] tmp3 = new String[5];
            for(int i = 0; i < 5; i++){
            	tmp3[i]="Option " + (i+1);
            }

            
            //int t = data[3];
            //t=bit(t,8,getBit(data[2],0)); 
            //int t = this.setBits(23,31);
            damage.setValue(this.setBits(23,31));
            
            
            //t=data[5];
            //for(int i = 0 ; i < 8; i++){
            //	t=bit(t,8+i,getBit(data[4],i));
            //}
            size.setValue(this.setBits(32,47));
            
            
            int t = this.setBits(48,63);
            if(getBit(t,15)==1){
            	t-=65536;
            }
            zOff.setValue(t);
            
            t = this.setBits(64,79);
            if(getBit(t,15)==1){
            	t-=65536;
            }
            yOff.setValue(t);
            
            t = this.setBits(80,95);
            if(getBit(t,15)==1){
            	t-=65536;
            }
            xOff.setValue(t);
            
            angle.setValue(this.setBits(96,104));
            knockbackGrowth.setValue(this.setBits(105,113));
            weightKnockback.setValue(this.setBits(114,122));
            boneAttachment.setValue(this.setBits(14,20));
            baseKnockback.setValue(this.setBits(128,136));
            shieldDamage.setValue(this.setBits(143,149));
            sound.setValue(this.setBits(150,157));
            hurtboxInteraction.setValue(this.setBits(158,159));
        	
            int elem = this.setBits(137,143);
            elem= bit(elem,0,0);
            elem= bit(elem,1,0);

            Attribute = new JComboBox<String>(tmp2);
            Attribute.setSelectedIndex(HitboxEffect.getSelect(elem));

            
            

            
            
            
            JPanel tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            //damage.getValue()
            tempPanel.add(new JLabel("Base Knockback: "));
            tempPanel.add(baseKnockback);
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
            
            tempPanel.add(new JLabel("Damage: "));
            tempPanel.add(damage);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Shield Damage: "));
            tempPanel.add(shieldDamage);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));

            
            this.add(Box.createVerticalStrut(5));
            this.add(tempPanel);
            this.add(Box.createVerticalStrut(5));
            tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            tempPanel.add(new JLabel("Size: "));
            tempPanel.add(size);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Angle: "));
            tempPanel.add(angle);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("X-Offset: "));
            tempPanel.add(xOff);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Y-Offset: "));
            tempPanel.add(yOff);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Z-Offset: "));
            tempPanel.add(zOff);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            
            this.add(Box.createVerticalStrut(5));
            this.add(tempPanel);
            this.add(Box.createVerticalStrut(5));
            tempPanel= new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.LINE_AXIS));
            
            tempPanel.add(new JLabel("Attribute: "));
            tempPanel.add(Attribute);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Bone: "));
            tempPanel.add(boneAttachment);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            
            
            tempPanel.add(new JLabel("Sound FX: "));
            tempPanel.add(sound);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            tempPanel.add(new JLabel("Hurtbox Interaction: "));
            tempPanel.add(hurtboxInteraction);
            tempPanel.add(Box.createHorizontalStrut(5));
            tempPanel.add(new JSeparator(SwingConstants.VERTICAL));
            tempPanel.add(Box.createHorizontalStrut(5));
            
            
            
            this.add(Box.createVerticalStrut(5));
            this.add(tempPanel);
            this.add(Box.createVerticalStrut(5));
            
            
            
            
            
            
            this.setBackground(Color.WHITE);
            this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            
        }
        
        
        
        public void updateData(){
        	//data[3]= ((Number)damage.getValue()).intValue();
        	this.saveBits(23,31,((Number)damage.getValue()).intValue());
        	this.saveBits(32,47,((Number)size.getValue()).intValue());

        	
        	
        	
            int tmp = ((Number)zOff.getValue()).intValue();
            if(getBit(tmp,15)==1){
            	tmp+=65536;
            }
            this.saveBits(48,63,tmp);
            
            tmp = ((Number)yOff.getValue()).intValue();
            if(getBit(tmp,15)==1){
            	tmp+=65536;
            }
            this.saveBits(64,79,tmp);
            
            tmp = ((Number)xOff.getValue()).intValue();
            if(getBit(tmp,15)==1){
            	tmp+=65536;
            }
            this.saveBits(80,95,tmp);
         
            
            this.saveBits(96,104,((Number)angle.getValue()).intValue());
            this.saveBits(105,113,((Number)knockbackGrowth.getValue()).intValue());
            this.saveBits(114,122,((Number)weightKnockback.getValue()).intValue());
            this.saveBits(14,20,((Number)boneAttachment.getValue()).intValue());
            this.saveBits(128,136,((Number)baseKnockback.getValue()).intValue());
            this.saveBits(143,149,((Number)shieldDamage.getValue()).intValue());
            this.saveBits(150,157,((Number)sound.getValue()).intValue());
            this.saveBits(158,159,((Number)hurtboxInteraction.getValue()).intValue());

            

        	int bit0=getBit(data[17],0),bit1=getBit(data[17],1);

        	int el = HitboxEffect.hitboxEffect[ Attribute.getSelectedIndex()].id;
        	el=bit(el,0,bit0);
        	el=bit(el,1,bit1);
        	this.saveBits(137,143,el);
  		
        }
        public void scramble(){
        	int min=50, max=200;
        	//baseKnockback.setValue((int)(((Number)baseKnockback.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//knockbackGrowth.setValue((int)(((Number)knockbackGrowth.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//weightKnockback.setValue((int)(((Number)weightKnockback.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//damage.setValue((int)(((Number)damage.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//shieldDamage.setValue((int)(((Number)shieldDamage.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//size.setValue((int)(((Number)size.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//angle.setValue((int)(((Number)angle.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//zOff.setValue((int)(((Number)zOff.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//xOff.setValue((int)(((Number)xOff.getValue()).intValue()*FileIO.randInt(min, max)/100.f));
        	//yOff.setValue((int)(((Number)yOff.getValue()).intValue()*FileIO.randInt(min, max)/100.f));

        	
        	baseKnockback.setValue(FileIO.randInt(0, 20));
        	knockbackGrowth.setValue(FileIO.randInt(0, 140));
        	//weightKnockback.setValue(0));
        	damage.setValue(FileIO.randInt(0,25));
        	shieldDamage.setValue(FileIO.randInt(0, 20));
        	size.setValue(FileIO.randInt(0,3000));
        	angle.setValue(FileIO.randInt(0, 360));
        	zOff.setValue(FileIO.randInt(0,1500)*FileIO.sign());
        	xOff.setValue(FileIO.randInt(0,1500)*FileIO.sign());
        	yOff.setValue(FileIO.randInt(0,1500)*FileIO.sign());
    	}
    }
