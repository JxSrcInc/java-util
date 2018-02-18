package jxsource.util.folder.search.app;

import java.io.File;
import java.text.ParseException;

import jxsource.tool.folder.file.SysFile;
import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.action.CollectionAction;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.action.ProcessWatchAction;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;
import jxsource.tool.folder.search.filter.pathfilter.TimeFilter;
import jxsource.tool.folder.search.zip.ZipReportAssert;
import jxsource.tool.folder.search.zip.ZipReportPrinter;
import jxsource.tool.folder.search.zip.ZipSearchTemplate;

public class Snapshot {
	
	public static void main(String[] args) {
		File root = new File("C:\\");
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new ProcessWatchAction(new SysFile(root)).setWatchLevel(3));
			engin.search(root);
		
	}

}
