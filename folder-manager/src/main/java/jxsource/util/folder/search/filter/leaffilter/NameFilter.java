package jxsource.util.folder.search.filter.leaffilter;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

/**
 * Match file name but excluding extension
 */
public class NameFilter extends LeafFilter {
	
	NameFilter(){}
	@Override
	protected int delegateStatus(Node file) {
		String name = file.getName();
		int i = name.lastIndexOf('.');
		if(i > 0) {
			name = name.substring(0, i);
		}
		if(stringMatcher.match(name)) {
			return Filter.ACCEPT;
		}

		
//		for(String match: matchs) {
//			if(contains(name, match)) {
//				return Filter.ACCEPT;
//			}
//		}
		return Filter.REJECT;
	}

}
