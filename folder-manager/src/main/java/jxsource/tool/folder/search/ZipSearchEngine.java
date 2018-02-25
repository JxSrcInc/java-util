package jxsource.tool.folder.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.tool.folder.node.AbstractNode;
import jxsource.tool.folder.node.CacheFile;
import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.NodeManager;
import jxsource.tool.folder.node.NodeManagerHolder;
import jxsource.tool.folder.node.Node;
import jxsource.tool.folder.node.Node;
import jxsource.tool.folder.node.ZipFile;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;

/**
 * It supports both ZipFile and CacheFile<ZipFile>
 * Use setCache(true) to work with CacheFile<ZipFile>
 * @author JiangJxSrc
 *
 */
public class ZipSearchEngine extends SearchEngine {
	private static Logger log = LogManager.getLogger(ZipSearchEngine.class);
	private NodeManager fileManager = NodeManagerHolder.get();
	private List<Node> trees;
	/**
	 * true will create a normalized tree
	 * false will have better performance 
	 */
	public void search(ZipInputStream zis) throws ZipException, IOException {
		ZipEntry entry;
		Node parentNode = null;
		boolean ok = true;
		while ((entry = zis.getNextEntry()) != null) {
			JFile currNode = new ZipFile(entry, zis);
			fileManager.add(currNode);
			log.debug("zip search: "+currNode.getPath());
			consum(currNode);
//			if(consum(currNode) == Filter.ACCEPT && !currNode.isDirectory() && cache) {
//					currNode = new CacheFile(currNode);
//			}
		}
		getTrees();
		zis.close();
	}
	public List<Node> getTrees() {
		if(trees == null) {
			Set<Node> set = fileManager.buildTrees();
			trees = new ArrayList<Node>(set.size());	
			for(Node node: set) {
				trees.add((Node)node);
			}
		}
		return trees;
	}
	
	public static void main(String...args) {
		try {
			ZipInputStream in = new ZipInputStream(new FileInputStream("test-data.jar"));
			ZipSearchEngine engin = new ZipSearchEngine();
			engin.search(in);
				ObjectMapper mapper = new ObjectMapper();
				for(Node f: engin.getTrees()) {
					JsonNode node = f.convertToJson();
					System.out.println(f.getPath()+" ****************************************************");
					System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
				}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
