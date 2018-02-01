package jxsource.tool.folder.search.filter.pathfilter;

import java.util.HashSet;
import java.util.Set;

import jxsource.tool.folder.file.AbstractJFile;
import jxsource.tool.folder.search.filter.Filter;

/**
 * Support ignore case and like search
 * @author JiangJxSrc
 *
 */
public abstract class AbstractFilter extends Filter {

	private boolean ignoreCase = true;
	private boolean like = false;
	
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
