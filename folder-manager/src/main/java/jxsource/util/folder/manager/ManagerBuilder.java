package jxsource.util.folder.manager;

import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.filter.filefilter.FileFilter;

public class ManagerBuilder {
	private String workingDir;
	private FileFilter filter;
	private SearchEngine engine;
	
	public ManagerBuilder setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
		return this;
	}
	public ManagerBuilder setFileFilter(FileFilter filter) {
		this.filter = filter;
		return this;
	}
	public ManagerBuilder setEngine(SearchEngine engine) {
		this.engine = engine;
		return this;
	}

	public Manager build() {
		Manager manager = new Manager();
		manager.setWorkingDir(workingDir);
		manager.setFileFilter(filter);
		manager.setEngine(engine);
		return manager;
	}

}
