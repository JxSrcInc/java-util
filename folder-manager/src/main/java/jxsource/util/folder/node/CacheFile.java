package jxsource.util.folder.node;

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
		NodeManagerHolder.get().add(this);
	}
	
	// Wrap the same method of T - JFile method
	public char getPathSeparator() {

		return cache.getPathSeparator();
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
	public boolean isDir() {
		return cache.isDir();
	}

	// Wrap the same method of T - JFile method
	public void setArray(boolean directory) {
		cache.setArray(directory);
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
	public List<Node> getChildren() {
		return cache.getChildren();
	}

	@Override
	public void setChildren(List<Node> children) {
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
	public int compareTo(Node o) {
		if(o == null) {
			return -1;
		}
		return cache.getPath().compareTo(o.getPath());
	}

	@Override
	public void setName(String name) {
		cache.setName(name);
	}

	@Override
	public void setContent(Object content) {
		if(content instanceof byte[]) {
			this.content = (byte[]) content;
		}
	}

	@Override
	public Node getParent() {
		return cache.getParent();
	}

	@Override
	public void setParent(Node node) {
		cache.setParent(node);
		
	}

	@Override
	public void addChild(Node child) {
		cache.addChild(child);
	}

	@Override
	public String getParentPath() {
		return cache.getParentPath();
	}

	@Override
	public JsonNode convertToJson() {
		return cache.convertToJson();
	}

	@Override
	public void setAbsolutePath(String absolutePath) {
		cache.setAbsolutePath(absolutePath);
	}

	@Override
	public String getAbsolutePath() {
		return cache.getAbsolutePath();
	}

	@Override
	public String toString() {
		return "CacheFile [getPath()=" + getPath() + "]";
	}

}
