package jxsource.util.folder.search.filter.pathfilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.match.MatchFactory;
import jxsource.util.folder.search.match.NodeMatch;

/**
 * TreeFilter select leaf nodes in sub-tree starting from matched node.
 * 
 */
public class PathFilter extends Filter {
	private static Logger log = LogManager.getLogger(PathFilter.class);

	private NodeMatch[] matches;
	private boolean reject;

	public PathFilter(String pathMatch) {
		matches = MatchFactory.createPathMatch(pathMatch);
	}

	public void setReject(boolean reject) {
		this.reject = reject;
	}

	@Override
	public int delegateAccept(Node file) {
		if(reject) {
			return getRejectStatus(file);
		} else {
			return getAcceptStatus(file);
		}
	}
	
	private int getRejectStatus(Node file) {
		PathMatcher pathMatcher = new PathMatcher(matches);
		switch (pathMatcher.match(file)) {
		case PathMatcher.NotMatch:
		case PathMatcher.PartialMatch:
			// return accept when partial match
			// for search engine to invoke next filter on the file.
			// see difference from getAcceptStatus method
			return Filter.ACCEPT;
		default:
			// Match or OverMatch
			return Filter.REJECT;
		}
	}
	
	private int getAcceptStatus(Node file) {
		PathMatcher pathMatcher = new PathMatcher(matches);
		// Modified to allow accept File if the file is in accepted path
		switch (pathMatcher.match(file)) {
		case PathMatcher.NotMatch:
			// if file is directory and has children
			// let search engine works on the children
			// see difference from getRejectStatus method
			if(pathMatcher.containsAnyMatch()) {
				if(file.isDir() && file.getChildren().size() > 0) {
					return Filter.PASS;
				} else {
					return Filter.REJECT;
				}
			} else {
				return Filter.REJECT;
			}
		case PathMatcher.PartialMatch:
			// if file is directory and has children
			// let search engine works on the children
			// see difference from getRejectStatus method
			if(file.isDir() && file.getChildren().size() > 0) {
				return Filter.PASS;
			} else {
				// TODO: handle it as reject
				// For examples:
				// match is **/*.java and file has path a/b/c.xml
				// match is **/*.java and file has path a/b and b has no child
				// in both cases, search engine will not invoke action on file.
				return Filter.REJECT;
			}
		default:
			// Match or OverMatch
			return Filter.ACCEPT;
		}
	}

}
