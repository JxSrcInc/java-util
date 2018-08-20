package jxsource.util.folder.search.action;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.util.Util;
import jxsource.util.folder.search.zip.ZipReportAction;

/**
 * Action apply to zip file found in Sys search.
 * It uses ZipSearchEngine to search the zip file passed in proc method, 
 * use Filter to filter the search conducted by ZipSearchEngine, and then
 * 	1. Not synchronized - use ZipReportAction (callback) or 
 *	2. Synchronized - use getResult() 
 * to process filtered nodes,
 * 
 * ZipSearchEngine's filter is passed in as filter attribute of this action.
 * 
 * ZipSearchEngine's action is CollectionAction with LearOnly attribute is false,
 * which contains all filtered nodes, including both directories and files
 * 
 * Note: ZipReportAction is not jxsource.tool.folder.search.action.Action
 * 
 * Note: getResult() returns all filtered nodes. So caller must 
 * 	1. call proc(Node) method of this action to process archive file
 * 	2. call getResult() method of this action to get filtered nodes for further processing.
 * 
 */
public class ZipExtractAction implements Action {
	Logger log = LogManager.getLogger(ZipExtractAction.class);
	private Filter filter;
	private ZipReportAction reportAction;
	private CollectionAction ca = new CollectionAction();
//	private boolean buildTree;
	private boolean cache;

	/**
	 * called by SysSearchEngine
	 * 
	 * If input parameter file is an archive file 
	 * then applies ZipSearchEngine to process the archive file.
	 * 
	 */
	public void proc(Node file) {
		if (file.isDir())
			return;
		// skip all non-archive files.
		if (Util.isArchive((JFile) file)) {
			log.debug("search " + file.getPath() + "," + file.getAbsolutePath());
			String url = file.getPath();
			ca.reset();
			try {
				ZipSearchEngine engine = new ZipSearchEngine();
				engine.setFilter(filter);
				// just collect all entries
				engine.addAction(ca);
				engine.search(new File(file.getAbsolutePath()));
				if (ca.getNodes().size() > 0) {
					log.debug("searched: " + file.getPath() + ", find: " + ca.getNodes().size());
					if (reportAction != null) {
						reportAction.report(url, ca.getNodes());
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("Error when extracting " + url, e);
			}
		}
	}

	public List<Node> getResult() {
		return ca.getNodes();
	}

	public Filter getFilter() {
		return filter;
	}

	public ZipExtractAction setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}

	public ZipReportAction getReport() {
		return reportAction;
	}

	public ZipExtractAction setReport(ZipReportAction report) {
		this.reportAction = report;
		return this;
	}

	public boolean isCache() {
		return cache;
	}

	public ZipExtractAction setCache(boolean cache) {
		this.cache = cache;
		return this;
	}

}
