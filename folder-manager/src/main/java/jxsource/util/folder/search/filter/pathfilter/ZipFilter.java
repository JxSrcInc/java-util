package jxsource.util.folder.search.filter.pathfilter;

import jxsource.util.folder.search.util.Util;

public class ZipFilter extends ExtFilter{
	public ZipFilter() {
		super.add(Util.archiveTypes);
	}

}
