package jxsource.tool.folder.search.action;

import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;
import jxsource.tool.folder.node.NodeManagerHolder;
import jxsource.tool.folder.search.ZipSearchEngine;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.util.Util;
import jxsource.tool.folder.search.zip.ZipReportAction;
import jxsource.tool.folder.search.zip.ZipReportPrinter;

/**
 * Action apply to zip file found in Sys search
 * It uses ZipSearchEngine to search the zip file passed in proc method
 * Use Filter to filter the search conducted by ZipSearchEngine
 * Use ZipReportAction (callback) to process search result for each zip file
 * Or use getResult() method to get search result -- Note: caller must follow proc -> getResult -> proc ... order
 */
public class ZipExtractAction implements Action {
	Logger log = LogManager.getLogger(ZipExtractAction.class);
	private Filter filter;
	private ZipReportAction reportAction;
	private CollectionAction ca = new CollectionAction();
	private boolean buildTree;
	private boolean cache;

	public void proc(Node f) {
		// skip all non-archive files.
		// TODO: JFile
		if (Util.isArchive((JFile)f)) {
			log.debug("search "+f.getPath());
			String url = f.getPath();
			ca.reset();
			try {
				ZipInputStream in = new ZipInputStream(new FileInputStream(f.getPath()));
				ZipSearchEngine engin = new ZipSearchEngine();
				engin.setFilter(filter);
				// just collect all entries
				engin.addAction(ca);					
				engin.search(in);
				if(ca.getNodes().size() > 0) {
				log.debug("searched: "+f.getPath()+", find: "+ca.getNodes().size());
//				new ZipReportPrinter().report(url, buildTree?engin.getTrees():ca.getNodes());
//				NodeManagerHolder.get().reset();
//				}
				if(reportAction != null) {
					reportAction.report(url, buildTree?engin.getTrees():ca.getNodes());
				}
				}
			} catch (Exception e) {
				throw new RuntimeException("Error when extracting "+url, e);
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

	public boolean isBuildTree() {
		return buildTree;
	}

	public ZipExtractAction buildTree(boolean buildTree) {
		this.buildTree = buildTree;
		return this;
	}

}
