package isotool.filesystem;

/**
 * Contains information about a ISO file.
 * 
 * @author darkside1222.
 *
 */
public class ISOFile {

	String name;
	long size, fileOffset, fstOffset;
	boolean isFolder;

	byte[] data;

	public ISOFile(String name, long size, long fileOffset, long fstOffset,
			boolean isFolder, byte[] data) {
		this.name = name;
		this.size = size;
		this.fileOffset = fileOffset;
		this.fstOffset = fstOffset;
		this.isFolder = isFolder;
		this.data = data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}