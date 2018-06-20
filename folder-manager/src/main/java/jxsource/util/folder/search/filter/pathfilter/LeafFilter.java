package jxsource.util.folder.search.filter.pathfilter;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

/*
 * accept all directory
 * but next filter only apply to sub class
 */
public abstract class LeafFilter extends AbstractFilter{

	protected abstract int _getStatus(Node file);
	@Override
	public int getStatus(Node file) {
		if(file.isDir()) {
			return Filter.PASS;
		} else {
			return _getStatus(file);
		}
	}

}
