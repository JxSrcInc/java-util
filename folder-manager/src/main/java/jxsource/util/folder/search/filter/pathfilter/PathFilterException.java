package jxsource.util.folder.search.filter.pathfilter;

public class PathFilterException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public PathFilterException(String message) {
		super(message);
	}
	public PathFilterException(String message, Exception e) {
		super(message, e);
	}
}
