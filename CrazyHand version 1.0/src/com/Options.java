package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
				System.out.println(o);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static boolean hasLastIso = false;
	public static String isoPath = "";
	public static boolean rawEnabled = false;
	
	public static void saveOptions()
	{
		File f = new File("options.cfg");
		
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		try {
			PrintWriter out = new PrintWriter("options.cfg");
			
			String ln = System.lineSeparator();
			
			if(MeleeEdit.restorePane != null)
				out.write("optRawData:" + MeleeEdit.restorePane.rawBox.isSelected()+ln);
			
			out.write("optLastISO:" + isoPath+ln);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
