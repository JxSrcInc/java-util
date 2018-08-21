package jxsource.util.folder.search.filter.filefilter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilter;

public abstract class InputFilter extends FileFilter {
	private static Logger log = LogManager.getLogger(InputFilter.class);

	@Override
	public int delegateStatus(JFile file) {
			InputStream in = null;
			try {
				in = file.getInputStream();
				if (delegate(in)) {
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
	}

	/**
	 * Delegate the accept decision to sub class
	 * @param in
	 * @return
	 * @throws IOException
	 */
	protected abstract boolean delegate(InputStream in) throws IOException;
}
