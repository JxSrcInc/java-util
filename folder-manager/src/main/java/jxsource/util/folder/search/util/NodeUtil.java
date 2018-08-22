package jxsource.util.folder.search.util;

import java.util.ArrayList;
import java.util.List;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.search.match.NodeMatch;

public class NodeUtil {
	
	@SuppressWarnings("unchecked")
	public static <T extends Node>List<T> convertTreeToList(T root) {
		List<T>	list = new ArrayList<T>();
		list.add(root);
		if(root.getChildren() != null) {
			for(Node child: root.getChildren()) {
				list.addAll(convertTreeToList((T)child));
			}
			
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Node> T getChild(T node, String childName) {
		for (Node child : node.getChildren()) {
			if (child.getName().contentEquals(childName)) {
				return (T)child;
			}
		}
		return null;
	}

	/**
	 * Find a node in a tree that starts from root 
	 * and end at the end of path.
	 * <P>
	 * It is a recursive method
	 * <p>
	 * Example: a tree starts at root and has three levels. 
	 * each node has two branches:
	 * 		root
	 * 		1-l, 1-r
	 * 		2-l-l, 2-l-r, 2-r-l, 2-r-r
	 * then path 1-l and 1-r/2-r-l will get node.
	 * but path 1-l/2-r-r and 2-l-l will get null
	 * 
	 * 
	 * @param root - start tree
	 * @param path - start to match from next level of root
	 * @return Node if tree contains path, or null if not
	 */
	public static Node getPath(Node root, String path) {
		int i = path.indexOf('/');
		String childName = path;
		String subPath = null;
		if (i != -1) {
			childName = path.substring(0, i);
			subPath = path.substring(i + 1);
		}
		Node child = getChild(root, childName);
		if (child != null) {
			if (subPath != null) {
				return getPath(child, subPath);
			} else {
				return child;
			}
		}
		return null;
	}

	/**
	 * path is an array of String and each element in array is a node.
	 * if nodeToMatch is an element in path, method will return its index
	 * in the array. if not, return -1;
	 * @param path
	 * @param nodeToMatch
	 * @return
	 */
	public static int isOnPath(String[] path, NodeMatch match) {
		for(int i=0; i<path.length; i++) {
			if(match.match(path[i])) {
				return i;
			}
		}
		return -1;
	}
	public static int isOnPath(List<String> path, NodeMatch match) {
		return isOnPath(path.toArray(new String[path.size()]), match);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Node> void procTree(T node, Action<T>...action) {
		if(node.isDir()) {
			for(Node child: node.getChildren()) {
				for(int i=0; i<action.length; i++) {
					procTree((T)child, action[i]);
				}
			}
		}
		// Do all children first
		for(int i=0; i<action.length; i++) {
			action[i].proc(node);
		}
		
	}
}
