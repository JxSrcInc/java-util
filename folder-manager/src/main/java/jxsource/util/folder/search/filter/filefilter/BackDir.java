package jxsource.util.folder.search.filter.filefilter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.util.NodeUtil;

public class BackDir {
	private static Logger log = LogManager.getLogger(BackDir.class);
	public static String rootDir = System.getProperty("user.dir")
			+System.getProperty("file.separator")+"working-backup";
	private File backDir = new File(rootDir);
	private Map<String,String> regitory = new HashMap<String,String>();
	
	public BackDir setWorkingDir(String workingtDir) { 
		if(workingtDir != null) {
			backDir = new File(rootDir, workingtDir);
		} else {
			backDir = new File(rootDir);			
		}
		return this;
	}
	public File get() {
		return backDir;
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
	
	@SuppressWarnings("unchecked")
	public void clear() {
		log.info("clear: "+backDir);
		NodeUtil.procTree(new SysFile(backDir), sysFile -> sysFile.delete());
	}
	
}
