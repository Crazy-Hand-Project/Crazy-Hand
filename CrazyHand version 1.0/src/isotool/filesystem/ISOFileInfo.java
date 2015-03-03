package isotool.filesystem;

public class ISOFileInfo {

	String name;
	long size, fileOffset, fstOffset;
	boolean isFolder;
	
	byte[] data;

	public ISOFileInfo(String name, long size, long fileOffset, long fstOffset,
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