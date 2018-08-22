package jxsource.util.folder.search.filter.leaffilter;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

public class ExtFilter extends LeafFilter{
	
	ExtFilter() {}
	protected int delegateStatus(Node f) {
		String name = f.getName();
		int index = name.lastIndexOf('.');
		if(index > 0)  {
			String ext = name.substring(index+1);
			if(stringMatcher.match(ext)) {
				return Filter.ACCEPT;
			}
		}
		return Filter.REJECT;
	}

}
