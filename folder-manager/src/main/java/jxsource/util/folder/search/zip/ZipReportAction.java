package jxsource.util.folder.search.zip;

import java.util.List;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

public abstract class ZipReportAction {
	public abstract void report(String url, List<Node> extractFiles);
}
