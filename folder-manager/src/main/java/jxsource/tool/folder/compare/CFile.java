package jxsource.tool.folder.compare;

import java.util.List;

/**
 * Extends JFile by adding listFiles method
 *  
 *  TODO: better solution ?
 */
public interface CFile {
	public List<CFile> listFiles();
	// methods in JFile
	public String getName();
	public String getPath();
	public void setPath(String path);
	public long getLength();
	public void setLength(long length);
	public boolean isDirectory();
	public void setDirectory(boolean directory);
	public String getExt();
}
