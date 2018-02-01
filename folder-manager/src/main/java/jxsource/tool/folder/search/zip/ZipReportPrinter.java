package jxsource.tool.folder.search.zip;

import java.util.List;

import jxsource.tool.folder.file.AbstractJFile;

public class ZipReportPrinter extends ZipReportAction {

	@Override
	public void report(String url, List<AbstractJFile> extractFiles) {
		System.out.println(url + " ------------------");
		for(AbstractJFile f: extractFiles)
		System.out.println("ZipReportPrinter: "+f);
	}

}
