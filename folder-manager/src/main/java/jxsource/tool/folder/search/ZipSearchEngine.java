package jxsource.tool.folder.search;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.file.AbstractJFile;
import jxsource.tool.folder.file.CacheFile;
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
	public void search(ZipInputStream zis) throws ZipException, IOException {
		ZipEntry entry;
		JFile parentNode = null;
		boolean ok = true;
		while ((entry = zis.getNextEntry()) != null) {
			JFile currNode = new ZipFile(entry, zis);
			if(consum(currNode) == Filter.ACCEPT && !currNode.isDirectory() && cache) {
					currNode = new CacheFile<ZipFile>((ZipFile)currNode);
			}
		}

		zis.close();
	}
	

}
