package jxsource.util.folder.search.filter.pathfilter;

import java.util.HashSet;
import java.util.Set;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

/**
 * String match must be full file name including extension
 */
public class FullNameFilter extends LeafFilter {
	@Override
	protected int _getStatus(Node file) {
		String name = file.getName();
		for(String match: matchs) {
			if(_accept(name, match)) {
				return Filter.ACCEPT;
			}
		}
		return Filter.REJECT;
	}

}
