package jxsource.util.folder.search.action;

import jxsource.util.folder.node.Node;

public class FilePrintAction<T extends Node> implements Action<T> {
	public void proc(T f) {
		if(!f.isDir())
			System.out.println("FilePrintAction: "+f.getPath());
	}
}
