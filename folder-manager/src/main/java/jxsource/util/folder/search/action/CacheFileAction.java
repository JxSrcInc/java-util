package jxsource.util.folder.search.action;

import jxsource.util.folder.node.CacheFile;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

public class CacheFileAction implements Action{

	@Override
	public void proc(Node file) {
		if(!file.isDir() && file instanceof JFile) {
			// TODO:
		new CacheFile((JFile)file);
		}
	}

}
