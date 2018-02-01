package jxsource.tool.folder.search.action;

import jxsource.tool.folder.file.AbstractJFile;

public class FilePrintAction implements Action {
	public void proc(AbstractJFile f) {
		if(!f.isDirectory())
			System.out.println("FilePrintAction: "+f.getPath());
	}
}
