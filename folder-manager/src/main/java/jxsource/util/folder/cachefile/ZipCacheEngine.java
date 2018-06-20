package jxsource.util.folder.cachefile;

import java.util.Set;

import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.search.action.CacheFileAction;

/**
 * It supports both ZipFile and CacheFile<ZipFile>
 * Use setCache(true) to work with CacheFile<ZipFile>
 * @author JiangJxSrc
 *
 */
public class ZipCacheEngine extends ZipSearchEngine {
	public ZipCacheEngine() {
		super.addAction(new CacheFileAction());		
	}

	@Override
	public void setActions(Set<Action> actions) {
		actions.add(new CacheFileAction());
		super.setActions(actions);
	}
	

}
