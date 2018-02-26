package jxsource.tool.folder.search.action;

import java.util.ArrayList;
import java.util.List;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;

public class CollectionAction implements Action{
	private List<JFile> files = new ArrayList<JFile>();
	private List<Node> nodes = new ArrayList<Node>();
	private String url = "Undefined";
	
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
	public void proc(Node f) {
		// TODO: Node or JFile?
		files.add((JFile)f);
		nodes.add(f);
	}
//	public List<JFile> getFiles() {
//		return files;
//	}
	public List<Node> getNodes() {
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
