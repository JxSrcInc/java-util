package jxsource.util.folder.search.filter.filefilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.util.RegexMatcher;
import jxsource.util.folder.search.util.Util;

public class ModifyFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(ModifyFilter.class);
	private String replacement;
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

	public ModifyFilter setRegex(String regex) {
		matcher = RegexMatcher.builder().build(regex);
		return this;
	}

	public boolean isChanged() {
		return changed;
	}

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
			log.error("Error when delegating in "+getClass().getName(), e);;
			return Filter.REJECT;
		}
 
	}

}
