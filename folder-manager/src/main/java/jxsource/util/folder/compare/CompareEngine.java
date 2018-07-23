package jxsource.util.folder.compare;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.compare.action.Action;
import jxsource.util.folder.compare.comparator.LastModifiedDiffer;
import jxsource.util.folder.compare.comparator.LeafDiffer;
import jxsource.util.folder.compare.comparator.LengthDiffer;
import jxsource.util.folder.compare.util.ComparePath;
import jxsource.util.folder.compare.util.Constants;
import jxsource.util.folder.node.Node;

public class CompareEngine {
	private static Logger log = LogManager.getLogger(CompareEngine.class);
	// default is LengthDiffer
	private LeafDiffer differ = new LengthDiffer();
	private Set<Action> actions = new HashSet<Action>();
	private Comparator<Node> comparator = (Node o1, Node o2) -> o1.getName().compareTo(o2.getName());
	private String srcRoot;
	private String compareRoot;
	private ComparePath srcComparePath;

	public CompareEngine setLeafDiffer(LeafDiffer differ) {
		this.differ = differ;
		return this;
	}

	public CompareEngine setActions(Set<Action> actions) {
		if (actions != null) {
			this.actions = actions;
		}
		return this;
	}

	public CompareEngine addAction(Action action) {
		this.actions.add(action);
		return this;
	}

	public boolean isDiff(ComparableNode comparableNode) {
		srcRoot = comparableNode.getSrc().getPath();
		compareRoot = comparableNode.getToCompare().getPath();
		srcComparePath = ComparePath.build(srcRoot);
		comparableNode.setComparePath("/");
		log.debug(Constants.srcSymbol + ": " + srcRoot);
		log.debug(Constants.cmpSymbol + ": " + compareRoot);
		return _isDiff(comparableNode);
	}

	/**
	 * @param comparableNode
	 * @return true - two nodes are different, false - two nodes are same
	 */
	private boolean _isDiff(ComparableNode comparableNode) {
		Node src = comparableNode.getSrc();
		Node toCompare = comparableNode.getToCompare();
		// src and toCompare may have different names in the first call
		// if one of src and toCompare is or both are dir.
		// but it is ok.
		// They must have the same name in recursive call - see
		// where it is called in compareChildren method
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
					if (diff) {
						if (differ.getActiveDiffer() instanceof LengthDiffer) {
							comparableNode.setDiffType(ComparableNode.LengthDiff);
						} else if (differ.getActiveDiffer() instanceof LastModifiedDiffer) {
							comparableNode.setDiffType(ComparableNode.LastModifiedDiff);
						}
					}
				} else {
					diff = false;
				}
			}
		} else {
			// different node types: one is leaf and one is not
			comparableNode.setDiffType(ComparableNode.NodeDiff);
			diff = true;
		}
		if (diff) {
			fire(comparableNode);
		}
		return diff;
	}

	/*
	 * 1. sort src and toCompare by name 2. compare the first two pair
	 * 
	 */
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
					// reset sChild
					if (sList.size() > 0) {
						sChild = sList.remove(0);
					} else {
						sChild = null;
					}
				} else if (status == 0) {
					// same
					ComparableNode comparableChild = new ComparableNode(sChild, cChild);
					comparableChild.setComparePath(srcComparePath.get(sChild));
					if (_isDiff(comparableChild)) {
						comparableNode.addDiff(comparableChild);
					} else {
						comparableNode.addSame(comparableChild);
					}
					// reset sChild and cChild
					if (sList.size() > 0) {
						sChild = sList.remove(0);
					} else {
						sChild = null;
					}
					if (cList.size() > 0) {
						cChild = cList.remove(0);
					} else {
						cChild = null;
					}
				} else {
					// cChild is missing
					comparableNode.addMissing(cChild);

					// reset cChild
					if (cList.size() > 0) {
						cChild = cList.remove(0);
					} else {
						cChild = null;
					}
				}
			} // end while loop

			// when out of loop, at most, one of cChild and sChild is not null
			if (cChild != null) {
				comparableNode.addMissing(cChild);
				for (Node missingNode : cList) {
					comparableNode.addMissing(missingNode);
				}
			} else if (sChild != null) {
				comparableNode.addExtra(sChild);
				for (Node extraNode : sList) {
					comparableNode.addExtra(extraNode);
				}
			}
			return complete(comparableNode);
		} else if (sList.size() == 0 && cList.size() == 0) {
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

	private void fire(ComparableNode node) {
		for (Action action : actions) {
			action.proc(node);
		}
	}
}
