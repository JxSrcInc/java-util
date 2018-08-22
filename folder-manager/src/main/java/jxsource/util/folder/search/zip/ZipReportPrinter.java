package jxsource.util.folder.search.zip;

import java.util.List;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.ZipFile;

public class ZipReportPrinter extends ZipReportAction {

	@Override
	public void report(String url, List<ZipFile> extractFiles) {
		System.out.println(url + " ------------------");
		for(Node f: extractFiles)
		System.out.println("ZipReportPrinter: "+f);
		System.out.println();
	}

}
