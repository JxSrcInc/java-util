package jxsource.tool.folder.file;

import java.io.IOException;
import java.io.InputStream;

public interface JFile {
	public String getName();
	public String getPath();
	public void setPath(String path);
	public long getLength();
	public void setLength(long length);
	public boolean isDirectory();
	public void setDirectory(boolean directory);
	public String getExt();
	public InputStream getInputStream() throws IOException;
	public void close();
	public long getLastModified();

}
