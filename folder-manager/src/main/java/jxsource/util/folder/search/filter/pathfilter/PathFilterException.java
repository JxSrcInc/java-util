package jxsource.util.folder.search.filter.pathfilter;

public class PathFilterException extends RuntimeException{
	public PathFilterException(String message) {
		super(message);
	}
	public PathFilterException(String message, Exception e) {
		super(message, e);
	}
}
