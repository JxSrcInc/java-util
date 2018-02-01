package jxsource.tool.folder.search.filter.contentfilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import jxsource.tool.folder.search.util.Util;

/**
 * Filter 
 * @author JiangJxSrc
 *
 */
public class SimpleContentFilter extends ContentFilter{
	private Pattern p;
	private String match;
	private boolean wordMatch;
	public SimpleContentFilter(String match) {
		this.match = match;
		// (\\n|.)* - any number of char and \n
		// (?i) - ignore case
		// (\\W+|^) - at least one \W or begin of line
		// (\\W+|$) - at least one \W or end of line
		p = Pattern.compile("(\\n|.)*(?i)(\\W+|^)"+match+"(\\W+|$).*");
	}
	public SimpleContentFilter setWordMatch(boolean wordMatch) {
		this.wordMatch = wordMatch;
		return this;
	}
	@Override
	public boolean accept(InputStream in) throws IOException {
		String content = Util.getContent(in);
		if(wordMatch) {
			return p.matcher(content).matches();
		} else {
			return content.contains(match);
		}
	}
}
