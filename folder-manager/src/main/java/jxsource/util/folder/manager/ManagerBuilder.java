package jxsource.util.folder.manager;

import java.util.HashMap;
import java.util.Map;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.filter.filefilter.FileFilter;

public class ManagerBuilder<T extends Node> {
	protected String workingDir;
	protected FileFilter filter;
	protected SearchEngine<T> engine;
	protected Map<String,String> properties = new HashMap<>();
	
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

	public ManagerBuilder<T> setProperties(Map<String,String> properties) {
		this.properties = properties;
		return this;
	}

	public <M extends Manager<T>> M build(Class<M> classManager) {
		try {
		M manager = classManager.newInstance();
		manager.setWorkingDir(workingDir);
		manager.setFileFilter(filter);
		manager.setEngine(engine);
		return manager;
		} catch(Exception e) {
			throw new RuntimeException("Fail to create Manager: "+classManager, e);
		}
	}

}
