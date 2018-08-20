package jxsource.util.folder.search.filter.leaffilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import jxsource.util.folder.search.filter.filefilter.BuilderFilter;
import jxsource.util.folder.search.util.Util;

/**
 * Filter 
 * @author JiangJxSrc
 *
 */
public class ContentFilter extends BuilderFilter{
	private Pattern p;
	private String match;
	private boolean wordMatch;
	ContentFilter(String match) {
		this.match = match;
		// (\\n|.)* - any number of char and \n
		// (?i) - ignore case
		// (\\W+|^) - at least one \W or begin of line
		// (\\W+|$) - at least one \W or end of line
		p = Pattern.compile("(\\n|.)*(?i)(\\W+|^)"+match+"(\\W+|$).*");
	}
	public ContentFilter setWordMatch(boolean wordMatch) {
		this.wordMatch = wordMatch;
		return this;
	}
	@Override
	public boolean accept(StringBuilder sb) {
		String content = sb.toString();
		if(wordMatch) {
			return p.matcher(content).matches();
		} else {
			return content.contains(match);
		}
	}
}
