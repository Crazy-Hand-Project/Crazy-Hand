package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class TextReader {//reads and manages the data stored in text files
	
	public static String[][] getData(String loc,int maxElem){
		String[][] data = null;
		int maxElements = maxElem;//maximum number of parameters that will be read/stored per line.
		try {
			//find length
			LineNumberReader reader = new LineNumberReader(new FileReader(loc));
		    while ((reader.readLine()) != null);//read to end of file to find length
		    int len =  reader.getLineNumber();
		    data = new String[len][maxElements];
		    reader.close();
		    
		    //record values
		    reader = new LineNumberReader(new FileReader(loc));
		    for(int i = 0; i < len; i++){
		    	String[] s = reader.readLine().split("~~");
		    	for(int k = 0; k < maxElements; k++){
		    		if(k>=s.length){
		    			data[i][k] = null;//if there's no more data, make null
		    		}
		    		else{
		    			data[i][k] = s[k];
		    		}
		    		
		    	}
		    }
		    reader.close();
		} catch (IOException e) {e.printStackTrace();}
		
		
		//for(int i = 0; i < data.length; i++){
        	//for(int k = 0; k < data[0].length; k++){
        		//System.out.println(data[i][k]);	
        	//}
        //}
		
		return data;
		
	}


}
