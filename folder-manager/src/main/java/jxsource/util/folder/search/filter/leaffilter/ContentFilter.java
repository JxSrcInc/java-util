package jxsource.util.folder.search.filter.leaffilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.util.RegexMatcher;
import jxsource.util.folder.search.util.Util;

/**
 * Filter
 * 
 * @author JiangJxSrc
 *
 */
public class ContentFilter extends LeafFilter {
	private static Logger log = LogManager.getLogger(ContentFilter.class);
	private Pattern p;
	private String match;
	private boolean wordMatch;
	private Map<String, List<String>> matchDisplay = new HashMap<String, List<String>>();

	ContentFilter(String match) {
		this.match = match;
		// (^|\\s) - line start or white space
		// (?i) - ignore case
		// (\\s|$) - white space or line end
		p = Pattern.compile("(^|\\s)((?i)" + match + ")(\\s|$)");
	}

	public Map<String, List<String>> getMatchDisplay() {
		return matchDisplay;
	}

	public ContentFilter setWordMatch(boolean wordMatch) {
		this.wordMatch = wordMatch;
		return this;
	}

	public int delegateStatus(Node node) {
		JFile file = (JFile) node;
		try {
			InputStream in = file.getInputStream();
			String content = Util.getContent(in).toString();
			List<String> matches;
			if(wordMatch) {
				matches = RegexMatcher.builder().setWord(true).build(match).find(content);
			} else {
				matches = RegexMatcher.builder().build(match).find(content);				
			}
			if (matches.size() > 0) {
				matchDisplay.put(file.getPath(), matches);
				return Filter.ACCEPT;
			} else {
				return Filter.REJECT;
			}
		} catch (IOException e) {
			log.warn("Error when creating content for file " + file.getPath(), e);
			return Filter.REJECT;
		}
	}
}
