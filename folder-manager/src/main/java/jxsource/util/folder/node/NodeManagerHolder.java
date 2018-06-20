package jxsource.util.folder.node;

public class NodeManagerHolder {
    private static final ThreadLocal<NodeManager> threadLocal =
            new ThreadLocal<NodeManager>() {
                @Override protected NodeManager initialValue() {
                    return new NodeManager();
            }
        };

        // Returns the current thread's unique ID, assigning it if necessary
        public static NodeManager get() {
            return threadLocal.get();
        }

}
