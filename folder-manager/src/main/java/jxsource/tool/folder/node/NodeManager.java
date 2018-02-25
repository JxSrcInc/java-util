package jxsource.tool.folder.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxsource.tool.folder.search.util.TreeFactory;

/**
 * Keep map between file path and its content CacheFile
 * it is thread-local instance
 * 
 */
public class NodeManager {
	// key: JFile path, value: JFile
	private Map<String, Node> map = new HashMap<String, Node>();
	
	NodeManager() {
		
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
	
	/**
	 * zip file may contain entries in format segment-1/segment-2 and no entry for segment-1.
	 * It is OK for most zip search but cannot create a standard normal tree using only entries zip file.
	 * addSegmentToRoot will add missing segments up to root
	 * @param f
	 * @return
	 */
	private Node addSegmentToRoot(Node f) {
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
			NodeImpl node = new NodeImpl();
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
