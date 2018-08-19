package jxsource.util.folder.search.filter.leaffilter;

import java.io.IOException;
import java.io.InputStream;

import jxsource.util.folder.search.util.Util;

public abstract class CachedContentFilter extends ContentFilter{

	private StringBuilder content;
	@Override
	public boolean accept(InputStream in) throws IOException {
		content = Util.getContent(in);
		return accept(content);
	}
	
	public abstract boolean accept(StringBuilder content);
}
