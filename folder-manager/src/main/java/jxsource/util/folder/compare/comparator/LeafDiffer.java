package jxsource.util.folder.compare.comparator;

import jxsource.util.folder.node.Node;

public abstract class LeafDiffer {

	private LeafDiffer next;

	protected abstract boolean isDiff(Node src, Node compareTo);

	public LeafDiffer setNext(LeafDiffer next) {
		this.next = next;
		return this;
	}
	public boolean diff(Node src, Node compareTo) {
		if (isDiff(src, compareTo)) {
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
