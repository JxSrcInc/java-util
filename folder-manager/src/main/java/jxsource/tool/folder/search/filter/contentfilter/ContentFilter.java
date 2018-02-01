package jxsource.tool.folder.search.filter.contentfilter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.file.JFile;
import jxsource.tool.folder.search.filter.Filter;

public abstract class ContentFilter extends Filter {
	private static Logger log = LogManager.getLogger(ContentFilter.class);

	@Override
	public int getStatus(JFile file) {
		if (file.isDirectory()) {
			// a safe way to handle directory
			return PASS;
		}
		InputStream in = null;
		try {
			in = file.getInputStream();
			if (accept(in)) {
				return ACCEPT;
			} else {
				return REJECT;
			}
		} catch (IOException e) {
			log.error("Error when getting input stream for " + file.getPath(), e);
			return REJECT;
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

	public abstract boolean accept(InputStream in) throws IOException;
}
