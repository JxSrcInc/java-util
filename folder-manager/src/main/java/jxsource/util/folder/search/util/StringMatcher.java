package jxsource.util.folder.search.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxsource.util.folder.node.AbstractNode;
import jxsource.util.folder.search.filter.Filter;

/**
 * Support ignore case and like search
 * @author JiangJxSrc
 *
 */
public class StringMatcher {

	protected boolean ignoreCase = true;
	protected boolean like = false;
	protected boolean exclude = false;
	protected List<String> matchs = new ArrayList<String>();

	public List<String> getMatchList() {
		return matchs;
	}
	public void add(String[] matchs) {
		for(String match: matchs) {
			this.matchs.add(match.trim());
		}
	}
	public void add(String multiMatch) {
		add(multiMatch.split(","));
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}
	public StringMatcher setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
		return this;
	}
	public boolean isLike() {
		return like;
	}
	public StringMatcher setLike(boolean like) {
		this.like = like;
		return this;
	}
	
	public boolean isExclude() {
		return exclude;
	}
	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}
	public boolean match(String src) {
		for(String match: matchs) {
			if(contains(src, match)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * if src contains match
	 * @param src
	 * @param match
	 * @return
	 */
	protected boolean contains(String src, String match) {
		if(ignoreCase) {
			src = src.toLowerCase();
			match = match.toLowerCase();
		}
		if(like) {
			return src.contains(match);
		} else {
			return src.equals(match);
		}
	}

}
