package jxsource.util.folder.search.filter.leaffilter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;

public abstract class ContentFilter extends LeafFilter {
	private static Logger log = LogManager.getLogger(ContentFilter.class);

	@Override
	public int _getStatus(Node node) {
		if (node instanceof JFile) {
			JFile file = (JFile) node;
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
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						log.error(e);
					}
				}
			}
		} else {
			return Filter.REJECT;
		}
	}

	public abstract boolean accept(InputStream in) throws IOException;
}
