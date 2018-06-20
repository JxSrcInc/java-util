package jxsource.util.folder.search.filter.pathfilter;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.match.AnyMatch;
import jxsource.util.folder.search.match.NodeMatch;
import jxsource.util.folder.search.util.NodeUtil;

/**
 * A file path converts to String[] nodes Match requirements are given as
 * Match[] matches. Each element of nodes compares with corresponding each
 * element of matches to determine if the file is 1. NotMatch -- path and match
 * are different 2. PartialMatch -- path equals part of match starting from root
 * or match contains path 3. Match -- path and match are equal 4. OverMatch --
 * match equals part of path or path contains match
 * 
 */
public class PathMatcher {

	public static final String NotMatch = "NM";
	public static final String Match = "M";
	public static final String PartialMatch = "PM";
	public static final String OverMatch = "OM";

	private Logger log = LogManager.getLogger(PathMatcher.class);
	// list of NodeMatch created by MatchFactory for a path-match string
	private List<NodeMatch> matches = new ArrayList<NodeMatch>();
	// added to allow reuse matches
	private List<NodeMatch> workingMatches;
	private List<String> nodes = new ArrayList<String>();
	private boolean containsAnyMatch;
	/**
	 * 
	 * @param matches
	 *            - list of NodeMatch created by MatchFactory for a path-match
	 *            string
	 */
	public PathMatcher(NodeMatch[] matches) {
		if(matches.length == 0) {
			throw new PathFilterException("Path match cannot be empty.");
		}
		for (NodeMatch m : matches) {
			this.matches.add(m);
			if(m instanceof AnyMatch) {
				containsAnyMatch = true;
			}
		}
	}
	public boolean containsAnyMatch() {
		return containsAnyMatch;
	}
	/**
	 * Match a single node, not a tree
	 * 
	 * @param node
	 * @return true if file is a node in the tree defined by matches. otherwise,
	 *         false
	 */
	public String match(Node node) {
		workingMatches = new ArrayList<NodeMatch>(matches);
		nodes.clear();
		String path = node.getPath();
		for (String nodeName : path.split("/")) {
			nodes.add(nodeName);
		}
		boolean match = false;
		while (nodes.size() > 0 && workingMatches.size() > 0) {
			boolean anyMatch = workingMatches.get(0) instanceof AnyMatch;
			if (anyMatch) {
				match = anyMatch();
			} else {
				match = match();
			}
			if (!match) {
				// if not match, reject file
				break;
			} // else - continue loop
		}
		String status = NotMatch;
		if (match) {
			// matched
			if (nodes.size() == 0 && workingMatches.size() == 0) {
				status = Match;
			} else if (nodes.size() == 0) {
				status = PartialMatch;
			} else {
				return OverMatch;
			}
		}
		log.trace(status + " " + path);
		return status;
	}

	private boolean anyMatch() {
		// remove AnyMatch
		workingMatches.remove(0);
		if (workingMatches.size() == 0) {
//			// clear nodes so when returned, break while loop
//			nodes.clear();
			return true;
		}
		NodeMatch match = workingMatches.remove(0);
		// does one element in nodes matches match
		int index = NodeUtil.isOnPath(nodes, match);
		if (index < 0) {
			return false;
		} else {
			for (int i = 0; i <= index; i++) {
				nodes.remove(0);
			}
			return true;
		}
	}

	/**
	 * remove the first elements from workingMatches and nodes and compare them.
	 * 
	 * @return
	 */
	private boolean match() {
		if (0 < workingMatches.size() && 0 < nodes.size()) {
			if (workingMatches.get(0) instanceof AnyMatch) {
				// let anyMatch to handle
				return true;
			}
			NodeMatch m = workingMatches.remove(0);
			String nodeName = nodes.remove(0);
			return m.match(nodeName);
		} else {
			return false;
		}
	}
}
