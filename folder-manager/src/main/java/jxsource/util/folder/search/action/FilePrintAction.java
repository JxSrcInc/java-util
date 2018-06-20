package jxsource.util.folder.search.action;

import jxsource.util.folder.node.Node;

public class FilePrintAction implements Action {
	public void proc(Node f) {
		if(!f.isDir())
			System.out.println("FilePrintAction: "+f.getPath());
	}
}
