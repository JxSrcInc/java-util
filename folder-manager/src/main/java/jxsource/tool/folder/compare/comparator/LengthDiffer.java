package jxsource.tool.folder.compare.comparator;

import jxsource.tool.folder.compare.CFile;

public class LengthDiffer extends Differ {

	@Override
	protected boolean isDiff(CFile src, CFile compareTo) {
		if (!src.isDirectory() && !compareTo.isDirectory()) {
			return src.getLength() != compareTo.getLength();
		} else if (src.isDirectory() && compareTo.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

}
