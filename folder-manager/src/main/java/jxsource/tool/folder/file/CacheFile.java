package jxsource.tool.folder.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * It is a wrapper of type T but with content of file T
 * It has all methods of T plus content access methods
 * @author JiangJxSrc
 *
 * @param <T>
 */
public class CacheFile<T extends AbstractJFile> implements JFile{

	private T cache;
	private byte[] content;
	private ByteArrayInputStream inputStream;
	public CacheFile(T cache) throws IOException {
		this.cache = cache;
		loadContent();
		// re-register in FileManager with the same key (file path) 
		// but different value - replace T with CacheFile<T>
		// refer AbstractJFile.setPath() method
		FileManagerHolder.get().add(this);
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

}
