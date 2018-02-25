package jxsource.tool.folder.compare.comparator;

import jxsource.tool.folder.node.Node;

public class PathDiffer extends Differ{

	@Override
	protected boolean isDiff(Node src, Node compareTo) {
		return !src.getPath().equals(compareTo.getPath());
	}
}
