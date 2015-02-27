package com;


public class Attribute {
	
	public static Attribute[] attributes = {
    	new Attribute("Initial Walk Velocity",""),//0
    	new Attribute("Walk Acceleration?",""),
    	new Attribute("Walk Maximum Velocity",""),
    	new Attribute("Slow Walk Max?",""),
    	new Attribute("Mid Walk Point?",""),
    	new Attribute("Fast Walk Min?",""),
    	new Attribute("Friction/Stop Deccel",""),//		"Influences wavedash length. Luigi=0.025 Zelda=0.1"),
    	new Attribute("Dash Initial Velocity",""),
    	new Attribute("Dash & Run Acceleration A",""),
    	new Attribute("Dash & Run Acceleration B",""),
    	new Attribute("Dash & Run Terminal Velocity",""),//10
    	new Attribute("Run Animation Scaling",""),
    	new Attribute("Run Acceleration?",""),//
    	new Attribute("????",""),
    	new Attribute("Jump Startup Lag (Frames)",""),//
    	new Attribute("Jump H Initial Velocity",""),
    	new Attribute("Jump V Initial Velocity",""),
    	new Attribute("Ground to Air Jump Momentum Multiplier",""),
    	new Attribute("Jump H Maximum Velocity",""),
    	new Attribute("Shorthop V Initial Velocity",""),
    	new Attribute("Air Jump Multiplier",""),//20
    	new Attribute("????",""),
    	new Attribute("Number of Jumps",""),//
    	new Attribute("Gravity",""),
    	new Attribute("Terminal Velocity",""),
    	new Attribute("Air Mobility A",""),
    	new Attribute("Air Mobility B",""),
    	new Attribute("Max Aerial H Velocity",""),
    	new Attribute("Air Friction",""),
    	new Attribute("Fast Fall Terminal Velocity",""),
    	new Attribute("????",""),//30
    	new Attribute("Jab 2 Window?",""),
    	new Attribute("Jab 3 Window?",""), 
    	new Attribute("Frames to Change Direction on Standing Turn",""),//
    	new Attribute("Weight",""),//
    	new Attribute("Model Scaling",""),
    	new Attribute("Shield Size",""),
    	new Attribute("Shield Break Initial Velocity",""),
    	new Attribute("Rapid Jab Window",""),
    	new Attribute("????",""),
    	new Attribute("????",""),//40
    	new Attribute("????",""),
    	new Attribute("Ledgejump Horizontal Velocity",""),
    	new Attribute("Ledgejump Vertical Velocity",""),
    	new Attribute("Item Throw Velocity",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),//50
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("Kirby Neutral+B Star Damage",""),
    	new Attribute("Normal Landing Lag",""),
    	new Attribute("N-Air Landing Lag",""),
    	new Attribute("F-Air Landing Lag",""),
    	new Attribute("B-Air Landing Lag",""),//60
    	new Attribute("U-Air Landing Lag",""),
    	new Attribute("D-Air Landing Lag",""),
    	new Attribute("Victory Screen Window Model Scaling",""),   
    	new Attribute("????",""),
    	new Attribute("WallJump H Velocity",""),
    	new Attribute("WallJump V Velocity",""),
 };

	public String name,info;
	public Attribute(String nm, String i){
		name=nm;
		info=i;
	}

}
