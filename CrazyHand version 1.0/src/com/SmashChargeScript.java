package com;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;


public class SmashChargeScript extends Script{
	JFormattedTextField frames = new JFormattedTextField(0), damageRate = new JFormattedTextField(0);
	JComboBox<String> effect;
	
	public static HitboxEffect[] charge = {

		new HitboxEffect(0x00,"00 - Nothing"),
		new HitboxEffect(0x1,"01 - Nothing"),
		new HitboxEffect(0x2,"02 - Fast Flashing White Aura (Ends with smash)"),
		new HitboxEffect(0x3,"03 - Fast Flashing Yellow Aura (Ends with smash)"),
		new HitboxEffect(0x4,"04 - Brief White Aura"),
		new HitboxEffect(0x5,"05 - Fast Flashing White Aura (Ends with smash)"),
		new HitboxEffect(0x6,"06 - Fast Flashing Black Aura (Ends with smash)"),
		new HitboxEffect(0x7,"07 - Lens Flare shoots downward, then nothing"),
		new HitboxEffect(0x8,"08 - Healing animation (Like when you pick up a heart)"),
		new HitboxEffect(0x9,"09 - Flashing White Aura"),
		new HitboxEffect(0xa,"0A - Pulsating White Aura (Starts full white then fades then goes back to full white) (AWESOME!)"),
		new HitboxEffect(0xb,"0B - Fire (Small Fraction of Charge)"),
		new HitboxEffect(0xc,"0C - Fire (About Half of the Charge)"),
		new HitboxEffect(0xd,"0D - Fire (Most of the Charge)"),
		new HitboxEffect(0xe,"0E - Fire (All of the Charge)"),
		new HitboxEffect(0xf,"0F - Electric (Small Fraction of Charge)"),
		new HitboxEffect(0x10,"10 - Electric (About Half of the Charge)"),
		new HitboxEffect(0x11,"11 - Electric (Most of the Charge)"),
		new HitboxEffect(0x12,"12 - Electric (All of the Charge)"),
		new HitboxEffect(0x13,"13 - Flashing Black for Small Fraction, Electric for the rest"),
		new HitboxEffect(0x14,"14 - Flashing Black Aura for half, Electric Aura for half"),
		new HitboxEffect(0x18,"18 - Flashing Black Aura for half, Electric Aura for half"),
		new HitboxEffect(0x1c,"1C - Flashing Black Aura for half, Electric Aura for half"),
		new HitboxEffect(0x1d,"1D - Flashing Black Aura for half, Electric Aura for half"),
		new HitboxEffect(0x1e,"1E - Flashing Black Aura for half, Electric Aura for half"),
		new HitboxEffect(0x1f,"1F - Brief Blue Aura"),
		new HitboxEffect(0x20,"20 - Nothing"),
		new HitboxEffect(0x21,"21 - Nothing"),
		new HitboxEffect(0x22,"22 - Nothing"),
		new HitboxEffect(0x23,"23 - Darkness (Small Fraction of Charge)"),
		new HitboxEffect(0x24,"24 - Darkness (About Half of the Charge)"),
		new HitboxEffect(0x25,"25 - Darkness (Most/All of the Charge)"),
		new HitboxEffect(0x26,"26 - Darkness (All of the Charge)"),
		new HitboxEffect(0x27,"27 - Flashing Dark Red Aura (Like getting your shield broken)"),
		new HitboxEffect(0x28,"28 - Flashing Dark Red Aura"),
		new HitboxEffect(0x29,"29 - Slow Flashing Pink Aura"),
		new HitboxEffect(0x2a,"2A - Slow Flashing Pink Aura"),
		new HitboxEffect(0x2b,"2B - Black Tint for the whole charge"),
		new HitboxEffect(0x2c,"2C - Brief White Lighting (not an aura)"),
		new HitboxEffect(0x2d,"2D - Original Aura (Might flash faster)"),
		new HitboxEffect(0x2e,"2E - Light Blue Aura (Shine Aura)"),
		new HitboxEffect(0x2f,"2F - Fast Flashing Yellow Aura that gets Yellower and Yellower (AWESOME!)"),
		new HitboxEffect(0x30,"30 - Starts yellow then fades to red then to normal"),
		new HitboxEffect(0x31,"31 - Starts Orange/yellow than fades away"),
		new HitboxEffect(0x32,"32 - Fast Flashing Light Bluegreen"),
		new HitboxEffect(0x33,"33 - Brief Flashing Dark Yellow Aura"),
		new HitboxEffect(0x34,"34 - Brief White Aura"),
		new HitboxEffect(0x35,"35 - Flashing White Aura with Flashing Star/Lens Flare Outside Body"),
		new HitboxEffect(0x36,"36 - Flashing White Aura with Flashing Star/Lens Flare Inside Body/On Hand?"),
		new HitboxEffect(0x37,"37 - Brief Yellow Aura"),
		new HitboxEffect(0x38,"38 - Brief Yellow Aura"),
		new HitboxEffect(0x39,"39 - Flashing White Aura with Smoke Above Head"),
		new HitboxEffect(0x3a,"3A - Flashing White Aura with Smoke In Front of Head"),
		new HitboxEffect(0x3b,"3B - Flashing White Aura with Flashing Lens Flares (Like charging a bat)"),
		new HitboxEffect(0x3c,"3C - Flashing White Aura"),
		new HitboxEffect(0x3d,"3D - Flashing White Aura"),
		new HitboxEffect(0x3e,"3E - Fast Flashing White Aura"),
		new HitboxEffect(0x3f,"3F - Starts yellowish then fades to normal"),
		new HitboxEffect(0x40,"40 - Starts brownish then fades to white then to normal"),
		new HitboxEffect(0x41,"41 - Flashing White with Electric"),
		new HitboxEffect(0x42,"42 - Nothing"),
		new HitboxEffect(0x43,"43 - No Aura but Flashing Electric Effects "),
		new HitboxEffect(0x44,"44 - Fast Flashing Electric Aura (AWESOME!)"),
		new HitboxEffect(0x45,"45 - Flashing Blue Aura with Electric Effects and Lens Flares (AWESOME!)"),
		new HitboxEffect(0x46,"46 - Fast Flashing White Aura"),
		new HitboxEffect(0x47,"47 - Orange and Red Lighting that ends in a Yellow Aura (Looks like a fire is behind character) (AWESOME!)"),
		new HitboxEffect(0x48,"48 - Fast Flashing Yellow Aura (Ends before smash is fully charged)"),
		new HitboxEffect(0x49,"49 - No Aura but Pink Lighting from beneath"),
		new HitboxEffect(0x4a,"4A - Very Fast White and then Bluegreen Aura (Maybe Early Shine Aura?)"),
		new HitboxEffect(0x4b,"4B - Flashing Bluegreen Aura with Electric Effects at feet"),
		new HitboxEffect(0x4c,"4C - Flashing Green with Flashing Lens Flares (AWESOME!)"),
		new HitboxEffect(0x4d,"4D - Starts full Black then fades to normal "),
		new HitboxEffect(0x4e,"4E - Starts Orangered and then fades to normal"),
		new HitboxEffect(0x4f,"4F - Brief White Aura in the middle of the charge"),
		new HitboxEffect(0x50,"50 - Whiter and Whiter Aura over time (Ends before smash is fully charged) (AWESOME!)"),
		new HitboxEffect(0x51,"51 - Starts White and fades to normal"),
		new HitboxEffect(0x52,"52 - No Aura, but Flashing Blue Lighting from beneath "),
		new HitboxEffect(0x53,"53 - Brief Orangered Aura then fades to normal"),
		new HitboxEffect(0x54,"54 - Greener and Greener Aura over time (Ends before smash is fully charged)"),
		new HitboxEffect(0x55,"55 - Flashing Orangered Aura then fades to normal"),
		new HitboxEffect(0x56,"56 - Fast Flashing White Aura"),
		new HitboxEffect(0x57,"57 - Fast Flashing White Aura"),
		new HitboxEffect(0x58,"58 - Brief Green Aura"),
		new HitboxEffect(0x59,"59 - No Aura, but lots of Lens Flares and Stars (From some item) (AWESOME!)"),
		new HitboxEffect(0x5a,"5A - No Aura, but lots of Lens Flares and Stars (More then 59) (AWESOME!)"),
		new HitboxEffect(0x5b,"5B - No Aura, but Pink Lighting from beneath "),
		new HitboxEffect(0x5c,"5C - Hand glows with Darkness Element (Mewtwo Style) (AWESOME!)"),
		new HitboxEffect(0x5d,"5D - Hand glows with Darkness Element (Mewtwo Style) (AWESOME!)"),
		new HitboxEffect(0x5e,"5E - Fast Flashing Purple Aura"),
		new HitboxEffect(0x5f,"5F - Brief Purple Aura"),
		new HitboxEffect(0x60,"60 - No Aura but Pink Lighting from behind"),
		new HitboxEffect(0x61,"61 - Flashing White and Pink Aura"),
		new HitboxEffect(0x62,"62 - Brief White Aura"),
		new HitboxEffect(0x63,"63 - Brief White Aura then progressively gets more and more Blueish Purplish"),
		new HitboxEffect(0x64,"64 - Glows light red or pink"),
		new HitboxEffect(0x65,"65 - Starts full Black then fades to alittle less black "),
		new HitboxEffect(0x66,"66 - Black Tint for the whole charge"),
		new HitboxEffect(0x67,"67 - Nothing"),
		new HitboxEffect(0x68,"68 - Yellower and Yellower Aura over time (Ends with smash) (Awesome!)"),
		new HitboxEffect(0x69,"69 - Dark Red Aura that fades to normal"),
		new HitboxEffect(0x6a,"6A - Flashing Orangered with Flashing Smoke (AWESOME!)"),
		new HitboxEffect(0x6b,"6B - Flashing White Aura with Stars and Lens Flares (I think like eatting a maximum tomato) (AWESOME!)"),
		new HitboxEffect(0x6c,"6C - Produces Sparkles (Like the sparkles behind Captain Falcon's Up B)"),
		new HitboxEffect(0x6d,"6D - Flashing Yellow with Flashing Large Lens Flare "),
		new HitboxEffect(0x6e,"6E - No Aura, but Yellow Lighting from the front"),
		new HitboxEffect(0x6f,"6F - Nothing"),
		new HitboxEffect(0x70,"70 - Yellow Aura that fades away"),
		new HitboxEffect(0x71,"71 - No Aura, but Green Lighting passes from front to back (AWESOME!)"),
		new HitboxEffect(0x72,"72 - Brief Red Aura that fades to normal"),
		new HitboxEffect(0x73,"73 - Brief Blue Aura that fades to normal"),
		new HitboxEffect(0x74,"74 - Brief Green Aura that fades to normal"),
		new HitboxEffect(0x75,"75 - White Tint for the whole charge"),
		new HitboxEffect(0x76,"76 - Brief Flashing White with Smoke on the ground (AWESOME!)"),
		new HitboxEffect(0x77,"77 - Original Aura"),
		new HitboxEffect(0x78,"78 - Original Aura"),
		new HitboxEffect(0x79,"79 - White with Blue Sparkles"),
		new HitboxEffect(0x7a,"7A - Flashing Dark Red Aura"),
		new HitboxEffect(0xFF,"Unknown")
		//new HitboxEffect(0x,"7B-FF - Crashes the game XD"),
		};


