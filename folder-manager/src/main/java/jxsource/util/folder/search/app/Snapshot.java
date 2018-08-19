package jxsource.util.folder.search.app;

import java.io.File;
import java.text.ParseException;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.action.FilePrintAction;
import jxsource.util.folder.search.action.ProcessWatchAction;
import jxsource.util.folder.search.filter.leaffilter.ExtFilter;
import jxsource.util.folder.search.filter.leaffilter.TimeFilter;
import jxsource.util.folder.search.zip.ZipReportAssert;
import jxsource.util.folder.search.zip.ZipReportPrinter;
import jxsource.util.folder.search.zip.ZipSearchTemplate;

public class Snapshot {
	
	public static void main(String[] args) {
		File root = new File("C:\\");
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new ProcessWatchAction(new SysFile(root)).setWatchLevel(3));
			engin.search(root);
		
	}

}
