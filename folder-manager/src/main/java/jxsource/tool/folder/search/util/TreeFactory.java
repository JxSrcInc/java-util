package jxsource.tool.folder.search.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.file.JFile;
import jxsource.tool.folder.file.XFile;

public class TreeFactory {
	Logger log = LogManager.getLogger(TreeFactory.class);
	private static final int Root = 2;
	private static final int OnPath = 1;
	private static final int Sibling = 0;
	private static final int Child = -1;
	private static final int Duplicate = -2;
	// working path
	private List<JFile> path = new ArrayList<JFile>();
	// created trees
	private Set<JFile> trees = new HashSet<JFile>();
	private List<JFile> src;

	public static TreeFactory build() {
		return new TreeFactory();
	}
	public Set<JFile> createTrees(List<JFile> src) {
		path.clear();
		trees.clear();
		this.src = new ArrayList<JFile>(src);
		Collections.sort(this.src);
		proc();
		return trees;
	}

	// return the first tree if src contains multiple trees
	public JFile createTree(final List<JFile> src) {
		JFile[] trees = createTrees(src).toArray(new JFile[0]);
		return (trees.length==0?null:trees[0]);
	}

	private int relation(JFile file) {
		if(trees.size() == 0) {
			return Root;
		} 
		JFile last = path.get(path.size()-1);
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
	private JFile findParent(JFile file) {

		String parent = file.getParentPath();
		for(int i=path.size()-1; i>-1; i--) {
			JFile pathNode = path.get(path.size()-1);
			if(pathNode.getPath().equals(parent)) {
				return pathNode;
			} else {
				path.remove(path.size()-1);
			}
		}
		return null;
	}
	private void addRoot(JFile root) {
		path.clear();
		path.add(root);
		trees.add(root);
	}
	private void proc() {
		if(src.size() == 0) {
			return;
		}
		JFile file = src.remove(0);
		log.debug("trees="+trees.size()+", file="+file.getPath()+", "+
				"parent="+(path.size()==0?"\\":path.get(path.size()-1).getPath()));
		switch(relation(file)) {
		case Child:
			JFile _parent = path.get(path.size()-1);
			if(file.getParent() == null) {
				file.setParent(_parent);
			}
			_parent.addChild(file);
			if(file.isDirectory()) {
				path.add(file);
			}
			break;
		case Sibling:
			path.remove(path.size()-1);
			if(path.size() > 0) {
				path.get(path.size()-1).addChild(file);
				if(file.isDirectory()) {
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
			JFile parent = findParent(file);
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
