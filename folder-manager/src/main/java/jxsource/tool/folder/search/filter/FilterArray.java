package jxsource.tool.folder.search.filter;

import java.util.HashSet;
import java.util.Set;

import jxsource.tool.folder.node.JFile;

public class FilterArray extends Filter{

	private Set<Filter> filters = new HashSet<Filter>();
	
	public void addFilters(Set<Filter> filters) {
		this.filters.addAll(filters);
	}
	public void addFilter(Filter filter) {
		filters.add(filter);
	}
	@Override
	public int getStatus(JFile file) {
		boolean pass = false;
		for(Filter filter: filters) {
			int status = filter.accept(file);
			switch(status) {
				case REJECT:
					return REJECT;
				case PASS:
					pass = true;
			}
		}
		if(pass) {
			return PASS;
		} else {
			return ACCEPT;
		}
	}

}
