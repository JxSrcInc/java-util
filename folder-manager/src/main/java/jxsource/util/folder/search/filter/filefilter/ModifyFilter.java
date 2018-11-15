package jxsource.util.folder.search.filter.filefilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.util.RegexMatcher;
import jxsource.util.folder.search.util.Util;

/**
 * Modify file content (in memory) if the RegexMatcher matches the file content.
 * Therefore
 * 1. if match, content changes.
 * 2. if not, content does not change
 *
 * A Filter after it can get changed content using getContent() method.
 * 
 * See jxsource.util.folder.manager.ModifyManager for how to use
 */
public class ModifyFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(ModifyFilter.class);
	/* replacement for RegexMatcher */
	private String replacement;
	/* file content */
	private String content;
	private RegexMatcher matcher;
	private boolean changed;
	private List<String> matches = new ArrayList<>();
	
	ModifyFilter() {}
	public String getReplacement() {
		return replacement;
	}

	public ModifyFilter setReplacement(String replacement) {
		this.replacement = replacement;
		return this;
	}

	public String getContent() {
		return content;
	}

	public ModifyFilter setRegexMatcher(RegexMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	public boolean isChanged() {
		return changed;
	}

	/**
	 * If file content matches RegesMatcher,
	 * replace all matched contents with replacement
	 */
	@Override
	protected int delegateStatus(JFile file) {
		try {
			InputStream in = file.getInputStream();
			content = Util.getContent(in).toString();
			matches = matcher.find(content);
			changed = matches.size() > 0;
			if(changed) {
				content = matcher.replace(content, replacement);
		        return Filter.ACCEPT;
			} else {
		        return Filter.REJECT;
			}
		} catch (IOException e) {
			log.error("Error when delegating in "+getClass().getName(), e);
			return Filter.REJECT;
		}
 
	}

}
