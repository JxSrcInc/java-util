package jxsource.tool.folder.search.filter.pathfilter;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.search.filter.Filter;

public class ExtFilter extends AbstractFilter{
	String[] exts;
	public ExtFilter(String[] exts) {
		for(int i=0; i<exts.length; i++) {
			exts[i] = exts[i].trim();
		}
		this.exts = exts;
	}
	public ExtFilter(String exts) {
		exts = exts.replaceAll(" ", "");
		this.exts = exts.split(",");
	}
	
	public int getStatus(JFile f) {
		if(f.isArray()) {
			return Filter.PASS;
		}
		String name = f.getName();
		int index = name.lastIndexOf('.');
		if(index > 0)  {
			String ext = name.substring(index+1);
			for(String match: exts) {
				if(_accept(ext,match)) {
					return Filter.ACCEPT;
				}
			}
		}
		return Filter.REJECT;
	}

}
