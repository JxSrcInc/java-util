package jxsource.util.folder.search.filter.filefilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.search.filter.Filter;

public class SaveFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(SaveFilter.class);
	@Override
	protected int delegateStatus(JFile file) {
		System.out.println(file);
		Filter modifyFilter = getModifyFilter(this);
		if(modifyFilter != null) {
			return Filter.ACCEPT;
		} else {
			log.warn("Cannot find ModifyFilter in parent filter chain.");
			// TODO: what should return?
			return Filter.REJECT;
		}
 
	}
	/**
	 * Make public for mock in JUnit test
	 * @param filter
	 * @return
	 */
	public Filter getModifyFilter(Filter filter) {
		System.out.println(filter);
		Filter before = filter.getBefore();
		if(before instanceof ModifyFilter) {
			return before;
		} else {
			Filter beforeBefore = before.getBefore();
			if(beforeBefore != null) {
				return getModifyFilter(beforeBefore);
			} else {
				return null;
			}
		}
	}
}
