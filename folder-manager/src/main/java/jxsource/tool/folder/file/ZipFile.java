package jxsource.tool.folder.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFile extends AbstractJFile{
	private ZipInputStream zis;
	private long lastModified;
	public ZipFile(ZipEntry zipEntry, ZipInputStream zis) {
		super("/");
		setPath(zipEntry.getName());
		setLength(zipEntry.getSize());
		setDirectory(zipEntry.isDirectory());
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
	
}
