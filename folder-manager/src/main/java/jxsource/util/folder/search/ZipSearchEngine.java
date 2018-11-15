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
import jxsource.util.folder.node.NodeManagerHolder;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.node.ZipFile;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;
import jxsource.util.folder.search.util.NodeUtil;
import jxsource.util.folder.search.util.Util;

/**
 * Search an archive (zip or jar) file passed in as JFile in search(JFile)
 * method.
 * 
 * Each entry of the archive is loaded as Node into NodeManager, and a node tree
 * is built from loaded Nodes. The build-tree process changed from optional to
 * required to allow super class SearchEngine to handle SysFile and ZipFile in
 * the same way.
 * 
 * Note: entries in the archive may not be a complete tree. So a special build
 * process is required. See NodeManager.buildTrees().
 * 
 * Use Filter to filter entries.
 * 
 * Use Action to specify how to process filtered entries
 * 
 * It supports both ZipFile and CacheFile<ZipFile> Use setCache(true) to work
 * with CacheFile<ZipFile>
 * 
 * @author JiangJxSrc
 *
 */
public class ZipSearchEngine extends SearchEngine<ZipFile> {
	private static Logger log = LogManager.getLogger(ZipSearchEngine.class);
	private NodeManager nodeManager = NodeManagerHolder.get();
	/*
	 * ZipFile entries does not have tree structure. It must be built using
	 * TreeFactory and NodeManager. However, simple search does not require file
	 * tree structure. Only compare operation requires it. buildTree flag indicate
	 * whether or not including it in zip search process
	 */
	private boolean buildTree;

	/**
	 * public method to search a zip/jar file
	 */
	@Override
	public void search(File f) {
		try {
			search(new SysFile(f));
		} catch (IOException e) {
			log.error("Error when search " + f.getPath(), e);
			throw new RuntimeException("Error when search " + f.getPath(), e);
		}
	}

	private void search(JFile f) throws ZipException, IOException {
		if (!Util.isArchive(f)) {
			throw new IOException(f.getPath() + " is not a jar or zip file.");
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(f.getPath()));
		ZipEntry entry;
		log.info("search zip file: " + f.getPath());
		while ((entry = zis.getNextEntry()) != null) {
			ZipFile currNode = new ZipFile(f.getPath(), entry, zis);
			nodeManager.add(currNode);
			log.debug("zip search: " + currNode.getPath());
		}
		if (buildTree) {
			List<ZipFile> list = new ArrayList<ZipFile>();
			// build tree and convert to list for consume.
			for (ZipFile node : getTrees()) {
				list.addAll(NodeUtil.convertTreeToList(node));
			}
			// ZipSearchEngine calls consume after tree build
			// to make PathFilter works.
			// Because PathFilter requires a complete tree
			// but zip file elements may not form a tree
			for (ZipFile node : list) {
				consume(node);
			}
		} else {
			for(Node node:nodeManager.getNodeList()) {
				consume((ZipFile)node);
			}
		}
		zis.close();
	}

	public Set<ZipFile> getTrees() {
		return nodeManager.buildTrees(ZipFile.class);
	}

	public ZipFile getTreeRootNode() {
		Iterator<ZipFile> i = getTrees().iterator();
		if (i.hasNext()) {
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
	public static void main(String... args) {
		// System.setProperty(ZipFile.ZipExtract, "true");
		try {
			// ZipInputStream in = new ZipInputStream(new FileInputStream("test-data.jar"));
			ZipSearchEngine engine = new ZipSearchEngine();
			Filter filter = new PathFilter("test-data/src");
			// filter.setNext(FilterFactory.create(FilterFactory.Name, "Data"));
			engine.setFilter(filter);
			CollectionAction<ZipFile> action = new CollectionAction<ZipFile>();
			engine.addAction(action);
			engine.search(new File("testdata\\test-data.jar"));
			String filteredNodes = "";
			for (ZipFile node : action.getNodes()) {
				filteredNodes += '\n' + node.getPath();
				JFile f = (JFile) node;
				InputStream in = f.getInputStream();
				byte[] b = new byte[1024 * 8];
				int i = 0;
				StringBuilder sb = new StringBuilder();
				while ((i = in.read(b)) != -1) {
					sb.append(new String(b, 0, i));
				}
				log.info(node.getPath() + "\n" + sb.toString());
			}
			log.info("filtered nodes:" + filteredNodes);
			ObjectMapper mapper = new ObjectMapper();
			for (Node f : engine.getTrees()) {
				JsonNode node = Util.convertToJson(f);
				log.info(f.getPath() + '\n' + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
