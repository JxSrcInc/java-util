package jxsource.tool.folder.search.zip;

import java.util.List;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;

public abstract class ZipReportAction {
	public abstract void report(String url, List<Node> extractFiles);
}
