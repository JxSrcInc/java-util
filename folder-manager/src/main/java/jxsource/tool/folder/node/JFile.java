package jxsource.tool.folder.node;

import java.io.IOException;
import java.io.InputStream;

public interface JFile extends Node {
	public long getLength();
	public void setLength(long length);
	public String getExt();
	public InputStream getInputStream() throws IOException;
	public void close();
	public long getLastModified();

}
