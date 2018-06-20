package jxsource.util.folder.search.filter.pathfilter;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

public class ExtFilter extends LeafFilter{
	
	protected int _getStatus(Node f) {
		String name = f.getName();
		int index = name.lastIndexOf('.');
		if(index > 0)  {
			String ext = name.substring(index+1);
			for(String match: matchs) {
				if(_accept(ext,match)) {
					return Filter.ACCEPT;
				}
			}
		}
		return Filter.REJECT;
	}

}