    public SmashChargeScript(String n, int[] d, int l){
    	super(n,d,l);
    	this.removeAll();
    	hexField=false;
    	init(n,d,l);
    	
    	
    	
    	
    	this.add(Box.createVerticalStrut(5));
    	Box b = Box.createHorizontalBox();
    	b.add(new JLabel("Max Charge Length (Frames): "));
        b.add(frames);
        b.add(Box.createHorizontalStrut(10));
        b.add(new JLabel("Damage Rate of Increase: "));
        b.add(damageRate);
        
        b.add( Box.createHorizontalGlue() );
        this.add(b);
        this.add(Box.createVerticalStrut(10));
        
        String[] tmp = new String[charge.length];
        for(int i = 0; i < tmp.length; i++){
        	tmp[i]=charge[i].name;
        }
        effect = new JComboBox<String>(tmp);
        effect.setSelectedIndex(getSelect(data[4]));
        //damage.setValue(this.setBits(23,31));
        frames.setValue(data[1]);
        
        this.add(effect);
        this.add(Box.createVerticalStrut(5));
        
        damageRate.setValue(data[3]+ data[2]*Math.pow(2, 8));
    	
   
     
        
    }
    
    public static int getSelect(int id){
		for(int i = 0; i < charge.length; i++){
			if(id==charge[i].id){
				return i;
			}
		}
		
		return charge.length-1;
	}
    
    public void updateData(){
    	data[4]=charge[effect.getSelectedIndex()].id;
    	data[1]=((Number)frames.getValue()).intValue();
    	
    	//data[3] = ((Number)damageRate.getValue()).intValue();
    	saveBits(16,31,((Number)damageRate.getValue()).intValue());
    }
}