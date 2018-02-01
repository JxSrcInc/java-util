package jxsource.tool.folder.search.zip;

import java.util.List;

import jxsource.tool.folder.file.AbstractJFile;
import jxsource.tool.folder.file.JFile;

public class ZipReportPrinter extends ZipReportAction {

	@Override
	public void report(String url, List<JFile> extractFiles) {
		System.out.println(url + " ------------------");
		for(JFile f: extractFiles)
		System.out.println("ZipReportPrinter: "+f);
	}

}
