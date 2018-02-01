package jxsource.tool.folder.search.filter;

import jxsource.tool.folder.file.AbstractJFile;

/**
 * getStatus() method determines the argument (JFile) is 
 * 1. ACCEPT, which results in search engine to fire actions and continue to search its children
 * 2. PASS, which results in search engine to continue to search its children but not fire actions
 * 3. REJECT, which results in search engine stops to search its children
 * 
 * Setting reject to true reverse above process -- change ACCEPT to REJECT and REJECT to ACCEPT.
 * However, the reject filter does not support "next" filter because unclear logic right now
 * TODO: need more investigations on this issue. 
 *
 */
public abstract class Filter {
	public static final int ACCEPT = 1;
	public static final int PASS = 0;
	public static final int REJECT = -1;
	private Filter next;
	private boolean reject;

	/**
	 * change accept filter to reject filter
	 * @param reject
	 */
	public void setReject(boolean reject) {
		this.reject = reject;
	}

	public void setNext(Filter filter) {
		next = filter;
	}

	/**
	 * implemented by sub-class to determine that "file" should be accept, 
	 * @param file
	 * @return
	 */
	public abstract int getStatus(AbstractJFile file);

	public int accept(AbstractJFile file) {
		if (!reject) {
			switch (getStatus(file)) {
			case ACCEPT:
				if (next != null) {
					return next.accept(file);
				} else {
					return ACCEPT;
				}
			case PASS:
				return PASS;
			default:
				return REJECT;
			}
		} else {
			// Partially support next Filter if file is real file
			// TODO: need more analysis if file is directory
			switch (getStatus(file)) {
			case ACCEPT:
				return REJECT;
			case PASS:
				return PASS;
			default:
				if (next != null) {
					return next.accept(file);
				} else {
					return ACCEPT;
				}
			}

		}
	}
}
