package jxsource.util.folder.search.filter.pathfilter;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

/**
 * Match file name but excluding extension
 */
public class NameFilter extends LeafFilter {
	
	@Override
	protected int _getStatus(Node file) {
		String name = file.getName();
		int i = name.lastIndexOf('.');
		if(i > 0) {
			name = name.substring(0, i);
		}
		for(String match: matchs) {
			if(_accept(name, match)) {
				return Filter.ACCEPT;
			}
		}
		return Filter.REJECT;
	}

}
