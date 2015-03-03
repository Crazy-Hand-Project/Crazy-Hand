package isotool.filesystem;

import isotool.BitConverter;
import isotool.utilities.Utilities;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;

/**
 * 
 * Contains functions to modify and open files within the SSBM ISO.
 * 
 * @author darkside1222
 *
 */
public class ISOFileSystem {

	private HashMap<String, ISOFileInfo> cachedISOFiles;

	private ISOFile isoFile;

	private ISOFileInfo currentLoadedFileInfo;

	public ISOFileSystem(ISOFile isoFile, ISOFileInfo fileInfo) {
		cachedISOFiles = new HashMap<String, ISOFileInfo>();
		this.isoFile = isoFile;
		this.currentLoadedFileInfo = fileInfo;
		try {
			seekFiles(fileInfo, isoFile.getStringTableOffset());
			setFileData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the cached ISO file's data and sets it.
	 * 
	 * @throws Exception
	 */
	private void setFileData() throws Exception {
		MappedByteBuffer b = isoFile
				.getISO()
				.getChannel()
				.map(MapMode.READ_WRITE, 0,
						isoFile.getISO().getChannel().size());

		for (ISOFileInfo info : cachedISOFiles.values()) {

			byte[] outFile = new byte[(int) info.size];
			b.position((int) info.fileOffset);
			for (int i = 0; i < info.size; i++) {
				outFile[i] = (byte) b.get();
			}

			info.setData(outFile);
		}

		Utilities.unmap(isoFile.getISO().getChannel(), b);
	}

	private void seekFiles(ISOFileInfo folderFileInfo, long offset)
			throws IOException {
		long currentOffset = folderFileInfo.fstOffset + 0xC;
		while (currentOffset < offset) {
			isoFile.getISO().seek(currentOffset);
			boolean isFolder = isoFile.getISO().readByte() == 1;

			byte[] buffer = new byte[4];
			isoFile.getISO().read(buffer, 1, 3);
			buffer[0] = 0;
			Utilities.checkByteOrder(buffer);
			long stringOffset = BitConverter.ToUInt32(buffer, 0);
			String isoFileName = getFileName(stringOffset);

			isoFile.getISO().read(buffer, 0, 4);
			Utilities.checkByteOrder(buffer);
			long fileOffset = BitConverter.ToUInt32(buffer, 0);

			isoFile.getISO().read(buffer, 0, 4);
			Utilities.checkByteOrder(buffer);

			long fileSize = BitConverter.ToUInt32(buffer, 0);

			ISOFileInfo fileInfo = new ISOFileInfo(isoFileName, fileSize,
					fileOffset, currentOffset, isFolder, null);

			if (isoFileName.startsWith("Pl")) { // only adds fighter files for
												// now

				cachedISOFiles.put(isoFileName, fileInfo);
			}

			currentOffset += 0xC;

			if (isFolder) {
				currentOffset = isoFile.getFSTOffset() + (fileSize * 0xC);
				seekFiles(fileInfo, currentOffset);
			}
		}

	}

	private String getFileName(long stringOffset) throws IOException {
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
	 * Replaces a file in the ISO. The files must have the same size!
	 * 
	 * @param info
	 *            - The ISO file to replace.
	 * @param data
	 *            - The new file's data.
	 * @throws IOException
	 */
	public boolean replaceFile(ISOFileInfo info, byte[] data)
			throws IOException {
		if (isoFile.isOpen()) {
			if (info.size != data.length) {
				System.out.println("Length does not match!"
						+ " File to Replace: " + info.size + " With: "
						+ data.length);
				return false;
			}
			isoFile.getISO().seek(info.fileOffset);
			isoFile.getISO().write(data, 0, data.length);
			info.setData(data);

			return true;
		}
		return false;

	}

	/**
	 * Opens and gets the file's data.
	 * 
	 * @param fileName
	 *            - The ISO file's name.
	 * @return - the ISO file's data.
	 * @throws IOException
	 */
	public byte[] openFile(String fileName) throws IOException {
		ISOFileInfo info = getFileInfo(fileName);
		if (info.data != null) {
			currentLoadedFileInfo = info;
			return info.data;
		}

		return null;
	}

	public ISOFileInfo getFileInfo(String fileName) {
		ISOFileInfo info = cachedISOFiles.get(fileName);
		if (info == null) {
			System.out.println("Could not load file info!");
			return null;
		}
		return info;
	}

	public void clearLoadedFiles() {
		cachedISOFiles.clear();

	}

	public HashMap<String, ISOFileInfo> getISOFiles() {
		return cachedISOFiles;
	}

	public ISOFileInfo getCurrentFileInfo() {
		return currentLoadedFileInfo;
	}

}
