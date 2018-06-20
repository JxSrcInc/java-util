package jxsource.util.folder.search.filter;

import java.util.HashMap;

public class FilterProperties extends HashMap<Integer, Boolean> {
	public static final int IngoreCase = -1;
	public static final int Like = -2;

	private static final long serialVersionUID = 1L;
	public static FilterProperties setIgnoreCase(boolean value, FilterProperties...properties) {
		FilterProperties option = null;
		if(properties.length > 0) {
			option = properties[0];
		} else {
			option = new FilterProperties();
		}
		option.put(IngoreCase, value);
		return option;
	}
	public static FilterProperties setLike(boolean value, FilterProperties...properties) {
		FilterProperties option = null;
		if(properties.length > 0) {
			option = properties[0];
		} else {
			option = new FilterProperties();
		}
		option.put(Like, value);
		return option;
	}
	public FilterProperties set(Integer key, Boolean value) {
		put(key, value);
		return this;
	}
}
