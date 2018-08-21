package jxsource.util.folder.search.filter.filefilter;

import jxsource.util.folder.manager.Manager;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilter;

public abstract class FileFilter extends LeafFilter{

	@Override
	public int delegateStatus(Node file) {
		if(file instanceof JFile) {
			return delegateStatus((JFile)file);
		} else {
			return Filter.REJECT;
		}
	}
	protected abstract int delegateStatus(JFile file);
}
