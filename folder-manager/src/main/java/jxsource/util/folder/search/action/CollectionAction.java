package jxsource.util.folder.search.action;

import java.util.ArrayList;
import java.util.List;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

public class CollectionAction<T extends Node> implements Action<T>{
	private List<JFile> files = new ArrayList<JFile>();
	private List<T> nodes = new ArrayList<T>();
	private String url = "Undefined";
	private boolean leafOnly = true;
	
	public boolean isLeafOnly() {
		return leafOnly;
	}
	public void setLeafOnly(boolean leafOnly) {
		this.leafOnly = leafOnly;
	}
	public void reset() {
		files.clear();
		nodes.clear();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void proc(T f) {
		if(leafOnly && f.isDir()) return;
		files.add((JFile)f);
		nodes.add(f);
	}
//	public List<JFile> getFiles() {
//		return files;
//	}
	public List<T> getNodes() {
		return nodes;
	}
	@Override
	public String toString() {
		String s = url +'\n';
		for(JFile f: files) {
			s += "\t"+f+'\n';
		}
		return s;
	}
}
