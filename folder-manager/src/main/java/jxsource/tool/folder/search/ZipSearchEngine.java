package jxsource.tool.folder.search;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.file.ZipFile;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;

public class ZipSearchEngine extends SearchEngine {
	private static Logger log = LogManager.getLogger(ZipSearchEngine.class);
	public void search(ZipInputStream zis) throws ZipException, IOException {
		ZipEntry entry;
		ZipFile parentNode = null;
		boolean ok = true;
		while ((entry = zis.getNextEntry()) != null) {
			// if(entry.isDirectory())
			// continue;
			ZipFile currNode = new ZipFile(entry, zis);
			// TODO: why need if condition?
			if (parentNode == null) {
				ok = consum(currNode, parentNode);
			} else {
				if(currNode.getPath().contains(parentNode.getPath()) && ok) {
					// process children only if parent is ACCEPT or PASS
					ok = consum(currNode, parentNode);
				} else {
					// currNode is not child of parentNode
					ok = consum(currNode, parentNode);
				}
			}
		}

		zis.close();
	}
	
	private boolean consum(ZipFile currNode, ZipFile parentNode) {
		log.debug(currNode+","+parentNode);
		if (consum(currNode)) {
			parentNode = currNode;
			return true;
		} else {
			return false;
		}
		
	}

}
