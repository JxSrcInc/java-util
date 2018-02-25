package jxsource.tool.folder.compare.comparator;

import jxsource.tool.folder.file.Node;

public class PathDiffer extends Differ{

	@Override
	protected boolean isDiff(Node src, Node compareTo) {
		return !src.getPath().equals(compareTo.getPath());
	}
}
