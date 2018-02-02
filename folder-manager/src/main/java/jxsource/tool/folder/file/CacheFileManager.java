package jxsource.tool.folder.file;

import java.util.HashMap;
import java.util.Map;

public class CacheFileManager {
	// key: CacheFile path, value: CacheFile
	private Map<String, CacheFile> map = new HashMap<String, CacheFile>();
	
	CacheFileManager() {
		
	}
	public void add(CacheFile file) {
		map.put(file.getPath(), file);
	}
	public JFile get(String path) {
		return map.get(path);
	}
	public Map<String, CacheFile> getCacheFiles() {
		return map;
	}
	public void reset() {
		map.clear();
	}
}
