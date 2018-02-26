package jxsource.tool.folder.search.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.zip.ZipInputStream;

import jxsource.tool.folder.node.AbstractNode;
import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.ZipSearchEngine;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.action.CollectionAction;
import jxsource.tool.folder.search.action.ZipExtractAction;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.FilterFactory;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;
import jxsource.tool.folder.search.filter.pathfilter.ZipFilter;
/**
 * Select archive files using SysSearchEngine
 * Each selected archive file will be processed by ZipExtractAction to further search file content
 * Default sysFilter is ZipFilter
 * But can use any filter because ZipSearchAction will automatically filter out directory and non-zip file
 */
public class ZipSearchTemplate {
	
	private File rootDir;
	private SysSearchEngine se;
	
	private ZipSearchTemplate(File rootDir, Filter sysFilter, Filter zipFilter, ZipReportAction zipReport, boolean cache, boolean buildTree) {
		se = new SysSearchEngine();
		se.setFilter(sysFilter==null?new ZipFilter():sysFilter); // select archive file only
		
		ZipExtractAction zipExtractAction = new ZipExtractAction()
				.buildTree(buildTree)
				.setCache(cache)
				.setReport(zipReport);
		if(zipFilter != null) {
			zipExtractAction.setFilter(zipFilter);
		}
		se.addAction(zipExtractAction);
		this.rootDir = rootDir;
	}
	public void search() {
		se.search(rootDir);
	}
	
	public static ZipSearchTemplateBuilder getBuilder() {
		return new ZipSearchTemplateBuilder();
	}
	public static class ZipSearchTemplateBuilder {
		File rootDir = new File(System.getProperty("user.dir"));
		Filter zipFilter;
		Filter sysFilter;
		ZipReportAction zipReport;
		boolean buildTree;
		boolean cache;
		public ZipSearchTemplateBuilder buildTree(boolean buildTree) {
			this.buildTree = buildTree;
			return this;
		}		
		public ZipSearchTemplateBuilder setRootDir(File rootDir) {
			this.rootDir = rootDir;
			return this;
		}
		public ZipSearchTemplateBuilder setRootDir(String rootDir) {
			return setRootDir(new File(rootDir));
		}
		public ZipSearchTemplateBuilder setZipFilter(Filter zipFilter) {
			this.zipFilter = zipFilter;
			return this;
		}
		public ZipSearchTemplateBuilder setZipReport(ZipReportAction zipReport) {
			this.zipReport = zipReport;
			return this;
		}
		public ZipSearchTemplateBuilder setSysFilter(Filter sysFilter) {
			this.sysFilter = sysFilter;
			return this;
		}
		public ZipSearchTemplateBuilder setCache(boolean cache) {
			this.cache = cache;
			return this;
		}
		public ZipSearchTemplate build() {
			return new ZipSearchTemplate(rootDir, sysFilter, zipFilter, 
					zipReport, cache, buildTree);
		}
	}
	
	public static void main(String...args) {
		ZipSearchTemplate t = ZipSearchTemplate.getBuilder()
				.setRootDir("C:\\Users\\JiangJxSrc\\.m2\\repository\\org\\springframework")
				.setZipFilter(FilterFactory.create(FilterFactory.Name, "RestController"))
				.setZipReport(new ZipReportPrinter())
				.build();
		t.search();
	}
}
