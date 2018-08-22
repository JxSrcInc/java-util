package jxsource.util.folder.manager;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.filter.filefilter.FileFilter;

public class ManagerBuilder<T extends Node> {
	private String workingDir;
	private FileFilter filter;
	private SearchEngine<T> engine;
	
	public ManagerBuilder<T> setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
		return this;
	}
	public ManagerBuilder<T> setFileFilter(FileFilter filter) {
		this.filter = filter;
		return this;
	}
	public ManagerBuilder<T> setEngine(SearchEngine<T> engine) {
		this.engine = engine;
		return this;
	}

	public Manager<T> build() {
		Manager<T> manager = new Manager<T>();
		manager.setWorkingDir(workingDir);
		manager.setFileFilter(filter);
		manager.setEngine(engine);
		return manager;
	}

}
