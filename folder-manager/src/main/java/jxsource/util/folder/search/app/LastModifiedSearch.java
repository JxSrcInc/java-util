package jxsource.util.folder.search.app;

import java.io.File;
import java.text.ParseException;

import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.action.CollectionAction;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;
import jxsource.tool.folder.search.filter.pathfilter.TimeFilter;
import jxsource.tool.folder.search.zip.ZipReportAssert;
import jxsource.tool.folder.search.zip.ZipReportPrinter;
import jxsource.tool.folder.search.zip.ZipSearchTemplate;

public class LastModifiedSearch {
	
	public static void main(String[] args) {
		String root = "C:\\Users\\JiangJxSrc\\AppData\\Roaming\\npm\\node_modules";
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new FilePrintAction());
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		try {
			engin.setFilter(new TimeFilter("2018-2-4 19:00:00", "2030-01-01 00:00:00"));
			engin.search(new File(root));
			System.out.println("find "+ca.getFiles().size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
