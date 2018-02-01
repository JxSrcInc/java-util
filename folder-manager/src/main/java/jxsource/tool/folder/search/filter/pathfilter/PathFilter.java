package jxsource.tool.folder.search.filter.pathfilter;

import jxsource.tool.folder.file.JFile;
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
		return pfp.start(file);
	}

}
