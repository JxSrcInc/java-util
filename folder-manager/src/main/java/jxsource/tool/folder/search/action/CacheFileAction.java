package jxsource.tool.folder.search.action;

import jxsource.tool.folder.node.CacheFile;
import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;

public class CacheFileAction implements Action{

	@Override
	public void proc(Node file) {
		if(!file.isArray() && file instanceof JFile)
			// TODO:
		new CacheFile((JFile)file);
	}

}
