package jxsource.util.folder.search.filter.filefilter;

public class FileFilterFactory {
	public static <T extends FileFilter> T create(Class<T> cl) {
		try {
			T filter = cl.newInstance();
			return filter;
		} catch(Exception e) {
			throw new RuntimeException("Cannot create FileFilter: "+cl.getSimpleName(), e);
		}
	}
}
