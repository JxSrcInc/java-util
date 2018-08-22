package jxsource.util.folder.node;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SysFile extends AbstractNode implements JFile{
	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(SysFile.class);
	private File file;
	private InputStream in;
	private String userDir = System.getProperty("user.dir");
	public SysFile(File file) {
		char fileSeparator = System.getProperty("file.separator").charAt(0);
		String path = file.getPath().replace(fileSeparator, '/');
		setPath(path);
		String absolutePath = file.getAbsolutePath().replace(fileSeparator, '/');
		setAbsolutePath(absolutePath);
		setLength(file.length());
		setArray(file.isDirectory());
		this.file = file;
		lastModified = file.lastModified();
	}

	public String getRelativePath() {
		return getPath().substring(userDir.length());
	}
	@Override
	public void setPath(String path) {
		path = path.replaceAll("\\\\", "/");
		super.setPath(path);
	}

	public InputStream getInputStream() throws IOException {
		return (in = new FileInputStream(file));
	}
	public void close() {
		if(in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Error when closing "+file.getPath());
			} finally {
				in = null;
			}
		}
	}
	@Override
	protected void finalize() throws Throwable {
		close();
		file = null;
		super.finalize();
	}
	@Override
	public long getLastModified() {
		return file.lastModified();
	}

	/**
	 * SysFile always uses file system's children
	 */
	@Override
	public List<Node> getChildren() {
		if(children.size() == 0) {
			File[] files = file.listFiles();
			if(files != null) {
				for(int i=0; i<files.length; i++) {
					JFile child = new SysFile(files[i]);
					child.setParent(this);
					children.add(child);
				}
			}
		}
		return children;
	}

	@Override
	public Object getContent() {
		return file;
	}

	@Override
	public void setContent(Object content) {
		if(content instanceof File && file == null) {
			file = (File) content;
		}
	}	
	
	// TODO: need to update parent
	public boolean delete() {
		return file.delete();
	}
}
