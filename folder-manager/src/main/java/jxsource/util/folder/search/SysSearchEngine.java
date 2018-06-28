package jxsource.util.folder.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;
import jxsource.util.folder.search.util.TreeFactory;

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
		//NodeManagerHolder.get().add(sysFile);
		int status = consum(sysFile);
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
			Filter filter = new PathFilter("**/.classpath");
			engine.setFilter(filter);
			CollectionAction action = new CollectionAction();
			action.setLeafOnly(false);
			engine.addAction(action);
			engine.search(new File("./.."));
//			Set<Node> set = TreeFactory.build().createTrees(action.getNodes());
//				ObjectMapper mapper = new ObjectMapper();
//				for(Node f: set) {
//					JsonNode node = f.convertToJson();
//					System.out.println(f.getPath()+" ****************************************************");
//					System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
//				}
			for(Node node: action.getNodes()) {
				System.out.println(node.getAbsolutePath());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
