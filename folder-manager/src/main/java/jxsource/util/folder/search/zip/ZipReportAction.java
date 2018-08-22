package jxsource.util.folder.search.zip;

import java.util.List;

import jxsource.util.folder.node.ZipFile;

public abstract class ZipReportAction {
	public abstract void report(String url, List<ZipFile> extractFiles);
}
