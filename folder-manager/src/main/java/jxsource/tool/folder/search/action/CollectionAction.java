package jxsource.tool.folder.search.action;

import java.util.ArrayList;
import java.util.List;

import jxsource.tool.folder.file.AbstractJFile;

public class CollectionAction implements Action{
	private List<AbstractJFile> files = new ArrayList<AbstractJFile>();
	private String url = "Undefined";
	
	public void reset() {
		files.clear();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void proc(AbstractJFile f) {
		files.add(f);
	}
	public List<AbstractJFile> getFiles() {
		return files;
	}
	@Override
	public String toString() {
		String s = url +'\n';
		for(AbstractJFile f: files) {
			s += "\t"+f+'\n';
		}
		return s;
	}
}
