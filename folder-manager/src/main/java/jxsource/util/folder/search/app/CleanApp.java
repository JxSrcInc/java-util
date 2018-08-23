package jxsource.util.folder.search.app;

import jxsource.util.folder.search.filter.filefilter.BackDirHolder;

public class CleanApp {
	public static void main(String...args) {
		BackDirHolder.get().clear();
	}
}
