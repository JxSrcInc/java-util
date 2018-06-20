package jxsource.util.folder.node;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface of Java File Wrap
 * @author JiangJxSrc
 *
 */
public interface JFile extends Node {
	public long getLength();
	public void setLength(long length);
	public String getExt();
	public InputStream getInputStream() throws IOException;
	public void close();
	public long getLastModified();

}
