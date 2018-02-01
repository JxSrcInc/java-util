package jxsource.tool.folder.file;

public class FileManagerHolder {
    private static final ThreadLocal<FileManager> threadLocal =
            new ThreadLocal<FileManager>() {
                @Override protected FileManager initialValue() {
                    return new FileManager();
            }
        };

        // Returns the current thread's unique ID, assigning it if necessary
        public static FileManager get() {
            return threadLocal.get();
        }

}
