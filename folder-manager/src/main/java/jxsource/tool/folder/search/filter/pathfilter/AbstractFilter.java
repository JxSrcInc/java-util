package jxsource.tool.folder.search.filter.pathfilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxsource.tool.folder.node.AbstractNode;
import jxsource.tool.folder.search.filter.Filter;

/**
 * Support ignore case and like search
 * @author JiangJxSrc
 *
 */
public abstract class AbstractFilter extends Filter {

	protected boolean ignoreCase = true;
	protected boolean like = false;
	protected List<String> matchs = new ArrayList<String>();

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
	public AbstractFilter setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
		return this;
	}
	public boolean isLike() {
		return like;
	}
	public AbstractFilter setLike(boolean like) {
		this.like = like;
		return this;
	}
	protected boolean _accept(String src, String match) {
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
