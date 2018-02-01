package jxsource.tool.folder.search.filter.pathfilter;

import java.util.HashSet;
import java.util.Set;

import jxsource.tool.folder.file.AbstractJFile;
import jxsource.tool.folder.search.filter.Filter;

/**
 * Match file name but excluding extension
 */
public class NameFilter extends FullNameFilter {
	
	@Override
	public int getStatus(AbstractJFile file) {
		if(file.isDirectory()) {
			return Filter.PASS;
		}
		String name = file.getName();
		int i = name.indexOf('.');
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
