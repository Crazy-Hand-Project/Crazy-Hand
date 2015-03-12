package isotool.filesystem;

import isotool.BitConverter;
import isotool.utilities.Utilities;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

/**
 * 
 * Contains functions to modify and open files within the SSBM ISO.
 * 
 * @author darkside1222
 *
 */
public class ISOFileSystem {

	private HashMap<String, ISOFile> cachedISOFiles;

	private ISO isoFile;
	private ISOFile currentLoadedFile;

	public ISOFileSystem(ISO iso, ISOFile isoFile) {
		cachedISOFiles = new HashMap<String, ISOFile>();
		this.isoFile = iso;
		this.currentLoadedFile = isoFile;
		try {
			readFiles(isoFile, iso.getStringTableOffset());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads all of the file's information that is in the ISO.
	 * 
	 * @param isoFolder
	 *            - The folder's file information.
	 * @param offset
	 *            - the fst offset.
	 * @throws IOException
	 */
	private void readFiles(ISOFile isoFolder, long offset) throws IOException {
		long currentOffset = isoFolder.fstOffset + 0xC;
		while (currentOffset < offset) {
			isoFile.getISO().seek(currentOffset);
			boolean isFolder = isoFile.getISO().readByte() == 1;

			byte[] buffer = new byte[4];
			isoFile.getISO().read(buffer, 1, 3);
			buffer[0] = 0;
			Utilities.checkByteOrder(buffer);
			long stringOffset = BitConverter.ToUInt32(buffer, 0);
			String isoFileName = getAbsoluteFileName(stringOffset);

			isoFile.getISO().read(buffer, 0, 4);
			Utilities.checkByteOrder(buffer);
			long fileOffset = BitConverter.ToUInt32(buffer, 0);

			isoFile.getISO().read(buffer, 0, 4);
			Utilities.checkByteOrder(buffer);

			long fileSize = BitConverter.ToUInt32(buffer, 0);

			ISOFile file = new ISOFile(isoFileName, fileSize, fileOffset,
					currentOffset, isFolder, null);

			if (isMovesetFile(isoFileName)) {
				cachedISOFiles.put(isoFileName, file);
				try {
					readData(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			currentOffset += 0xC;

			if (isFolder) {
				currentOffset = isoFile.getFSTOffset() + (fileSize * 0xC);
				readFiles(file, currentOffset);
			}
		}

	}

	/**
	 * Reads the given ISO file into bytes and stores them for later use.
	 * 
	 * @param file
	 *            - the file to read.
	 * @throws Exception
	 */
	private void readData(ISOFile file) throws Exception {

		try {

			long fileSize = file.size;
			long offset = file.fileOffset;
			long totalLength = file.size + file.fileOffset;

			FileChannel inChannel = isoFile.getISO().getChannel();

			while (offset < totalLength) {
				ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
				long len = offset + fileSize < totalLength ? fileSize
						: totalLength - offset;
				inChannel.read(buffer, offset);
				buffer.flip();
				file.setData(buffer.array());
				buffer.clear();
				offset += len;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the file name that is stored in the ISO.
	 * 
	 * @param stringOffset
	 * @return
	 * @throws IOException
	 */
	private String getAbsoluteFileName(long stringOffset) throws IOException {
		String filename = "";
		if (isoFile.getStringTableOffset() == 0)
			return null;

		long position = isoFile.getISO().getFilePointer();

		isoFile.getISO().seek(isoFile.getStringTableOffset() + stringOffset);
		int buffer = 1;
		while (buffer != 0) {
			buffer = isoFile.getISO().readByte();
			if (buffer != 0)
				filename += (char) buffer;
		}
		isoFile.getISO().seek(position);
		return filename;
	}

	/**
	 * Checks if the file is a character moveset file. An example of a character
	 * file would be: <b>PlBo.dat</b> for Bowser.
	 * 
	 * @param name
	 * @return
	 */
	private boolean isMovesetFile(String name) {// should be 34 files
		return (name.startsWith("Pl") && name.replace(".dat", "").length() == 4);
	}

	/**
	 * Replaces a file in the ISO. The files must have the same size!
	 * 
	 * @param file
	 *            - The ISO file to replace.
	 * @param data
	 *            - The new file's data.
	 * @throws IOException
	 */
	public boolean replaceFile(ISOFile file, byte[] data) throws IOException {
		if (isoFile.isOpen()) {
			if (file.size != data.length) {
				System.out.println("Length does not match!"
						+ " File to Replace: " + file.size + " With: "
						+ data.length);
				return false;
			}
			isoFile.getISO().seek(file.fileOffset);
			isoFile.getISO().write(data, 0, data.length);
			file.setData(data);

			return true;
		}
		return false;

	}

	/**
	 * Opens a file.
	 * 
	 * @param fileName
	 *            - the file's name.
	 * @return - the file's data.
	 * @throws IOException
	 */
	public byte[] openFile(String fileName) throws IOException {
		ISOFile file = getISOFile(fileName);
		if (file.data != null) {
			currentLoadedFile = file;
			return file.data;
		}

		return null;
	}

	public ISOFile getISOFile(String fileName) {
		ISOFile info = cachedISOFiles.get(fileName);
		if (info == null) {
			throw new RuntimeException("Could not load cached ISO file!");
		}
		return info;
	}

	public void clearCachedISOFiles() {
		cachedISOFiles.clear();

	}

	public HashMap<String, ISOFile> getISOFiles() {
		return cachedISOFiles;
	}

	public ISOFile getCurrentFile() {
		return currentLoadedFile;
	}

}
