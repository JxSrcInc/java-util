package jxsource.tool.folder.search.filter.pathfilter;

import java.util.HashSet;
import java.util.Set;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.search.filter.Filter;

/**
 * String match must be full file name including extension
 */
public class FullNameFilter extends AbstractFilter {
	protected Set<String> matchs = new HashSet<String>();
	
	public FullNameFilter add(String[] matchs) {
		for(String match: matchs) {
			this.matchs.add(match.trim());
		}
		return this;
	}
	public FullNameFilter add(String multiMatch) {
		return add(multiMatch.split(","));
	}
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
