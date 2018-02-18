package jxsource.tool.folder.file;

import java.util.HashMap;
import java.util.Map;

/**
 * Keep map between file path and its content CacheFile
 * it is thread-local instance
 * 
 */
public class FileManager {
	// key: JFile path, value: JFile
	private Map<String, JFile> map = new HashMap<String, JFile>();
	
	FileManager() {
		
	}
	public void add(JFile file) {
		map.put(file.getPath(), file);
	}
	public JFile get(String path) {
		return map.get(path);
	}
	public Map<String, JFile> getFiles() {
		return map;
	}
	public void reset() {
		map.clear();
	}
}
