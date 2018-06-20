package jxsource.util.folder.search.match;

/**
 * 
 * @author JiangJxSrc
 *
 * Changed from Match to NodeMatch because it is not general Regex match
 */
public abstract class NodeMatch {
	protected boolean ignoreCase;
	// we need this field to keep user imports
	// so we can handle ignoreCase when call sub-class's match(String, String) method
	private String match;
	
	public String getMatch() {
		return match;
	}
	/**
	 * must create in MatchFactory
	 * and call setMatch() to set match value
	 */
	NodeMatch() {}
	void init(String match, boolean ignoreCase) {
		this.match = match;		
		this.ignoreCase = ignoreCase;
	}
	
	public boolean match(String nodeName) {
		if(ignoreCase) {
			return match(nodeName.toLowerCase(), match.toLowerCase());
		} else {
			return match(nodeName, match);
		}
	}
	
	@Override
	public String toString() {
		return match;
	}
	
	/**
	 * 
	 * @param nodeName - node is a segment of JFile path, which is also a JFile
	 * 		nodeName is the value of getName() of the JFile
	 * @param match - we pass match value through this method instead of 
	 * 		let sub-class to get it from match field because we need to handle
	 * 		ignoreCase.
	 * @return
	 */
	protected abstract boolean match(String nodeName, String match);
}
