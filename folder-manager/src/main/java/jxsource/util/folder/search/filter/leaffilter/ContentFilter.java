package jxsource.util.folder.search.filter.leaffilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.filefilter.BuilderFilter;
import jxsource.util.folder.search.filter.filefilter.BackDir;
import jxsource.util.folder.search.util.Util;

/**
 * Filter 
 * @author JiangJxSrc
 *
 */
public class ContentFilter extends LeafFilter{
	private static Logger log = LogManager.getLogger(ContentFilter.class);
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
	protected int delegateStatus(Node node) {
		JFile file = (JFile)node;
		try {
			InputStream in = file.getInputStream();
			String content = Util.getContent(in).toString();
			boolean ok = false;
			if(wordMatch) {
				ok = p.matcher(content).matches();
			} else {
				ok = content.contains(match);
			}
			if(ok) {
				return Filter.ACCEPT;
			} else {
				return Filter.REJECT;
			}
		
		} catch (IOException e) {
			log.warn("Error when creating content for file "+file.getPath(), e);
			return Filter.REJECT;
		}
	}
}
