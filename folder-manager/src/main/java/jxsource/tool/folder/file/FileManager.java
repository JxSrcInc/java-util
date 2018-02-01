package jxsource.tool.folder.file;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
	private Map<String, JFile> map = new HashMap<String, JFile>();
	
	FileManager() {
		
	}
	public void add(JFile file) {
		map.put(file.getPath(), file);
	}
	public JFile get(String path) {
		return map.get(path);
	}
	public Map<String, JFile> getMap() {
		return map;
	}
	public void reset() {
		map.clear();
	}
}
