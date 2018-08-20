package jxsource.util.folder.search.filter.leaffilter;

import java.util.HashMap;
import java.util.Map;

import jxsource.util.folder.search.filter.AbstractFilter;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.util.StringMatcher;
import jxsource.util.folder.search.util.Util;

public class LeafFilterFactory {
	
	public static final String Ext = "Ext";
	public static final String Name = "Name";
	public static final String FullName = "FullName";
	public static final String Time = "Time";
	public static final String Zip = "Zip";
	public static final String Content = "Content";

	public static Filter createZipFilter(HashMap<Integer, Boolean>...filterProperties) {
		if(filterProperties.length == 0) {
			return create(Zip, Util.archiveTypes);
		} else {
			return create(Zip, Util.archiveTypes, filterProperties[0]);			
		}
	}
	
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
		case Zip:
			filter = new ZipFilter();
			break;
		case Content:
			filter = new ContentFilter(value);
			break;
			default:
				throw new RuntimeException("Invalid Filter type: "+type);
		}
		StringMatcher stringMatcher = new StringMatcher();
		stringMatcher.add(value);
		for(Map.Entry<Integer, Boolean> entry: option.entrySet()) {
			if(entry.getKey() == FilterProperties.IngoreCase) {
				stringMatcher.setIgnoreCase(entry.getValue());
			} else if(entry.getKey() == FilterProperties.Like) {
				stringMatcher.setLike(entry.getValue());
			} else if(entry.getKey() == FilterProperties.Exclude) {
				filter.setExclude(entry.getValue());
			}
		}
		filter.setStringMatcher(stringMatcher);
		return filter;
	}
}
