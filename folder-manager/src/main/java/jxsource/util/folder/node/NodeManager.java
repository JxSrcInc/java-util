package jxsource.util.folder.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxsource.util.folder.search.util.TreeFactory;

/**
 * Keep map between file path and its content CacheFile
 * it is thread-local instance
 * 
 */
public class NodeManager {
	// key: Node path, value: Node
	private Map<String, Node> map = new HashMap<String, Node>();
	
	public NodeManager() {
		
	}
	public void add(Node file) {
		map.put(file.getPath(), file);
	}
	public Node get(String path) {
		return map.get(path);
	}
	public Map<String, Node> getFiles() {
		return map;
	}
	public void reset() {
		map.clear();
	}
	
	public Set<Node> buildTrees() {
		List<Node> list = new ArrayList<Node>(map.size());
		for(Node f: map.values()) {
			list.add(f);
		}
		// added code below to normalize ZipFiles creation.
		Set<Node> trees = new HashSet<Node>();
		for(Node f: TreeFactory.build().createTrees(list)) {
			Node tree = addSegmentToRoot(f);
			trees.add(tree);
		}
		return trees;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Node> Set<T> buildTrees(Class<T> cl) {
		Set<T> trees = new HashSet<T>();
		for(Node node: buildTrees()) {
			trees.add((T)node);
		}
		return trees;
	}
	
	/**
	 * zip file may contain entries in format segment-1/segment-2 and no entry for segment-1.
	 * It is OK for most zip search but cannot create a standard normal tree using only zip file entries.
	 * addSegmentToRoot will add missing segments up to root
	 * @param f
	 * @return
	 */
	private Node addSegmentToRoot(Node f) {
		if(!(f instanceof ZipFile)) {
			throw new RuntimeException(getClass().getName()+".addSegmentToRoot() "
					+ "method only supports ZipFile as input parameter. But it is "+f.getClass().getName());
		}
		if(f.getPath().equals(f.getName())) {
			// root sibling
			if(!map.containsKey(f.getPath())) {
				map.put(f.getPath(), f);
			}
			return f;
		}
		if(map.containsKey(f.getParentPath())) {
			Node parent = map.get(f.getParentPath());
			if(!parent.getChildren().contains(f)) {
				parent.addChild(f);
			}
			return parent;
		}
		int i = f.getPath().lastIndexOf(f.getPathSeparator());
		if(i > 0) {
			String parentPath = f.getPath().substring(0, i);
			ZipFile node = new ZipFile();
			node.setPath(parentPath);
			node.setArray(true);
			node.addChild(f);
			f.setParent(node);
			map.put(parentPath, node);
			return addSegmentToRoot(node);
		} else {
			// this shouldn't happen
			throw new RuntimeException(f.getPath()+" dose not miss path to root");
		}
	}

}
