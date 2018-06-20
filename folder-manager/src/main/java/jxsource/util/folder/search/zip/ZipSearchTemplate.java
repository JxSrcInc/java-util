package jxsource.util.folder.search.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.zip.ZipInputStream;

import jxsource.util.folder.node.AbstractNode;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.action.ZipExtractAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.FilterFactory;
import jxsource.util.folder.search.filter.pathfilter.ExtFilter;
import jxsource.util.folder.search.filter.pathfilter.ZipFilter;
/**
 * Select archive files using SysSearchEngine
 * Each selected archive file will be processed by ZipExtractAction to further search file content
 * Default sysFilter is ZipFilter
 * But can use any filter because ZipSearchAction will automatically filter out directory and non-zip file
 */
public class ZipSearchTemplate {
	
	private File rootDir;
	private SysSearchEngine se;
	
	private ZipSearchTemplate(File rootDir, Filter sysFilter, Filter zipFilter, ZipReportAction zipReport, boolean cache) {
		se = new SysSearchEngine();
		se.setFilter(sysFilter==null?new ZipFilter():sysFilter); // select archive file only
		
		ZipExtractAction zipExtractAction = new ZipExtractAction()
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
		boolean cache;
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
					zipReport, cache);
		}
	}
	
	public static void main(String...args) {
		ZipSearchTemplate t = ZipSearchTemplate.getBuilder()
				.setRootDir("test-data.jar")
//				.setZipFilter(FilterFactory.create(FilterFactory.Name, "RestController"))
				.setZipReport(new ZipReportPrinter())
				.build();
		t.search();
	}
}
