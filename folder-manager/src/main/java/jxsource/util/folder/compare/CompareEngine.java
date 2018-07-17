package jxsource.util.folder.compare;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.compare.action.Action;
import jxsource.util.folder.compare.comparator.LeafDiffer;
import jxsource.util.folder.node.Node;

public class CompareEngine {
	private static Logger log = LogManager.getLogger(CompareEngine.class);
	private LeafDiffer differ;
	private Action action;
	private Comparator<Node> comparator = (Node o1, Node o2) -> o1.getName().compareTo(o2.getName());

	public CompareEngine setLeafDiffer(LeafDiffer differ) {
		this.differ = differ;
		return this;
	}

	public CompareEngine setAction(Action action) {
		this.action = action;
		return this;
	}

	/**
	 * @param comparableNode
	 * @return true - two nodes are different, false - two nodes are same
	 */
	public boolean isDiff(ComparableNode comparableNode) {
		Node src = comparableNode.getSrc();
		Node toCompare = comparableNode.getToCompare();
		// src and toCompare may have different names in the first call
		// if one of src and toCompare is or both are dir.
		// but it is ok.
		// They must have the same name in recursive call - see 
		// where it is called in compareChildren method
		log.debug(src.getPath()+","+toCompare.getPath());
		boolean diff;
		if (src.isDir() && toCompare.isDir()) {
			diff = compareChildren(comparableNode);
		} else if (!src.isDir() && !toCompare.isDir()) {
			// compare leaf
			if (!src.getName().equals(toCompare.getName())) {
				// different name
				diff = true;
			} else {
				// same name, continue to compare other features, like time, length
				if (differ != null) {
					diff = differ.diff(src, toCompare);
				} else {
					diff = false;
				}
			}
		} else {
			// different node types: one is leaf and one is not
			diff = true;
		}
		if (diff) {
			fire(src, toCompare);
		}
		return diff;
	}

	// TODO: not right.
	private boolean compareChildren(ComparableNode comparableNode) {
		Node src = comparableNode.getSrc();
		Node toCompare = comparableNode.getToCompare();
		List<Node> sList = src.getChildren();
		Collections.sort(sList, comparator);
		List<Node> cList = toCompare.getChildren();
		Collections.sort(cList, comparator);
		if (sList.size() > 0 && cList.size() > 0) {
			Node sChild = sList.remove(0);
			Node cChild = cList.remove(0);

			while (sChild != null && cChild != null) {
				int status = sChild.getName().compareTo(cChild.getName());
				if (status < 0) {
					// sChild is extra
					comparableNode.addExtra(sChild);
				} else if (status == 0) {
					// same
					ComparableNode comparableChild = new ComparableNode(sChild, cChild);
					if (isDiff(comparableChild)) {
						comparableNode.addDiff(comparableChild);
					} else {
						comparableNode.addSame(comparableChild);
					}
				} else {
					// cChild is missing
					comparableNode.addMissing(cChild);
				}
				// reset sChild and cChild
				if(sList.size() > 0) {
					sChild = sList.remove(0);
				} else {
					sChild = null;
				}
				if(cList.size() > 0) {
					cChild = cList.remove(0);
				} else {
					cChild = null;
				}
			}
			if(cChild != null) {
				comparableNode.addMissing(cChild);
			}
			if(sChild != null) {
				comparableNode.addExtra(sChild);
			}
			return complete(comparableNode);
		} else
		if (sList.size() == 0 && cList.size() == 0) {
			// complete
			return complete(comparableNode);
		} else if (cList.size() > 0) {
			// add missing nodes
			for (Node child : cList) {
				comparableNode.addMissing(child);
			}
			// complate
			return true;

		} else {
			// sList.size) > 0
			// add extra nodes
			for (Node child : sList) {
				comparableNode.addExtra(child);
			}
			// complate
			return true;
		}
	}

	private boolean complete(ComparableNode comparableNode) {
		return comparableNode.getDiff().size() > 0 || comparableNode.getExtra().size() > 0
				|| comparableNode.getMissing().size() > 0;
	}

	private void fire(Node src, Node compareTo) {
		if (action != null) {
			action.proc(src, compareTo);
		}
	}
}
