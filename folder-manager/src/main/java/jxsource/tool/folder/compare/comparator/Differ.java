package jxsource.tool.folder.compare.comparator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jxsource.tool.folder.compare.CFile;

public abstract class Differ {

	protected Differ next;

	protected abstract boolean isDiff(CFile src, CFile compareTo);

	public boolean diff(CFile src, CFile compareTo) {
		if (src.isDirectory() && !compareTo.isDirectory()) {
			return true;
		} else if (!src.isDirectory() && compareTo.isDirectory()) {
			return true;
		} else if (isDiff(src, compareTo)) {
			return true;
		} else {
			// no difference
			if (next != null) {
				return next.diff(src, compareTo);
			} else {
				return false;
			}
		}
	}

}
