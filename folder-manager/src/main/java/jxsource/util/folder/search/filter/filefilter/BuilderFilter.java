package jxsource.util.folder.search.filter.filefilter;

import java.io.IOException;
import java.io.InputStream;

import jxsource.util.folder.search.util.Util;

public abstract class BuilderFilter extends InputFilter{

	private StringBuilder content;
	@Override
	protected boolean delegate(InputStream in) throws IOException {
		content = Util.getContent(in);
		return accept(content);
	}
	
	public abstract boolean accept(StringBuilder content);
	
	
}
