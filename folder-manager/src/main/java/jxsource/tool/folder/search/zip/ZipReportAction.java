package jxsource.tool.folder.search.zip;

import java.util.List;

import jxsource.tool.folder.file.AbstractJFile;

public abstract class ZipReportAction {
	public abstract void report(String url, List<AbstractJFile> extractFiles);
}
