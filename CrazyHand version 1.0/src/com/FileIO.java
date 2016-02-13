package com;

import isotool.filesystem.ISO;
import isotool.filesystem.ISOFile;
import isotool.filesystem.ISOFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileIO {
	
	public static ByteBuffer f;
	public static ISOFileSystem isoFileSystem;
	public static ISO loadedISOFile;
	
	
	
	public static void init(String filename){
		byte[] isoFileData;
		isoFileData = loadedISOFile.getFileSystem().getFileData(filename);
		f = ByteBuffer.wrap(isoFileData);
	}
	public static void init(int character) {
		init("Pl" + Character.characters[character].id+ ".dat");
	}
	
	public static void init() {
		init(MeleeEdit.selected);
	}
	
	public static void initPlCo(){
		init("PlCo.dat");
	}
	
	public static void initItCo(){
		init("ItCo.dat");
	}
	
	public static void initDOL() {
		init("Start.dol");
	}


	public static void loadISOFile() {
		try {
			FileNameExtensionFilter isoFilter = new FileNameExtensionFilter(
					"ISO Files", "iso");
			final JFileChooser fc = new JFileChooser();

			fc.setCurrentDirectory(Options.hasLastIso ? new File(
					Options.isoPath) : new File(System.getProperty("user.dir")));
			fc.addChoosableFileFilter(isoFilter);
			fc.setFileFilter(isoFilter);

			int returnVal = fc.showOpenDialog(MeleeEdit.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				loadedISOFile = new ISO(selectedFile);
				isoFileSystem = loadedISOFile.getFileSystem();
				if (MeleeEdit.frame != null)
					MeleeEdit.updateTitle(fc.getSelectedFile().getName());

				Options.isoPath = fc.getCurrentDirectory().getPath();
				Options.hasLastIso=true;
				Options.saveOptions();
			} else {
				if (loadedISOFile == null)
					throw new RuntimeException("You must select a ISO file!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	
	public static int readByte() {
		int val = 0;
		val = f.get();
		return ((short) (val & 0xff));

	}
	public static int readInt() {
		int val = 0;
		val = f.getInt();
		return val;
	}
	public static float readFloat() {
		float val = 0.9f;
		val = f.getFloat();
		return val;
	}
	
	
	public static void writeByte(int b) {
		f.put((byte) (b & 0xff));
	}
	public static void writeInt(int b) {
		f.putInt(b);
	}

	public static void writeFloat(float b) {
		f.putFloat(b);
	}

	
	public static void setPosition(int pos) {
		f.position(pos);
	}
	
	

	
	
	public static void finalizeSave(){
		try {
			FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(), f.array());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileIO.init();
	}
	
	


	
	
	
}