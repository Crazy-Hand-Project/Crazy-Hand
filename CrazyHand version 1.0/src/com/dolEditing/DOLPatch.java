package com.dolEditing;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.FileIO;

public class DOLPatch implements PropertyChangeListener {
	/*
	public static final DOLPatch dolPatchSelfDamage = new DOLPatch(
			"Damage Self or Heal Self(v1.02) [Achilles]",
				
				//Default pointer to write to(In the case of Gecko codes)
				0x4279C0,
			
				//Pointers to write to(In the case of AR codes)
				new int[]{
					0x6F7FC,
					0x6F800,
					0x6F804,
				},
				
				//Default Values inside vanilla .DOL, four values per pointer
				//This only contains the data modified by the AR portion of the code due to gecko being mean to me, hence why this is unused as of yet.
				new int[]{
					0x54, 0x84, 0x30, 0x34,
					0x7C, 0x84, 0x36, 0x70,
					0x6C, 0x84, 0x80, 0x00,
				},
				
				//New Values, four values per pointer
				new int[]{
					0x60,0x00,0x00,0x00,
					0x60,0x00,0x00,0x00,
					0x60,0x00,0x00,0x00,
					0x60,0x00,0x00,0x00,
				}
			
			);
	*/
	
	public static final DOLPatch dolPatchStockHandicap = new DOLPatch(
			"(DOL) Handicap = Stock Count [Dan Salvato]",
			//Default pointer to write to(In the case of Gecko codes. Unused in this case since it is pure AR.)
			0x4279C0,//Default "safe pointer" for AR codes, in case something wonky happens and values are somehow written outside of where they are supposed to be. This will overwrite a previously applied patch instead of vital data inside the DOL.
			
			//Pointers to write to
			new int[]{
				0x1649A8,
				0x1649AC
			},
			
			//Default values
			new int[][]{
				new int[]{0x88,0x03,0x00,0x00},
				new int[]{0x7C,0x7D,0x02,0x14}
			},
			
			//New values
			new int[][]{
				new int[]{0x98,0x1C,0x00,0x6A},
				new int[]{0x4B,0xFF,0xFF,0x8C}
			}
	);
	
	//0x164970
	//0x1649AC
	
	public String name = "ERR";
	public int[] pointers;
	public int[][] defaultValues, newValues;
	//Default pointer that Crazy Hand will try to stick any given patch into.
	public int defaultPointer;
	
	
	/**
	 * 
	 * @param n: Name of the patch
	 * @param dfp: Default Pointer(For Gecko code injection)
	 * @param po: List of pointers to write to(For AR code injection)
	 * @param dv: Default Values at the previously specified pointers(For removal of code injection)
	 * @param nv: New Values at the previously specified pointers(For code injection)
	 */
	public DOLPatch(String n, int dfp, int[] po, int[][] dv, int[][] nv){
		defaultPointer = dfp;
		name=n;
		pointers=po;
		defaultValues=dv;
		newValues=nv;
	}
	
	
	private JProgressBar progress;
	private JFrame progressDisplay;
	private int progressAmount=0;
	private final int TASK_PATCH=0,TASK_UNPATCH=1;
	
	public void applyPatch(){
		int patchSize = newValues.length*4;
		//Only bother to create a window if it's a sizable patch
		if(patchSize > 15){
			createProgressDisplay();
		}
		
		PatchTask task = new PatchTask(TASK_PATCH);
	    task.addPropertyChangeListener(this);
	    task.execute();
		
		
		while(!task.isDone()&&!task.isCancelled()){
			
		}
		disposeProgressDisplay();
	}
	
	public void undoPatch(){
		int patchSize = newValues.length*4;
		//Only bother to create a window if it's a sizable patch
		if(patchSize > 15){
			createProgressDisplay();
		}
		
		PatchTask task = new PatchTask(TASK_UNPATCH);
	    task.addPropertyChangeListener(this);
	    task.execute();
		
		
		while(!task.isDone()&&!task.isCancelled()){
			
		}
		disposeProgressDisplay();
	}
	
	public void createProgressDisplay(){
		progress = new JProgressBar();
		progress.setValue(0);
		progress.setStringPainted(true);
		progressDisplay = new JFrame("Patch progress");
		progressDisplay.setSize(300, 200);
		progressDisplay.setDefaultCloseOperation(progressDisplay.DO_NOTHING_ON_CLOSE);
		Box bx = Box.createVerticalBox();
		bx.add(progress);
		progressDisplay.add(bx);
		progressDisplay.setVisible(true);
	}
	
	public void disposeProgressDisplay(){
		if(progressDisplay!=null){
			progressDisplay.dispose();
		}
		progressAmount=0;
		progress=null;
		progressDisplay=null;
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if(progress!=null)
		progress.setString(progressAmount+"/"+(pointers.length * 4)+" bytes written");
	}
	
	
		class PatchTask extends SwingWorker<Void, Void> {
			public int operation = -1;
			
			public PatchTask(int op){
				operation = op;
			}
			
	        /*
	         * Main task. Executed in background thread.
	         */
	        @Override
	        public Void doInBackground() {
	            Random random = new Random();
	            progressAmount=0;
	            //Initialize progress property.
	            setProgress(0);
	            FileIO.initDOL();
	    			
	    		int i,j;
	    		//AR portion
	    		for(i = 0; i < pointers.length; i ++){
	    			int pointer = pointers[i];
	    			int[][] valArray = operation == TASK_PATCH ? newValues : defaultValues;
	    			
	    			System.out.println("Start:"+Integer.toHexString(pointer));
	    			
	    			for(j = 0; j < valArray[i].length; j ++){
	    				int bt = valArray[i][j];
	    				FileIO.setPosition(pointer+j);
	    				System.out.println(Integer.toHexString(pointer+j));
	    				FileIO.writeByte(bt);
	    				progressAmount++;
	    			}
	    		}
	    		
	            return null;
	        }
	 
	        /*
	         * Executed in event dispatching thread
	         */
	        @Override
	        public void done() {
	        	
	        	//Save the changes to FileIO
	        	try {
	        		FileIO.loadedISOFile.reload();
					FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), FileIO.f.array());
					FileIO.loadedISOFile.reload();
					FileIO.loadedISOFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	
	        	
	            Toolkit.getDefaultToolkit().beep();
	            try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {}
	        }
	    }
	
}
