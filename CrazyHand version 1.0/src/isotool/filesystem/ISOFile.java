/*
 * ISO loading code
 * Converted. Credits to ie for melee-toolkit c#
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
 * 
 * @author darkside1222
 *
 */
public class ISOFile {

	private final int OffsetFSTOffset = 0x424;

	private long FSTOffset;
	private long numberOfEntries;
	private long stringTableOffset;
	private RandomAccessFile isoFile;
	private File chosenISOFile;
	private ISOFileSystem fileSystem;

	public ISOFile(File file) throws IOException {
		chosenISOFile = file;
		isoFile = new RandomAccessFile(file, "rw");
		readSectors();
	}

	private void readSectors() throws IOException {
		try {
			/*
			 * TODO Start.dol and other &&systemdata files if (isoFile .length()
			 * > 0x438L) { isoFile.seek(0x400L);
			 * 
			 * int pos5;
			 * 
			 * int size; int fileOff; //toc.fils.get(2).pos = 0; //
			 * toc.fils.get(2).len = 0x2440; // toc.fils.get(3).pos = 0x2440;
			 * isoFile.readInt(); isoFile.seek(isoFile.getChannel().position() +
			 * 0x1cL); fileOff = isoFile.readInt(); pos5 = isoFile.readInt();
			 * isoFile.readInt(); size = pos5 - fileOff;
			 * isoFile.seek(isoFile.getChannel().position() + 8L);
			 * isoFile.readInt(); //(int) isoFile.length();
			 * 
			 * startDol = new ISOFileInfo("Start.dol", size,fileOff, FSTOffset,
			 * false);
			 * 
			 * }
			 * 
			 * /
			 */

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
			createRootFolders();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void createRootFolders() {
		ISOFileInfo rootFileInfo = new ISOFileInfo("root", 0, 0, FSTOffset,
				true, null);
		fileSystem = new ISOFileSystem(this, rootFileInfo);

		close();
	}

	/**
	 * Reloads the ISO file for reading and writing.
	 * 
	 * @throws FileNotFoundException
	 */
	public void reload() throws FileNotFoundException {
		// TODO message box saying that process is still open
		isoFile = new RandomAccessFile(chosenISOFile, "rw");
	}

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

	public boolean isOpen() {
		return isoFile != null;
	}

	public RandomAccessFile getISO() {
		return isoFile;
	}

	public ISOFileSystem getFileSystem() {
		return fileSystem;
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
