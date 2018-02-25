package jxsource.tool.folder.search;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.node.CacheFile;
import jxsource.tool.folder.node.SysFile;
import jxsource.tool.folder.node.ZipFile;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;

/**
 * Search a folder
 * 
 * @author JiangJxSrc
 *
 */
public class SysSearchEngine extends SearchEngine {
	private static Logger log = LogManager.getLogger(SysSearchEngine.class);

	/**
	 * this is recursive method. the parameter in the first call is the root
	 * directory to search
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void search(File file) {
		SysFile sysFile = new SysFile(file);
		int status = consum(sysFile);
		// if(status == Filter.ACCEPT && !file.isDirectory() && cache) {
		// new CacheFile(sysFile);
		// }
		if (status == Filter.ACCEPT || status == Filter.PASS) {
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				if (children != null) {
					for (File child : children) {
						search(child);
					}
				}
			}
		}
	}

}
