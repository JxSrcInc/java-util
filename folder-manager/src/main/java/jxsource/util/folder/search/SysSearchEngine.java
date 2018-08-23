package jxsource.util.folder.search;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;

/**
 * Search a folder
 * 
 * @author JiangJxSrc
 *
 */
public class SysSearchEngine extends SearchEngine<SysFile> {
	private static Logger log = LogManager.getLogger(SysSearchEngine.class);

	/**
	 * this is recursive method. the parameter in the first call is the root
	 * directory to search
	 * 
	 * @param file
	 * @throws IOException
	 */
	@Override
	public void search(File file) {
		SysFile sysFile = new SysFile(file);
		//NodeManagerHolder.get().add(sysFile);
		int status = consume(sysFile);
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

	public static void main(String...args) {
		try {
			SysSearchEngine engine = new SysSearchEngine();
//			Filter filter = new PathFilter("**/*.java");
			Filter filter = new PathFilter("testdata/test-data/src");
			engine.setFilter(filter);
			CollectionAction<SysFile> action = new CollectionAction<SysFile>();
			action.setLeafOnly(false);
			engine.addAction(action);
			engine.search(new File("testdata/test-data"));
			for(SysFile node: action.getNodes()) {
				log.info(node.getAbsolutePath());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
