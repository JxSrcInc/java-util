package jxsource.tool.folder.search.action;

import jxsource.tool.folder.file.CacheFile;
import jxsource.tool.folder.file.JFile;

public class CacheFileAction implements Action{

	@Override
	public void proc(JFile file) {
		if(!file.isDirectory())
		new CacheFile(file);
	}

}
