package jxsource.tool.folder.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.tool.folder.file.AbstractJFile;
import jxsource.tool.folder.file.CacheFile;
import jxsource.tool.folder.file.FileManager;
import jxsource.tool.folder.file.FileManagerHolder;
import jxsource.tool.folder.file.JFile;
import jxsource.tool.folder.file.ZipFile;
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
	private FileManager fileManager = FileManagerHolder.get();
	private boolean buildTree;
	
	/**
	 * true will create a normalized tree
	 * false will have better performance 
	 */
	public ZipSearchEngine buildTree(boolean buildTree) {
		this.buildTree = buildTree;
		return this;
	}
	public void search(ZipInputStream zis) throws ZipException, IOException {
		ZipEntry entry;
		JFile parentNode = null;
		boolean ok = true;
		while ((entry = zis.getNextEntry()) != null) {
			JFile currNode = new ZipFile(entry, zis);
			fileManager.add(currNode);
			if(buildTree) {
			}
			log.debug("zip search: "+currNode.getPath());
			consum(currNode);
//			if(consum(currNode) == Filter.ACCEPT && !currNode.isDirectory() && cache) {
//					currNode = new CacheFile(currNode);
//			}
		}

		zis.close();
		if(buildTree) {
		ObjectMapper mapper = new ObjectMapper();
		for(JFile f: fileManager.buildTrees()) {
			JsonNode node = f.convertToJson();
			log.debug(f.getPath()+" ****************************************************");
			log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
		}
		}
	}
	
	public static void main(String...args) {
		try {
			ZipInputStream in = new ZipInputStream(new FileInputStream("test-data.jar"));
			ZipSearchEngine engin = new ZipSearchEngine();
			engin.buildTree(true);
//			engin.addAction(new FilePrintAction());
			engin.search(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
