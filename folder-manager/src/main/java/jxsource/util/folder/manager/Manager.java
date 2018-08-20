package jxsource.util.folder.manager;

import java.io.File;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.filefilter.TempDir;
import jxsource.util.folder.search.filter.filefilter.TempDir.TempDirBuilder;
import jxsource.util.folder.search.util.Util;

public class Manager {
	
	protected TempDir tempDir;
	private Filter filter;
	private SearchEngine engine;
	private CollectionAction action = new CollectionAction();
	
	Manager() {}

	public TempDir getTempDir() {
		return tempDir;
	}
	
	Manager setTempDir(TempDir tempDir) {
		this.tempDir = tempDir;
		return this;
	}

	public SearchEngine getEngine() {
		if(engine == null) {
			throw new NullPointerException("Call run(...) method first to avoid NullPointerException");
		}
		return engine;
	}

	Manager setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}

	public CollectionAction getAction() {
		return action;
	}

	public void run(String file) {
		run(new File(file));
	}
	public void run(File file) {
		if(Util.isArchive(file)) {
			engine = new ZipSearchEngine();
		} else {
			engine = new SysSearchEngine();
		}
		engine.setFilter(filter);
		engine.addAction(action);
		engine.search(file);
		for(Node node: action.getNodes()) {
			System.out.println(node.getAbsolutePath());
		}
	}
}
