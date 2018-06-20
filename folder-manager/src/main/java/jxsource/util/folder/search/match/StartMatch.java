package jxsource.util.folder.search.match;

public class StartMatch extends NodeMatch{

	protected boolean match(String nodeName, String match) {
		return nodeName.startsWith(match);
	}

}
