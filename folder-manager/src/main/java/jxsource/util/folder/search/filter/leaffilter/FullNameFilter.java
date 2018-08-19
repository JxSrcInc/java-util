package jxsource.util.folder.search.filter.leaffilter;

import java.util.HashSet;
import java.util.Set;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

/**
 * String match must be full file name including extension
 */
public class FullNameFilter extends LeafFilter {
	FullNameFilter(){}
	@Override
	protected int _getStatus(Node file) {
		String name = file.getName();
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
