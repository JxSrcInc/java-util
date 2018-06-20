package jxsource.util.folder.compare.comparator;

import jxsource.util.folder.node.Node;

public class PathDiffer extends Differ{

	@Override
	protected boolean isDiff(Node src, Node compareTo) {
		return !src.getPath().equals(compareTo.getPath());
	}
}
