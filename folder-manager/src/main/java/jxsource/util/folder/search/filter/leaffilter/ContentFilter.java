package jxsource.util.folder.search.filter.leaffilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private RegexMatcher regexMatcher;
	private Map<String, List<String>> matchDisplay = new HashMap<String, List<String>>();

	private ContentFilter() {
	}

	public Map<String, List<String>> getMatchDisplay() {
		return matchDisplay;
	}


	public int delegateStatus(Node node) {
		JFile file = (JFile) node;
		try {
			InputStream in = file.getInputStream();
			String content = Util.getContent(in).toString();
			List<String> matches;
//			if(wordMatch) {
//				matches = RegexMatcher.builder().setWord(true).build(match).find(content);
//			} else {
//				matches = RegexMatcher.builder().build(match).find(content);				
//			}
			matches = regexMatcher.find(content);
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
	
	public static class ContentFilterBuilder {
		private RegexMatcher regexMatcher;
		public ContentFilterBuilder setRegexMatcher(RegexMatcher regexMatcher) {
			this.regexMatcher = regexMatcher;
			return this;
		}
		public ContentFilter build() {
			assert regexMatcher != null: "ContentFilter's RegexMatcher cannot be null.";
			ContentFilter filter = new ContentFilter();
			filter.regexMatcher = regexMatcher;
			return filter;
		}
	}
	public static ContentFilterBuilder builder() {
		return new ContentFilterBuilder();
	}
}
