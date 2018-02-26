package jxsource.util.folder.search.app;

import jxsource.tool.folder.search.filter.contentfilter.SimpleContentFilter;

public class ContentSearchApp extends SearchApp{

	String contentSearch;
	boolean word;
	@Override
	protected void init(String[] args) {
		super.init(args);
		for(String arg: args) {
			int i = arg.indexOf('=');
			if(i > 0) {
				String key = arg.substring(0,i).trim();
				String value = arg.substring(i+1).trim();
				switch(key) {
				case "ContentSearch":
					contentSearch = value;
					break;
				case "SearchByWork":
					word = value.toLowerCase().charAt(0)=='t'?true:false;
					break;
					default:
				}
			}
		}
		filter.setNext(new SimpleContentFilter(contentSearch).setWordMatch(word));
	}

	public static void main(String[] args) {
		ContentSearchApp app = new ContentSearchApp();
		app.init(args);
		app.run();
		System.out.println("complete");
		
	}
	
}
