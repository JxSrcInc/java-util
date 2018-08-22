package jxsource.util.folder.node;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFile extends AbstractNode implements JFile {
	private static final long serialVersionUID = 1L;
	public static final String CachePropertyName = "jxsource.util.folder.zipfile.cache";
	public static final String NoCache = "NoCache";
	public static final String Memory = "Memory";
	public static final String File = "File";
	public static String cache;
	private ZipEntry zipEntry;
	private byte[] content;

	static {
		cache = System.getProperty(CachePropertyName);
		if (cache == null || (!cache.equals(Memory)) && (!cache.equals(File))) {
			cache = NoCache;
		}
	}

	public ZipFile() {
		
	}
	public ZipFile(String uri, ZipEntry zipEntry, ZipInputStream zis) {
		this.zipEntry = zipEntry;
		String path = zipEntry.getName();
		// in zip, the last char of path is '/' if zipEntry is not file
		// so remove it
		if (path.charAt(path.length() - 1) == '/') {
			path = path.substring(0, path.length() - 1);
		}
		setPath(path);
		setAbsolutePath(uri + '#' + path);
		setLength(zipEntry.getSize());
		setArray(zipEntry.isDirectory());
		lastModified = zipEntry.getLastModifiedTime().toMillis();
		if (!this.isDir()) {
			switch (cache) {
			case Memory:
				try {
					loadContent(zis);
				} catch (IOException e) {
					throw new RuntimeException("Error when loading content for " + getAbsolutePath(), e);
				}

				break;
			case File:
			default:
			}
		}
	}

	private void loadContent(ZipInputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024 * 8];
		int i = 0;
		while ((i = in.read(b)) != -1) {
			out.write(b, 0, i);
		}
		out.flush();
		out.close();
		content = out.toByteArray();

	}

	public InputStream getInputStream() throws IOException {
		// InputStream must create every time because it will be consumed by caller
		return new ByteArrayInputStream(content);
	}

	public void close() {
		// do nothing
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public long getLastModified() {
		return lastModified;
	}

	@Override
	public List<Node> getChildren() {
		if (children == null) {
			throw new RuntimeException("Children in zip@" + path + " has not been set. Use TreeFactory to setup.");
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
		if (content instanceof ZipEntry && zipEntry == null) {
			zipEntry = (ZipEntry) content;
		}

	}

}
