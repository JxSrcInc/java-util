package jxsource.util.folder.search.filter;

import java.util.HashMap;
import java.util.Map;

import jxsource.util.folder.search.filter.contentfilter.SimpleContentFilter;
import jxsource.util.folder.search.filter.pathfilter.AbstractFilter;
import jxsource.util.folder.search.filter.pathfilter.ExtFilter;
import jxsource.util.folder.search.filter.pathfilter.FullNameFilter;
import jxsource.util.folder.search.filter.pathfilter.NameFilter;
import jxsource.util.folder.search.filter.pathfilter.TimeFilter;

public class FilterFactory {
	
	public static final String Ext = "Ext";
	public static final String Name = "Name";
	public static final String FullName = "FullName";
	public static final String Time = "Time";

	public static Filter create(String type, String value) {
		return create(type, value, new HashMap<Integer, Boolean>());
	}

	public static Filter create(String type, String value, Map<Integer, Boolean> option) {
		AbstractFilter filter = null;
		switch(type) {
		case Ext:
			filter = new ExtFilter();
			break;
		case Name:
			filter = new NameFilter();
			break;
		case FullName:
			filter = new FullNameFilter();
			break;
		case Time:
			filter = new TimeFilter();
			break;
			default:
				throw new RuntimeException("Invalid Filter type: "+type);
		}
		filter.add(value);
		for(Map.Entry<Integer, Boolean> entry: option.entrySet()) {
			if(entry.getKey() == FilterProperties.IngoreCase) {
				filter.setIgnoreCase(entry.getValue());
			} else if(entry.getKey() == FilterProperties.Like) {
				filter.setLike(entry.getValue());
			}
		}
		return filter;
	}
}
