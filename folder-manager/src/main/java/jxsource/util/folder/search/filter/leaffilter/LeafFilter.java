package jxsource.util.folder.search.filter.leaffilter;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.util.StringMatcher;

/*
 * accept all directory
 * but next filter only apply to sub class
 */
public abstract class LeafFilter extends Filter {

	protected StringMatcher stringMatcher;
	private boolean exclude;
	
	public boolean isExclude() {
		return exclude;
	}
	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}
	public void setStringMatcher(StringMatcher stringMatcher) {
		this.stringMatcher = stringMatcher;
	}
	protected abstract int delegateStatus(Node file);
	@Override
	public int delegateAccept(Node file) {
		if(file.isDir()) {
			return Filter.PASS;
		} else {
			int status = delegateStatus(file);
			if(exclude) {
				if(status == Filter.ACCEPT) {
					return Filter.REJECT;
				} else {
					return Filter.ACCEPT;
				}
			} else {
				return status;
			}
		}
	}

}
