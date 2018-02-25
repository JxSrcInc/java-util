package jxsource.tool.folder.cachefile;

import java.util.Set;

import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.action.CacheFileAction;

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
