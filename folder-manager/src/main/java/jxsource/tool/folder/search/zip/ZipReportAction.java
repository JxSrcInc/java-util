package jxsource.tool.folder.search.zip;

import java.util.List;

import jxsource.tool.folder.file.JFile;

public abstract class ZipReportAction {
	public abstract void report(String url, List<JFile> extractFiles);
}
