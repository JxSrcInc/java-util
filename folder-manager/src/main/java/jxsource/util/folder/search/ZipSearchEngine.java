package jxsource.util.folder.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.NodeManager;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.node.ZipFile;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.leaffilter.FilterFactory;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;
import jxsource.util.folder.search.util.NodeUtil;
import jxsource.util.folder.search.util.Util;

/**
 * Search an archive (zip or jar) file passed in as JFile in search(JFile) method.
 * 
 * Each entry of the archive is loaded as Node into NodeManager, 
 * and a node tree is built from loaded Nodes. 
 * The build-tree process changed from optional to required to allow super class
 * SearchEngine to handle SysFile and ZipFile in the same way.
 * 
 * Note: entries in the archive may not be a complete tree. So a special build
 * process is required. See NodeManager.buildTrees().
 * 
 * Use Filter to filter entries.
 * 
 * Use Action to specify how to process filtered entries
 * 
 * It supports both ZipFile and CacheFile<ZipFile>
 * Use setCache(true) to work with CacheFile<ZipFile>
 * @author JiangJxSrc
 *
 */
public class ZipSearchEngine extends SearchEngine {
	private static Logger log = LogManager.getLogger(ZipSearchEngine.class);
	private NodeManager nodeManager = new NodeManager();//NodeManagerHolder.get();

	/**
	 * public method to search a zip/jar file
	 */
	public void search(JFile f) throws ZipException, IOException {
		if(!Util.isArchive(f)) {
			throw new IOException(f.getPath()+" is not a jar or zip file.");
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(f.getPath()));
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			ZipFile currNode = new ZipFile(f.getPath(), entry, zis);
			nodeManager.add(currNode);
			log.debug("zip search: "+currNode.getPath());
		}
		
		List<Node> list = new ArrayList<Node>();
		// build tree and convert to list for consume.
		for(Node node: getTrees()) {
			list.addAll(NodeUtil.convertTreeToList(node));			
		}
		// ZipSearchEngine calls consume after tree build
		// to make PathFilter works. 
		// Because PathFilter requires a complete tree
		// but zip file elements may not form a tree
		for(Node node: list) {
			consume(node);
		}
		zis.close();
	}
	public Set<Node> getTrees() {
		return nodeManager.buildTrees();
	}
	
	public Node getTreeRootNode() {
		Iterator<Node> i = getTrees().iterator();
		if(i.hasNext()) {
			return i.next();
		} else {
			return null;
		}
	}

	/**
	 * sample code that displays tree and filtered nodes using Filter and Action
	 * 
	 * @param args
	 */
	public static void main(String...args) {
		System.setProperty(ZipFile.CachePropertyName, ZipFile.Memory);
		try {
//			ZipInputStream in = new ZipInputStream(new FileInputStream("test-data.jar"));
			ZipSearchEngine engine = new ZipSearchEngine();
			Filter filter = new PathFilter("test-data/src");
//			filter.setNext(FilterFactory.create(FilterFactory.Name, "Data"));
			engine.setFilter(filter);
			CollectionAction action = new CollectionAction();
			engine.addAction(action);
			engine.search(new SysFile(new File("test-data.jar")));
				ObjectMapper mapper = new ObjectMapper();
				for(Node f: engine.getTrees()) {
					JsonNode node = Util.convertToJson(f);
					log.info(f.getPath()+'\n'+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
				}
			String filteredNodes = "";
			for(Node node: action.getNodes()) {
				filteredNodes += '\n'+node.getPath();
				JFile f = (JFile) node;
				InputStream in = f.getInputStream();
				byte[] b = new byte[1024*8];
				int i = 0;
				StringBuilder sb = new StringBuilder();
				while((i=in.read(b)) != -1 ) {
					sb.append(new String(b,0,i));
				}
				log.info(node.getPath()+"\n"+sb.toString());
			}
			log.info("filtered nodes:"+filteredNodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
