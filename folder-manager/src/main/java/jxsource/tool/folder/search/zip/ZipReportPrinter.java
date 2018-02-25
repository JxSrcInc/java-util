package jxsource.tool.folder.search.zip;

import java.util.List;

import jxsource.tool.folder.node.AbstractNode;
import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;

public class ZipReportPrinter extends ZipReportAction {

	@Override
	public void report(String url, List<Node> extractFiles) {
		System.out.println(url + " ------------------");
		for(Node f: extractFiles)
		System.out.println("ZipReportPrinter: "+f);
	}

}
