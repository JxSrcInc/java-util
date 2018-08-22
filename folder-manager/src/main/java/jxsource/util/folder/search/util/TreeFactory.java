package jxsource.util.folder.search.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.Node;

/*
 * createTrees(List<T> src) builds several trees from src parameter,
 * which sort src and then call proc() method.
 * 
 * Because src is sorted, all nodes related to one tree is grouped together
 * 
 * path changes dynamically when building a tree.
 *
 * If the first path segment of node changes, this node is a new root for a new tree.
 */
public class TreeFactory {
	Logger log = LogManager.getLogger(TreeFactory.class);
	// working path
	private List<Node> path = new ArrayList<Node>();
	// created trees
	private Set<Node> trees = new HashSet<Node>();
	private List<Node> src;

	public static TreeFactory build() {
		return new TreeFactory();
	}

	/**
	 * create trees from input list
	 * 
	 * @param src
	 * @return Set<Node> each node is a root of a tree.
	 */
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
		Iterator<Node> i = createTrees(src).iterator();
		Node first = null;
		if(i.hasNext()) {
			first = i.next();
		}
		return first;
	}

	/*
	 * find file's parent node from (working) path by matching file.getParent()
	 * String with
	 */
	private Node findParent(Node file) {

		String parentPath = file.getParentPath();
		while (path.size() > 0) {
			Node lastNodeOnPath = path.get(path.size() - 1);
			String pathOfLastNodeOnPath = lastNodeOnPath.getPath();
			if (pathOfLastNodeOnPath.equals(parentPath)) {
				// file's parent path equals selected parts of path
				return lastNodeOnPath;
			} else {
				path.remove(path.size() - 1);
			}
		}
		return null;
	}

	private void addRoot(Node root) {
		path.clear();
		path.add(root);
		trees.add(root);
	}

	private void proc() {
		if (src.size() == 0) {
			return;
		}
		Node file = src.remove(0);
		// find file's parent node from (working) path by comparing parent path String
		Node parentNode = findParent(file);
		if (parentNode == null) {
			// file is root or a new tree
			addRoot(file);
		} else {
			if (file.getParent() == null) {
				// file has no parent node when file is ZipFile
				file.setParent(parentNode);
			} else {
				// validation
				if (!file.getParent().equals(parentNode)) {
					throw new RuntimeException(
							"multi parent error: " + file.getParent().getPath() + "," + parentNode.getPath());
				}
			}
			parentNode.addChild(file);
			path.add(file);
		}
		log.debug("trees=" + trees.size() + ", path-length: " + path.size() + ", file=" + file.getPath() + ", "
				+ "parent=" + (path.size() == 0 ? "null" : path.get(path.size() - 1).getPath()));
		// process next item in src
		proc();
	}
}
