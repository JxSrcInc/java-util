package jxsource.util.folder.compare.comparator;

public class DefaultDiffer extends PathDiffer{
	
	public DefaultDiffer() {
		next = new LengthDiffer();
	}

}
