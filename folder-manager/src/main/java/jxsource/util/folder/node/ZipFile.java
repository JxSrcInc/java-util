package jxsource.util.folder.node;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZipFile extends AbstractNode implements JFile {
	private static Logger log = LogManager.getLogger(ZipFile.class);
	private static final long serialVersionUID = 1L;
	public static final String ZipTempDir = "jxsource.util.folder.zipfile.temp-dir";
	public static final String ZipExtract = "jxsource.util.folder.zipfile.extract";
	private static String zipTempDir = "zip-temp";
	private static boolean extract;
	private ZipEntry zipEntry;
	private InputStream in;
	private File tempFile;

	static {
		extract = (System.getProperty(ZipExtract) != null);
		String _zipTempDir = System.getProperty(ZipTempDir);
		if (_zipTempDir != null) {
			zipTempDir = _zipTempDir.trim();
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
		if (!this.isDir() && zipTempDir != null) {
			try {
				loadContent(zis);
			} catch (IOException e) {
				throw new RuntimeException("Error when loading content for " + getAbsolutePath(), e);
			}
		}
	}

	public void loadContent(ZipInputStream in) throws IOException {
		String parentPath = this.getParentPath();
		File parent = new File(zipTempDir, parentPath);
		if (!parent.exists()) {
			if (!parent.mkdirs()) {
				throw new IOException("Cannot create " + parent.getPath());
			}
		}
		tempFile = new File(zipTempDir, path);
		tempFile.deleteOnExit();
		OutputStream out = new FileOutputStream(tempFile);
		byte[] b = new byte[1024 * 8];
		int i = 0;
		while ((i = in.read(b)) != -1) {
			out.write(b, 0, i);
		}
		out.flush();
		out.close();

	}

	public InputStream getInputStream() throws IOException {
		// InputStream must create every time because it will be consumed by caller
		return in = new FileInputStream(new File(zipTempDir, path));
	}

	public void close() {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Error when closing " + tempFile.getPath());
			} finally {
				in = null;
			}
		}
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
