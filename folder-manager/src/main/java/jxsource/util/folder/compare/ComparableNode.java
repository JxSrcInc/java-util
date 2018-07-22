package jxsource.util.folder.compare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jxsource.util.folder.compare.comparator.LastModifiedDiffer;
import jxsource.util.folder.compare.comparator.LeafDiffer;
import jxsource.util.folder.compare.comparator.LengthDiffer;
import jxsource.util.folder.compare.util.Constants;
import jxsource.util.folder.compare.util.JsonUtil;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

/**
 * ComparableNode contains two (folder) Nodes and compare results of their
 * children
 * 
 * @author JiangJxSrc
 *
 */
public class ComparableNode {
	public static String LengthDiff = "diff-len";
	public static String LastModifiedDiff = "diff-time";
	public static String NodeDiff = "diff-type";

	private Node src;
	private Node toCompare;
	// found in both and no difference.
	private Set<ComparableNode> same = new HashSet<ComparableNode>();
	// found in both but different.
	private Set<ComparableNode> diff = new HashSet<ComparableNode>();
	// missing in src - found in compareTo but not in src. The Set contains
	// compareTo's nodes
	private Set<Node> missing = new HashSet<Node>();
	// extra in src - found in src but not in compareTo. The Set contains src's
	// nodes
	private Set<Node> extra = new HashSet<Node>();
	private static ObjectMapper mapper = new ObjectMapper();
	private String diffType;
	private String comparePath;

	public ComparableNode(Node src, Node toCompare) {
		this.src = src;
		this.toCompare = toCompare;
	}

	public String getComparePath() {
		return comparePath;
	}

	public void setComparePath(String comparePath) {
		this.comparePath = comparePath;
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

	public String getDiffType() {
		return diffType;
	}

	public void setDiffType(String diffType) {
		this.diffType = diffType;
	}

}
