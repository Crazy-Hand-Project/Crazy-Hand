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
    	new Attribute("Double Jump Momentum",""),
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
    	
    	//Everything below this point is temporary
    	//Trying to figure out the exact mechanics of double-jump cancelling
    	//Mewtwo, Ness, and Yoshi all share a Double jump cancel? value of -1
    	//They are the only characters in the roster that have that value set to -1
    	//I've been digging around in their jump subactions and grafting them
    	//onto other characters, and I'm making some progress.
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("DJC?",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("DJC?",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("????",""),
    	new Attribute("Double jump cancel?", "")
 };
	
	public static double[] avg = {//average values of all characters for attributes. used in the randomizer.
		0.14640625,	
		0.0875	,
		1.0203125,	
		0.189975,	
		0.4511875,	
		0.732005,	
		0.1016875,	
		1.4718125,	
		0.159258064516129,	
		0.0664375,	
		1.51806451612903,	
		1.59609375,	
		29.12275,	
		2.971,	
		4.55535483870968,	
		0.935625,	
		2.5625	,
		0.799375,	
		1.446875,	
		1.5934375,	
		0.9259375,	
		0.866875,	
		1,//lul
		0.10575,	
		1.9328125,	
		0.035859375,	
		0.02421875,	
		0.937586206896552,	
		0.01484375,	
		2.448125,	
		3.0625,	
		26.875,	
		12,	
		5.09375,	
		92.8076923076923,	
		1.0796875,	
		14.0078125,	
		3.0759375,	
		0,	
		16,	
		0,	
		1,//lul
		1.00625,	
		2.6309375,	
		0.984375,	
		0.9921875,	
		0.2,	
		2.1671875,	
		0,	
		6,	
		0,	
		0,	
		9,	
		0,	
		6.103125,	
		2.40453125,	
		15.71875,	
		5.5625,	
		18.625,	
		21.8709677419355,	
		20.03125,	
		20.9375	,
		26.7096774193548,	
		22.15625,	
		0.50625,	
		1.146875,	
		2.2825,
		};

	public String name,info;
	public Attribute(String nm, String i){
		name=nm;
		info=i;
	}

}
