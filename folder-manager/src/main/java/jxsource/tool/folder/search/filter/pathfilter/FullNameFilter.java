package jxsource.tool.folder.search.filter.pathfilter;

import java.util.HashSet;
import java.util.Set;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.search.filter.Filter;

/**
 * String match must be full file name including extension
 */
public class FullNameFilter extends AbstractFilter {
	@Override
	public int getStatus(JFile file) {
		if(file.isArray()) {
			return Filter.PASS;
		}
		String name = file.getName();
		for(String match: matchs) {
			if(_accept(name, match)) {
				return Filter.ACCEPT;
			}
		}
		return Filter.REJECT;
	}

}
