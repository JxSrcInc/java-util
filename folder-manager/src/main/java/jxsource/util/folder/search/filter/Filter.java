package jxsource.util.folder.search.filter;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

/**
 * getStatus() method determines the argument (JFile) is 
 * 1. ACCEPT, which results in search engine to fire actions and continue to search its children
 * 2. PASS, which results in search engine to continue to search its children but not fire actions 
 * 3. REJECT, which results in search engine stops to search its children
 * 
 * Setting reject to true reverse above process -- change ACCEPT to REJECT and
 * REJECT to ACCEPT. However, the reject filter does not support "next" filter
 * because unclear logic right now TODO: need more investigations on this issue.
 *
 */
public abstract class Filter {
	/**
	 * search child nodes and invoke actions
	 */
	public static final int ACCEPT = 1;
	/**
	 * search child nodes but not invoke actions.
	 * <p>
	 * case uses PASS: 
	 * 	PathFilter: a/match 
	 * 	Path 1: a/match 
	 * 	Path 2: a/b 
	 * search engine need to return PASS when processing JFile a, 
	 * because search engine cannot distinguish the path is
	 * Path 1 or Path 2
	 */
	public static final int PASS = 0;
	public static final int REJECT = -1;
	protected Filter next;

	public void setNext(Filter filter) {
		next = filter;
	}

	/**
	 * implemented by sub-class to determine that "file" should be accept,
	 * 
	 * @param file
	 * @return
	 */
	public abstract int delegateAccept(Node file);

	public int accept(Node file) {
		int status = delegateAccept(file);
		switch (status) {
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
	}
	
	public static String getStatusName(int status) {
		switch(status) {
		case ACCEPT:
			return "Accept";
		case PASS:
			return "Pass";
		case REJECT:
			return "Reject";
			default:
				throw new RuntimeException("Invalid status code: "+status);
		}
	}
}
