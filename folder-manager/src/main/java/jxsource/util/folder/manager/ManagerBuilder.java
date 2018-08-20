package jxsource.util.folder.manager;

import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.filefilter.TempDir;

public abstract class ManagerBuilder {
	private String workingDir;
	private Filter filter;
	
	public ManagerBuilder setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
		return this;
	}
	public ManagerBuilder setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}
	public Manager build() {
		Manager manager = new Manager();
		if(workingDir != null) {
			manager.tempDir = TempDir.builder().setSubWorkingDir(workingDir).build();
		} else {
			manager.tempDir = TempDir.builder().build();				
		}
		manager.setFilter(buildFilter());
		return manager;
	}
	protected abstract Filter buildFilter();

}
