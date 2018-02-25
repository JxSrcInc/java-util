package jxsource.tool.folder.search.filter.pathfilter;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.match.Match;
import jxsource.tool.folder.search.match.MatchFactory;

/**
 * PathFilter does not extend AbstractFilter
 * It use PathMatcher which can handle more complex filter than ignore case and like
 *
 */
public class PathFilter extends Filter{
	private Match[] matches;

	public PathFilter(String pathMatch) {
		matches = MatchFactory.createPathMatch(pathMatch);
	}
	@Override
	public int getStatus(JFile file) {
		PathMatcher pfp = new PathMatcher(matches);
		// Modified to allow accept File if the file is in accepted path
		switch(pfp.start(file)) {
		case Filter.PASS:
			if(file.isArray()) {
				return Filter.PASS;
			} else {
				return Filter.ACCEPT;
			}
		case Filter.ACCEPT:
			return Filter.ACCEPT;
			default:
				return Filter.REJECT;
		}
	}

}
