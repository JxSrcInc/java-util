package jxsource.tool.folder.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SysFile extends AbstractJFile{
	private static Logger log = LogManager.getLogger(SysFile.class);
	private File file;
	private InputStream in;
	public SysFile(File file) {
		super(System.getProperty("file.separator").charAt(0));
		String path = file.getPath();
		if(path.length()>=2 && path.charAt(0)=='.' && path.charAt(1)==fileSeparator) {
			if(path.length()>2) {
				path = path.substring(2);
			} else {
				path = ".";
			}
		} 
		setPath(path);
		setLength(file.length());
		setDirectory(file.isDirectory());
		this.file = file;
		lastModified = file.lastModified();
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
	public List<JFile> getChildren() {
		if(children == null) {
			File[] files = file.listFiles();
			children = new ArrayList<JFile>();
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
}
