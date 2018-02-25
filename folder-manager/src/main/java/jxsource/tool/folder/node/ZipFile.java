package jxsource.tool.folder.node;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFile extends AbstractNode implements JFile {
	private ZipInputStream zis;
	private ZipEntry zipEntry;
	
	public ZipFile(ZipEntry zipEntry, ZipInputStream zis) { 
		this.zipEntry = zipEntry;
		String path = zipEntry.getName();
		// in zip, the last char of path is '/' if zipEntry is not file
		// so remove it
		if(path.charAt(path.length()-1) == '/') {
			path = path.substring(0, path.length()-1);
		}
		setPath(path);
		setLength(zipEntry.getSize());
		setArray(zipEntry.isDirectory());
		lastModified = zipEntry.getLastModifiedTime().toMillis();
		this.zis = zis;
	}

	public InputStream getInputStream() throws IOException {
		return zis;
	}

	public void close() {
		// do nothing
	}

	@Override
	protected void finalize() throws Throwable {
		zis = null;
		super.finalize();
	}

	@Override
	public long getLastModified() {
		return lastModified;
	}

	@Override
	public List<Node> getChildren() {
		if(children == null) {
			throw new RuntimeException("Children in zip@"+path+" has not been set. Use TreeFactory to setup.");
		} else {
			return super.getChildren();
		}
	}

	@Override
	public Object getContent() {
		return zipEntry;
	}

	@Override
	public void setContent(Object content) {
		if(content instanceof ZipEntry && zipEntry == null) {
			zipEntry = (ZipEntry) content;
		}
		
	}
	
}
