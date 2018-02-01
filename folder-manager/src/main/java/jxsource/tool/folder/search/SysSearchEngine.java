package jxsource.tool.folder.search;

import java.io.File;

import jxsource.tool.folder.file.SysFile;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;

/**
 * Search a folder 
 * @author JiangJxSrc
 *
 */
public class SysSearchEngine extends SearchEngine {
	
	/**
	 * this is recursive method.
	 * the parameter in the first call is the root directory to search
	 * @param file
	 */
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
