package jxsource.tool.folder.search;

import java.io.File;

import jxsource.tool.folder.file.SysFile;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;

public class SysSearchEngine extends SearchEngine {
	
	public void search(File file) {
		if(consum(new SysFile(file))) {
			if(file.isDirectory()) {
			for(File child: file.listFiles()) {
				search(child);
			}
			}
		}
	}
	
}
