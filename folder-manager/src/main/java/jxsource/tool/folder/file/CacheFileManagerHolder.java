package jxsource.tool.folder.file;

public class CacheFileManagerHolder {
    private static final ThreadLocal<CacheFileManager> threadLocal =
            new ThreadLocal<CacheFileManager>() {
                @Override protected CacheFileManager initialValue() {
                    return new CacheFileManager();
            }
        };

        // Returns the current thread's unique ID, assigning it if necessary
        public static CacheFileManager get() {
            return threadLocal.get();
        }

}
