package jxsource.util.folder.search.zip;

import java.util.List;

import jxsource.util.folder.node.AbstractNode;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

public class ZipReportPrinter extends ZipReportAction {

	@Override
	public void report(String url, List<Node> extractFiles) {
		System.out.println(url + " ------------------");
		for(Node f: extractFiles)
		System.out.println("ZipReportPrinter: "+f);
		System.out.println();
	}

}
