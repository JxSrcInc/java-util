package jxsource.util.folder.search.match;

public class EndMatch extends NodeMatch{

	protected boolean match(String nodeName, String match) {
		return nodeName.endsWith(match);
	}

}
