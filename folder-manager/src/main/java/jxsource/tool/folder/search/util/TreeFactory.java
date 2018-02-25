package jxsource.tool.folder.search.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;

public class TreeFactory {
	Logger log = LogManager.getLogger(TreeFactory.class);
	private static final int Root = 2;
	private static final int OnPath = 1;
	private static final int Sibling = 0;
	private static final int Child = -1;
	private static final int Duplicate = -2;
	// working path
	private List<Node> path = new ArrayList<Node>();
	// created trees
	private Set<Node> trees = new HashSet<Node>();
	private List<Node> src;

	public static TreeFactory build() {
		return new TreeFactory();
	}
	public Set<Node> createTrees(List<Node> src) {
		path.clear();
		trees.clear();
		this.src = new ArrayList<Node>(src);
		Collections.sort(this.src);
		proc();
		return trees;
	}

	// return the first tree if src contains multiple trees
	public Node createTree(final List<Node> src) {
		Node[] trees = createTrees(src).toArray(new Node[0]);
		return (trees.length==0?null:trees[0]);
	}

	private int relation(Node file) {
		if(trees.size() == 0) {
			return Root;
		} 
		Node last = path.get(path.size()-1);
		if(file.getParentPath().equals(last.getPath())) {
			return Child;
		}
		if(file.getParentPath().equals(last.getParentPath())) {
			return Sibling;
		}
		if(file.getPath().equals(last.getPath())) {
			// validation
			return Duplicate;
		}
		return OnPath;
	}
	// return parent on the current path or null;
	private Node findParent(Node file) {

		String parent = file.getParentPath();
		for(int i=path.size()-1; i>-1; i--) {
			Node pathNode = path.get(path.size()-1);
			if(pathNode.getPath().equals(parent)) {
				return pathNode;
			} else {
				path.remove(path.size()-1);
			}
		}
		return null;
	}
	private void addRoot(Node root) {
		path.clear();
		path.add(root);
		trees.add(root);
	}
	private boolean isArray(Node file) {
		return file.isArray();
	}
	private void proc() {
		if(src.size() == 0) {
			return;
		}
		Node file = src.remove(0);
		log.debug("trees="+trees.size()+", file="+file.getPath()+", "+
				"parent="+(path.size()==0?"\\":path.get(path.size()-1).getPath()));
		switch(relation(file)) {
		case Child:
			Node _parent = path.get(path.size()-1);
			if(file.getParent() == null) {
				file.setParent(_parent);
			}
			_parent.addChild(file);
			if(isArray(file)) {
				path.add(file);
			}
			break;
		case Sibling:
			path.remove(path.size()-1);
			if(path.size() > 0) {
				path.get(path.size()-1).addChild(file);
				if(isArray(file)) {
					path.add(file);
				}
			} else {
				addRoot(file);
			}
			break;
		case Root:
			addRoot(file);
			break;
		case OnPath:
			Node parent = findParent(file);
			if(parent != null) {
				parent.addChild(file);
				path.add(file);
			} else {
				// new root
				// TODO: need more analysis
				addRoot(file);
			}
			break;
		default:
			// this should not happen; include Duplicate
			String err = "Duplicate file "+file.getPath()+
				" or code error when creating Tree";
			throw new RuntimeException(err);
		}
		
		proc();
	}
}
