package jxsource.tool.folder.cachefile;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.node.AbstractNode;
import jxsource.tool.folder.node.CacheFile;
import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.ZipFile;
import jxsource.tool.folder.search.ZipSearchEngine;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.action.CacheFileAction;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;

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
