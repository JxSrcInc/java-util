package jxsource.util.folder.search.match;

public class MatchFactory {
	private static boolean validate(String value) {
		if(value.isEmpty()) throw new RuntimeException("Cannot match empty Node");
		if(value.equals("*")) throw new RuntimeException("Not support '*' match");
		if(value.equals("**")) return true;
		// cannot have "**" and length > 2
		if(value.contains("**")) throw new RuntimeException("Not support filter that contains '**' and length is greater than 2");
		return true;
	}
	public static NodeMatch createMatch(String value, boolean ignoreCase) {
		validate(value);
		NodeMatch match;
		if(value.equals("**")) {
			match = new AnyMatch();
			match.init(value, ignoreCase);
		} else
		if(value.charAt(0) == '*') {
			if(value.charAt(value.length()-1) == '*') {
				// format *<match>*
				match = new LikeMatch();
				match.init(value.substring(1, value.length()-1), ignoreCase);
			} else {
				// format *<match>
				match = new EndMatch();
				match.init(value.substring(1), ignoreCase);
			}
		} else 
		if(value.charAt(value.length()-1) == '*') {	
			// format <match>*
			match = new StartMatch();
			match.init(value.substring(0, value.length()-1), ignoreCase);
		} else {
			match = new RegexMatch();
			match.init(value, ignoreCase);
		}
		return match;
	}
	
	public static NodeMatch[] createPathMatch(String pathMatch) {
		return createPathMatch(pathMatch, false);
	}
	public static NodeMatch[] createPathMatch(String pathMatch, boolean ignoreCase) {
		pathMatch = pathMatch.replaceAll("\\\\", "/");
		String[] segments = pathMatch.split("/");
		NodeMatch[] pathMatchArray = new NodeMatch[segments.length];
		for(int i=0; i<segments.length; i++) {
			pathMatchArray[i] = createMatch(segments[i], ignoreCase);
		}
		return pathMatchArray;
	}
}
