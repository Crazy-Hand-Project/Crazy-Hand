package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Options
{
	public static boolean advanced = false;
	
	
	public static void loadOptions()
	{
		File f = new File("options.cfg");
		
		try {
			List<String> lines = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
			for(int i = 0; i < lines.size(); i ++)
			{
				String o = lines.get(i);
				if(o.startsWith("optLastISO:")) {
					hasLastIso = true;
					isoPath = o.split("optLastISO:")[1];
				}
				else if(o.startsWith("optRawData:")) {
					rawEnabled = o.split("optRawData:")[1]=="true";
				}
				else if(o.startsWith("optDolphinPath:")){
					hasDolphinPath = true;
					dolphinPath = o.split("optDolphinPath:")[1];
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static boolean hasLastIso = false, hasDolphinPath = false;
	public static String dolphinPath = "",isoPath = "";
	public static boolean rawEnabled = false, tabSubactions = false;
	
	public static void saveOptions()
	{
		File f = new File("options.cfg");
		
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		try {
			PrintWriter out = new PrintWriter("options.cfg");
			
			String ln = System.lineSeparator();
			
			if(MeleeEdit.restorePane != null)
				out.write("optRawData:" + MeleeEdit.restorePane.rawBox.isSelected()+ln);
			
			if(hasLastIso)
			out.write("optLastISO:" + isoPath+ln);
			if(hasDolphinPath)
			out.write("optDolphinPath:"+dolphinPath+ln);
			out.write("tabSubactions:"+tabSubactions+ln);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeRandomSeed(Long newSeed)
	{
		File f = new File("randomSeeds.txt");
		
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("randomSeeds.txt", true)));
			//List<String> lines = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
			String ln = System.lineSeparator();
			
			Date d = Calendar.getInstance().getTime();

			out.write(newSeed + " (Created:"+d.toGMTString()+")"+ln);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*Parameters for running Dolphin from console. 
	    [Command line usage]
		Usage: Dolphin [-h] [-d] [-l] [-e <str>] [-b] [-V <str>] [-A <str>]
		  -h, --help                	Show this help message
		  -d, --debugger            	Opens the debugger
		  -l, --logger              	Opens the logger
		  -e, --exec=<str>          	Loads the specified file (DOL,ELF,WAD,GCM,ISO)
		  -b, --batch             	Exit Dolphin with emulator
		  -V, --video_backend=<str>  	Specify a video plugin
		  -A, --audio_emulation=<str>  	Low level (LLE) or high level (HLE) audio
		  
		  
		  
		  
		//Don't think we'll be needing this function anymore.
	public static void writeDolphinRunFile(){
		
		File f = new File("runDolphin.bat");
		
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		try {
			PrintWriter out = new PrintWriter("runDolphin.bat");
			
			String ln = System.lineSeparator();
			out.write("@Echo off"+ln);
			out.write("cd " + dolphinPath+ln);
			out.write("start \"\" /wait Dolphin.exe /e " + FileIO.loadedISOFile.getChosenISOFile().getPath()+ ""+ln);
			out.write("exit"+ln);
			
			
			
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	*/
	
	public static void openDolphin()
	{
		if(dolphinInstance != null && dolphinInstance.isAlive()){
		String ln = System.lineSeparator();

	    JOptionPane optionPane = new JOptionPane(
			    JOptionPane.QUESTION_MESSAGE,
			    JOptionPane.OK_CANCEL_OPTION);
		int res = optionPane.showConfirmDialog(MeleeEdit.frame, "There is already an instance of Dolphin running! Would you like to close it now?", "Dolphin is already running", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if(res!=JOptionPane.OK_OPTION){
			return;
		}
	}
		
		if(Options.dolphinInstance != null && Options.dolphinInstance.isAlive()){
			destroyDolphinInstance();
		}
		
		
			if(!Options.hasDolphinPath) {
				FileNameExtensionFilter exeFilter = new FileNameExtensionFilter(
						"EXE Files", "exe");
				final JFileChooser fc = new JFileChooser();

				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.addChoosableFileFilter(exeFilter);
				fc.setFileFilter(exeFilter);
				
				
				int returnVal = fc.showOpenDialog(MeleeEdit.frame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					Options.dolphinPath=fc.getCurrentDirectory().getPath();
					Options.hasDolphinPath=true;
					Options.saveOptions();
				}
			}
			
			File fi = new File(dolphinPath);
			String[] prg = {dolphinPath+"\\Dolphin.exe",
							"/e" + FileIO.loadedISOFile.getChosenISOFile().getPath()
						   };
			ProcessBuilder pb = new ProcessBuilder(prg);
			
			if(fi.exists()){
				try {
					dolphinInstance = pb.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
	}
	
	public static void destroyDolphinInstance(){
		Options.dolphinInstance.destroy();
		 Options.dolphinInstance = null;
	}
	
	public static Process dolphinInstance;
	
	public static boolean isOSWindows(){
		return System.getProperty("os.name").startsWith("Windows");
	}
	
	//TODO More options for how the GUI looks.
	//Not a high priority at the moment.
	class userPreferences {
		
	}
	
	//Was used for writing byte-by-byte differences after character loading
	//Might recycle this in the future.
	public static void writeDebug(String s) {
		File f = new File("debug.txt");
		
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		try {
			PrintWriter out = new PrintWriter("debug.txt");
			
			String ln = System.lineSeparator();
			
			Date d = Calendar.getInstance().getTime();
			System.out.println(d.toGMTString());
			out.write(s);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
