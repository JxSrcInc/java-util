package jxsource.util.folder.search.action;

import jxsource.util.folder.node.Node;

public interface Action<T extends Node> {
	public void proc(T file);
}
