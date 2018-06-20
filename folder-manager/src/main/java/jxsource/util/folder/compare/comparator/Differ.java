package jxsource.util.folder.compare.comparator;

import jxsource.util.folder.node.Node;

public abstract class Differ {

	protected Differ next;

	protected abstract boolean isDiff(Node src, Node compareTo);

	public boolean diff(Node src, Node compareTo) {
		if (src.getChildren().size() != compareTo.getChildren().size()) {
			return true;
		} else if (isDiff(src, compareTo)) {
			return true;
		} else {
			// no difference
			if (next != null) {
				return next.diff(src, compareTo);
			} else {
				return false;
			}
		}
	}

}
