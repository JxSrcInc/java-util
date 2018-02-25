package jxsource.tool.folder.cachefile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import jxsource.tool.folder.node.CacheFile;
import jxsource.tool.folder.node.SysFile;
import jxsource.tool.folder.node.ZipFile;
import jxsource.tool.folder.search.SearchEngine;
import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.action.CacheFileAction;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;

/**
 * Search a folder
 * 
 * @author JiangJxSrc
 *
 */
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
