package com.panels;

import com.BitWork;

class FSMData implements Comparable<FSMData>{
	int charId,startFrame,actionID;
	float multiplier;
	boolean isAction;
	
	
	public FSMData(int b[], float f) {
		charId = b[0];
		startFrame = b[1];
		isAction = (BitWork.setBits(16,19,b) == 0);
		actionID = BitWork.setBits(20,31,b);
		multiplier = f;
		
	}
    
	
	@Override
	 public int compareTo(FSMData d) {
		 return (this.charId*1000000+this.actionID*1000+startFrame - d.charId*1000000+d.actionID*1000+d.startFrame);

	  }


}
