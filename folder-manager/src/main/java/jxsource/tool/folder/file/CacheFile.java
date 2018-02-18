package jxsource.tool.folder.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * extended JFile contains file content in memory
 */
public class CacheFile implements JFile{

	private JFile cache;
	private byte[] content;
	private ByteArrayInputStream inputStream;
	public CacheFile(JFile cache) {
		this.cache = cache;
		try {
			loadContent();
		} catch (IOException e) {
			throw new RuntimeException("Error when loading content for "+cache.getPath(), e);
		}
		// register the file in CacheFileManager
		FileManagerHolder.get().add(this);
	}
	
	// Wrap the same method of T - JFile method
	public char getFileSeparator() {

		return cache.getFileSeparator();
	}
	// Wrap the same method of T - JFile method
	public String getName() {
		return cache.getName();
	}

	// Wrap the same method of T - JFile method
	public String getPath() {
		return cache.getPath();
	}

	// Wrap the same method of T - JFile method
	public void setPath(String path) {
		cache.setPath(path);
	}

	// Wrap the same method of T - JFile method
	public long getLength() {
		return cache.getLength();
	}

	// Wrap the same method of T - JFile method
	public void setLength(long length) {
		cache.setLength(length);
	}

	// Wrap the same method of T - JFile method
	public boolean isDirectory() {
		return cache.isDirectory();
	}

	// Wrap the same method of T - JFile method
	public void setDirectory(boolean directory) {
		cache.setDirectory(directory);
	}

	// Wrap the same method of T - JFile method
	public String getExt() {
		return cache.getExt();
	}

	private void loadContent() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024*8];
		int i = 0;
		InputStream in = cache.getInputStream();
		while((i=in.read(b)) != -1) {
			out.write(b,0,i);
		}
		out.flush();
		out.close();
		cache.close();
		content = out.toByteArray();
		
	}
	// Wrap the same method of T - JFile method
	// But return input stream for content
	public InputStream getInputStream() throws IOException {
		inputStream = new ByteArrayInputStream(content);
		return inputStream;
	}

	// new method for content access
	public byte[] getContent() {
		return content;
	}
	// new method for content access
	public void close() {
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// new method for content export
	public void export(OutputStream out) throws IOException {
		out.write(content);
	}

	@Override
	public long getLastModified() {
		return cache.getLastModified();
	}

	@Override
	public List<JFile> getChildren() {
		return cache.getChildren();
	}

	@Override
	public void setChildren(List<JFile> children) {
		cache.setChildren(children);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cache.getPath() == null) ? 0 : cache.getPath().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheFile other = (CacheFile) obj;
		if (cache.getPath() == null) {
			if (other.cache.getPath() != null)
				return false;
		} else if (!cache.getPath().equals(other.cache.getPath()))
			return false;
		return true;
	}
	@Override
	public int compareTo(JFile o) {
		if(o == null) {
			return -1;
		}
		return cache.getPath().compareTo(o.getPath());
	}

	@Override
	public void setParent(JFile parent) {
		cache.setParent(parent);
	}

	@Override
	public JFile getParent() {
		return cache.getParent();
	}
	@Override 
	public String getParentPath() {
		return cache.getParentPath();
	}

	@Override
	public void addChild(JFile child) {
		cache.addChild(child);
		
	}

	@Override
	public JsonNode convertToJson() {
		return cache.convertToJson();
	}
}
