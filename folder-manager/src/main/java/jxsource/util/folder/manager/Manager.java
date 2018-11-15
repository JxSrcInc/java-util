package jxsource.util.folder.manager;

import java.io.File;
import java.util.Set;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.filefilter.BackDir;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.FileFilter;

public class Manager<T extends Node> {
	
	protected BackDir backDir;
	private Filter filter;
	private SearchEngine<T> engine;
	private CollectionAction<T> action = new CollectionAction<T> ();
	private boolean engineReady;
	
	Manager() {}

	public BackDir getBackDir() {
		return backDir;
	}
	
	Manager<T> changeBackDir(String workingDir) {
		backDir = BackDirHolder.get();
		backDir.changeBackDir(workingDir);
		return this;
	}

	public SearchEngine<T>  getEngine() {
		return engine;
	}

	Manager<T> setEngine(SearchEngine<T> engine) {
		this.engine = engine;
		initEngine();
		return this;
	}
	
	Manager<T> setFileFilter(FileFilter filter) {
		this.filter = filter;
		initEngine();
		return this;
	}

	private void initEngine() {
		if(engine != null) {
			Filter filter = engine.getFilter();
			if(filter == null) {
				engine.setFilter(this.filter);
			} else {
				filter.setNext(this.filter);
			}
			Set<Action<T>> actions = engine.getActions();
			for(Action<T>  action: actions) {
				if(action instanceof CollectionAction) {
					this.action = (CollectionAction<T>) action;
					break;
				}
			}
			if(action == null) {
				action = new CollectionAction<T>();
				actions.add(action);
			}
			engineReady = true;
		}
	}
	public CollectionAction<T> getAction() {
		return action;
	}

	private void check() {
		assert filter != null: "Manager.filter is null.";
		assert engine != null: "Manager.engine is null.";
	}
	/*
	 * public entry point
	 */
	public void run(String file) {
		run(new File(file));
	}
	/*
	 * public entry point
	 */
	public void run(File file) {
		if(!engineReady) {
			initEngine();
		}
		check();
		engine.search(file);
		for(Node node: action.getNodes()) {
			System.out.println(node.getAbsolutePath());
		}
	}
}
