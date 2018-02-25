package jxsource.tool.folder.search.action;

import jxsource.tool.folder.node.Node;

public class FilePrintAction implements Action {
	public void proc(Node f) {
		if(!f.isArray())
			System.out.println("FilePrintAction: "+f.getPath());
	}
}
