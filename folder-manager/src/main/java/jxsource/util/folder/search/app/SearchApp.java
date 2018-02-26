package jxsource.util.folder.search.app;

import java.io.File;
import java.text.ParseException;

import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.action.CollectionAction;
import jxsource.tool.folder.search.action.FilePrintAction;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.FilterFactory;
import jxsource.tool.folder.search.filter.FilterProperties;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;
import jxsource.tool.folder.search.filter.pathfilter.TimeFilter;
import jxsource.tool.folder.search.zip.AssertZipReport;
import jxsource.tool.folder.search.zip.ZipReportPrinter;
import jxsource.tool.folder.search.zip.ZipSearchTemplate;

public class SearchApp {
	private String root;
	private String filterType;
	private String filterValue;
	private boolean like;
	protected Filter filter;

	protected void init(String[] args) {
		for(String arg: args) {
			int i = arg.indexOf('=');
			if(i > 0) {
				String key = arg.substring(0,i).trim();
				String value = arg.substring(i+1).trim();
				switch(key) {
				case "Root":
					setRoot(value);
					break;
				case "FilterType":
					setFilterType(value);
					break;
				case "FilterValue":
					setFilterValue(value);
					break;
				case "FilterLike":
					setFilterLike(value);
					break;
					default:
//						System.out.println("Parameters:\n"+ 
//					"\tRoot=<Root value>\n"+
//					"\tFilterLike=<true|false>\n"+
//					"\tFilterType=<FilterType value>\n"+
//					"\tFilterValue=<FilterValue value>\n");
				}
			}
		}

		FilterProperties filterProps = new FilterProperties();
		if(like) {
			filterProps.set(FilterProperties.Like, true);
		}
		filter = FilterFactory.create(filterType, filterValue, filterProps);
	}
	public void run() {
		System.out.println("Search: "+root);
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new FilePrintAction());
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
			engin.setFilter(filter);
			engin.search(new File(root));
			System.out.println("searched "+engin.getSearchedCount());
			System.out.println("find "+ca.getNodes().size());
	}
	public SearchApp setRoot(String root) {
		this.root = root;
		return this;
	}
	public SearchApp setFilterType(String type) {
		filterType = type;
		return this;
	}
	public SearchApp setFilterValue(String value) {
		filterValue = value;
		return this;
	}
	public SearchApp setFilterLike(String...like) {
		if(like.length == 0) {
			this.like = false;
		} else {
			this.like = like[0].toLowerCase().charAt(0)=='t'?true:false;
		}
		return this;
	}
	public static void main(String[] args) {
		SearchApp app = new SearchApp();
		app.init(args);
		app.run();
		System.out.println("complete");
		
	}

}
