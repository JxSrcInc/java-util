package jxsource.util.folder.search.filter.leaffilter;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

/**
 * String match must be full file name including extension
 */
public class FullNameFilter extends LeafFilter {
	FullNameFilter(){}
	@Override
	protected int delegateStatus(Node file) {
		String name = file.getName();
		if(stringMatcher.match(name)) {
			return Filter.ACCEPT;
		}
		return Filter.REJECT;
	}

}
