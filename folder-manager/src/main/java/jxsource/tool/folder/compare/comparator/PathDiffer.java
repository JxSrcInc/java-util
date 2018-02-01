package jxsource.tool.folder.compare.comparator;

import jxsource.tool.folder.compare.CFile;

public class PathDiffer extends Differ{

	@Override
	protected boolean isDiff(CFile src, CFile compareTo) {
		return !src.getPath().equals(compareTo.getPath());
	}
}
