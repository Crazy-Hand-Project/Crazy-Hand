/*
 * ISO loading code
 * readSectors() converted. Credits to ie for melee-toolkit c#
 * https://code.google.com/p/melee-toolkit/
 */
package isotool.filesystem;

import isotool.BitConverter;
import isotool.utilities.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Represents a GameCube ISO.
 * 
 * @author darkside1222
 *
 */
public class ISO {

	private final int OffsetFSTOffset = 0x424;

	private long FSTOffset;
	private long numberOfEntries;
	private long stringTableOffset;
	private RandomAccessFile isoFile;
	private File chosenISOFile;
	private ISOFileSystem fileSystem;

	public ISO(File file) throws IOException {
		chosenISOFile = file;
		isoFile = new RandomAccessFile(file, "rw");
		readSectors();
	}

	/**
	 * Reloads the ISO file to allow read and writing to the ISO.
	 * 
	 * @throws FileNotFoundException
	 */
	public void reload() throws FileNotFoundException {
		if (!isOpen())
			isoFile = new RandomAccessFile(chosenISOFile, "rw");
	}

	/**
	 * Attempts to close the ISO. This method should be called if the ISO is
	 * done with reading and writing operations.
	 */
	public void close() {
		if (isoFile != null) {
			try {
				isoFile.close();
				isoFile = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void readSectors() throws IOException {
		try {
			isoFile.seek(OffsetFSTOffset);
			byte[] buffer = new byte[4];
			isoFile.read(buffer, 0, 4);
			Utilities.checkByteOrder(buffer);
			FSTOffset = BitConverter.ToUInt32(buffer, 0);

			isoFile.read(buffer, 0, 4);
			Utilities.checkByteOrder(buffer);
			BitConverter.ToUInt32(buffer, 0);

			isoFile.seek(FSTOffset + 0x8);
			isoFile.read(buffer, 0, 4);
			Utilities.checkByteOrder(buffer);
			numberOfEntries = BitConverter.ToUInt32(buffer, 0);
			stringTableOffset = FSTOffset + (numberOfEntries * 0xC);
			initFileSystem();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the file system for the ISO.
	 */
	private void initFileSystem() {
		ISOFile rootFolder = new ISOFile("root", 0, 0, FSTOffset, true, null);
		fileSystem = new ISOFileSystem(this, rootFolder);
		close();
	}

	/**
	 * Is the ISO open?
	 * 
	 * @return true if the ISO is open or instanced.
	 */
	public boolean isOpen() {
		return isoFile != null;
	}

	public RandomAccessFile getISO() {
		return isoFile;
	}

	public ISOFileSystem getFileSystem() {
		return fileSystem;
	}

	public File getChosenISOFile() {
		return chosenISOFile;
	}

	public long getFSTOffset() {
		return FSTOffset;
	}

	public long getNumberofEntries() {
		return numberOfEntries;
	}

	public long getStringTableOffset() {
		return stringTableOffset;
	}

}
