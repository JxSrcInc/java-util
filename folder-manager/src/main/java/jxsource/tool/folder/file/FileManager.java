package jxsource.tool.folder.file;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
	private Map<String, AbstractJFile> map = new HashMap<String, AbstractJFile>();
	private static FileManager me;
	
	private FileManager() {
		
	}
	public void add(AbstractJFile file) {
		map.put(file.getPath(), file);
	}
	public AbstractJFile get(String path) {
		return map.get(path);
	}
	public static FileManager getInstance() {
		if(me == null) {
			me = new FileManager();
		}
		return me;
	}
	public Map<String, AbstractJFile> getMap() {
		return map;
	}
	public void reset() {
		map.clear();
	}
}
