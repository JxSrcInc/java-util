package jxsource.util.folder.cachefile;

import java.util.Set;

import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.search.action.CacheFileAction;

public class SysCacheEngine extends SysSearchEngine {

	public SysCacheEngine() {
		super.addAction(new CacheFileAction());		
	}

	@Override
	public void setActions(Set<Action> actions) {
		actions.add(new CacheFileAction());
		super.setActions(actions);
	}
	
}
