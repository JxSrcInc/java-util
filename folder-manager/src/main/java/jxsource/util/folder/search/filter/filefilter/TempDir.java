package jxsource.util.folder.search.filter.filefilter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jxsource.util.folder.node.JFile;

public class TempDir {
	public static String defaultTempDir = "c:\\temp";
	private File tempDir;
	private Map<String,String> regitory = new HashMap<String,String>();
	
	public static class TempDirBuilder{
		private String subWorkingDir;
		public TempDirBuilder() {
			this.subWorkingDir = TempDir.defaultTempDir;
		}
		public TempDirBuilder setSubWorkingDir(String root) {
			if(root != null) {
				this.subWorkingDir = TempDir.defaultTempDir+System.getProperty("file.separator")+root;
			}
			return this;
		}
		public TempDir build() {
			return new TempDir(subWorkingDir);
		}
		
	}
	public static TempDirBuilder builder() {
		return new TempDirBuilder();
	}
	private TempDir(String rootDir) {
		tempDir = set(rootDir);
	}
	public File set(String name) {
		tempDir = new File(name);
		if(!tempDir.exists()) {
			if(!tempDir.mkdirs()) {
				throw new RuntimeException("Cannot create dir "+name);
			}
		}
		return tempDir;
	}
	public File get() {
		return tempDir;
	}
	
	public File createTempFile(JFile src) {
		String suffix = Long.toHexString(System.currentTimeMillis());
		File parent = new File(get(), src.getParentPath());
		if(!parent.exists()) {
			parent.mkdirs();
		}
		File file = new File(parent, src.getName()+'-'+suffix);
		try {
			file.createNewFile();
			regitory.put(file.getPath(), src.getAbsolutePath());
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
