package jxsource.util.folder.search.filter.filefilter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;

public class BackDir {
	private static Logger log = LogManager.getLogger(BackDir.class);
	public static String rootDir = System.getProperty("user.dir")
			+System.getProperty("file.separator")+"working-backup";
	private File tempDir = new File(rootDir);
	private Map<String,String> regitory = new HashMap<String,String>();
	
//	public static class TempDirBuilder{
//		private String subWorkingDir;
//		public TempDirBuilder() {
//			this.subWorkingDir = TempDir.rootDir;
//		}
//		public TempDirBuilder setSubWorkingDir(String root) {
//			if(root != null) {
//				this.subWorkingDir = TempDir.rootDir+System.getProperty("file.separator")+root;
//			}
//			return this;
//		}
//		public TempDir build() {
//			return new TempDir(subWorkingDir);
//		}
//		
//	}
//	public static TempDirBuilder builder() {
//		return new TempDirBuilder();
//	}
	public BackDir setWorkingDir(String workingtDir) { 
		if(workingtDir != null) {
			tempDir = new File(rootDir, workingtDir);
		} else {
			tempDir = new File(rootDir);			
		}
		return this;
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
			log.warn("Error when creating back file "+file.getPath(), e);
			return null;
		}
	}
	
	public void clear() {
		delete(tempDir);
	}
	
	private void delete(File file) {
		if(file.isDirectory()) {
			for(File child: file.listFiles()) {
				delete(child);
			}
		}
		file.delete();
	}
}
