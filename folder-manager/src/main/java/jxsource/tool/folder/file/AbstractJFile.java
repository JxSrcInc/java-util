package jxsource.tool.folder.file;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractJFile implements JFile{
	private String name;
	private String path;
	private long length;
	private boolean directory;
	private String fileSeparator;
//	public abstract InputStream getInputStream() throws IOException;
//	public abstract void close();
	
	protected AbstractJFile(String fileSeparator) {
		this.fileSeparator = fileSeparator;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
		int i = path.lastIndexOf(fileSeparator);
		if(i > 2) {
			name = path.substring(i+1);
		} else {
			name = path;
		}
//		FileManagerHolder.get().add(this);
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public boolean isDirectory() {
		return directory;
	}
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}
	public String getExt() {
		int i = name.lastIndexOf('.');
		return name.substring(i+1);
	}
	@Override
	public String toString() {
		return path + ',' + length;
	}
}
