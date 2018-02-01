package jxsource.tool.folder.compare.comparator;

public class DefaultDiffer extends PathDiffer{
	
	public DefaultDiffer() {
		next = new LengthDiffer();
	}

}
