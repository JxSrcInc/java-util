package jxsource.util.folder.search.filter.filefilter;

public class BackDirHolder {
    private static final ThreadLocal<BackDir> threadTraceLocal =
            new ThreadLocal<BackDir>() {
                @Override protected BackDir initialValue() {
                    return new BackDir();
            }
        };
	// after remove, threadTraceLocal will create a new ThreadTrace object
	public static void reset() {
		threadTraceLocal.remove();
	}
	public static BackDir get() {
		return threadTraceLocal.get();
	}

}
