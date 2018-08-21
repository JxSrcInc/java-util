package jxsource.util.folder.manager;

import java.io.File;
import java.util.Set;

import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.FileFilter;
import jxsource.util.folder.search.filter.filefilter.BackDir;
import jxsource.util.folder.search.util.Util;

public class Manager {
	
	protected BackDir tempDir;
	private Filter filter;
	private SearchEngine engine;
	private CollectionAction action = new CollectionAction();
	private boolean engineReady;
	
	Manager() {}

	public BackDir getTempDir() {
		return tempDir;
	}
	
	Manager setWorkingDir(String workingDir) {
		tempDir = BackDirHolder.get();
		tempDir.setWorkingDir(workingDir);
		return this;
	}

	public SearchEngine getEngine() {
		return engine;
	}

	Manager setEngine(SearchEngine engine) {
		this.engine = engine;
		return this;
	}
	
	Manager setFileFilter(FileFilter filter) {
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
			Set<Action> actions = engine.getActions();
			for(Action action: actions) {
				if(action instanceof CollectionAction) {
					this.action = (CollectionAction) action;
					break;
				}
			}
			if(action == null) {
				action = new CollectionAction();
				actions.add(action);
			}
			engineReady = true;
		}
	}
	public CollectionAction getAction() {
		return action;
	}

	public void run(String file) {
		run(new File(file));
	}
	public void run(File file) {
		if(!engineReady) {
			initEngine();
		}
		engine.setFilter(filter);
		engine.search(file);
		for(Node node: action.getNodes()) {
			System.out.println(node.getAbsolutePath());
		}
	}
}
