package jxsource.tool.folder.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface JFile extends Comparable<JFile> {
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
	public List<JFile> getChildren();
	public void setChildren(List<JFile> children);
	public char getFileSeparator();
	public void setParent(JFile parent);
	public JFile getParent();
	public String getParentPath();
	public void addChild(JFile child);
	public JsonNode convertToJson();

}
