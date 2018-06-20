package jxsource.util.folder.search.match;

public class LikeMatch extends NodeMatch{

	protected boolean match(String nodeName, String match) {
		return nodeName.contains(match);
	}

}
