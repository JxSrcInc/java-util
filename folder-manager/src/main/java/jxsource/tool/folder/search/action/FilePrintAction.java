package jxsource.tool.folder.search.action;

import jxsource.tool.folder.file.JFile;

public class FilePrintAction implements Action {
	public void proc(JFile f) {
		if(!f.isDirectory())
			System.out.println("FilePrintAction: "+f.getPath());
	}
}
