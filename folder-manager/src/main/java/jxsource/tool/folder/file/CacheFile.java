package jxsource.tool.folder.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CacheFile<T extends AbstractJFile> implements JFile{

	private T cache;
	private byte[] content;
	private ByteArrayInputStream inputStream;
	public CacheFile(T cache) throws IOException {
		this.cache = cache;
		loadContent();
	}
	
	public String getName() {
		return cache.getName();
	}

	public String getPath() {
		return cache.getPath();
	}

	public void setPath(String path) {
		cache.setPath(path);
	}

	public long getLength() {
		return cache.getLength();
	}

	public void setLength(long length) {
		cache.setLength(length);
	}

	public boolean isDirectory() {
		return cache.isDirectory();
	}

	public void setDirectory(boolean directory) {
		cache.setDirectory(directory);
	}

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
	public InputStream getInputStream() throws IOException {
		inputStream = new ByteArrayInputStream(content);
		return inputStream;
	}

	public byte[] getContent() {
		return content;
	}
	public void close() {
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
