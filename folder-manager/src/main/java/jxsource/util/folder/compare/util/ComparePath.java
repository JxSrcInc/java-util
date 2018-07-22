package jxsource.util.folder.compare.util;

import jxsource.util.folder.node.Node;

public class ComparePath {
	private String startPath;
	
	public static ComparePath build(String startPath) {
		if(startPath == null || startPath.isEmpty()) {
			throw new RuntimeException("Root path cannot be null or empty.");
		}
		ComparePath ComparePath = new ComparePath();
		ComparePath.startPath = startPath;
		return ComparePath;
	}
	
	public String get(String path) {
		String comparePath = path;
		int i = path.indexOf(startPath);
		if(i != -1) {
			comparePath = path.substring(i+startPath.length());
		}
		return comparePath;
	}
	
	public String get(Node node) {
		return get(node.getPath());
	}
}
