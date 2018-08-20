package jxsource.util.folder.search.filter.filefilter;

import jxsource.util.folder.manager.Manager;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.AbstractFilter;
import jxsource.util.folder.search.filter.Filter;

public abstract class FileFilter extends AbstractFilter{

	private Manager manager;

	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	@Override
	public int _getStatus(Node file) {
		if(file instanceof JFile) {
			return delegateStatus((JFile)file);
		} else {
			return Filter.REJECT;
		}
	}
	protected abstract int delegateStatus(JFile file);
}
