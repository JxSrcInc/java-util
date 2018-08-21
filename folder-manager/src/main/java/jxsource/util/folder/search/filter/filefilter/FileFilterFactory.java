package jxsource.util.folder.search.filter.filefilter;

import jxsource.util.folder.manager.Manager;

public class FileFilterFactory {
	public static <T extends FileFilter> T create(Class<T> cl) throws InstantiationException, IllegalAccessException {
		T filter = cl.newInstance();
		return filter;
	}
}
