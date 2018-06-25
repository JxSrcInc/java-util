package jxsource.util.folder.compare;

import java.util.HashSet;
import java.util.Set;

import jxsource.util.folder.node.Node;

public class ComparableNode {
	private Node src;
	private Node toCompare;
	// found in both and no difference. 
	private Set<ComparableNode> same = new HashSet<ComparableNode>();
	// found in both but different. 
	private Set<ComparableNode> diff = new HashSet<ComparableNode>();
	// missing in src - found in compareTo but not in src. The Set contains compareTo's nodes
	private Set<Node> missing = new HashSet<Node>();
	// extra in src - found in src but not in compareTo. The Set contains src's nodes
	private Set<Node> extra = new HashSet<Node>();
	
	public ComparableNode(Node src, Node toCompare) {
		this.src = src;
		this.toCompare = toCompare;
	}

	public Node getSrc() {
		return src;
	}

	public Node getToCompare() {
		return toCompare;
	}


	public Set<ComparableNode> getSame() {
		return same;
	}

	public void addSame(ComparableNode same) {
		this.same.add(same);
	}

	public Set<ComparableNode> getDiff() {
		return diff;
	}

	public void addDiff(ComparableNode diff) {
		this.diff.add(diff);
	}

	public Set<Node> getMissing() {
		return missing;
	}

	public void addMissing(Node missing) {
		this.missing.add(missing);
	}

	public Set<Node> getExtra() {
		return extra;
	}

	public void addExtra(Node extra) {
		this.extra.add(extra);
	}
	

}