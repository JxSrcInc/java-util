package jxsource.util.folder.search.app;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.action.FilePrintAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;
import jxsource.util.folder.search.filter.leaffilter.FilterProperties;

public class SearchApp {
	private static Logger log = LogManager.getLogger(SearchApp.class);
	private String root;
	protected Filter filter;
	protected Filter workingFilter = null;

	private void parseFilter(String entry) {
		// filters
		// filter attributes
		String type = null;
		String value = null;
		FilterProperties props = new FilterProperties();
		String[] attrs = entry.split(";");
		for (int i = 0; i < attrs.length; i++) {
			final String[] val = attrs[i].split("@");
			if (val.length != 2) {
				throw new RuntimeException("Invalid Filter: " + entry + " -> " + attrs[i]);
			}
			switch (val[0].toLowerCase()) {
			case "type":
				type = val[1];
				break;
			case "value":
				value = val[1];
				break;
			case "like":
				props.set(FilterProperties.Like, val[1].toLowerCase().charAt(0) == 't' ? true : false);
				break;
			case "ignore":
				props.set(FilterProperties.IngoreCase, val[1].toLowerCase().charAt(0) == 't' ? true : false);
				break;
			default:
				throw new RuntimeException("Invalid Filter: " + entry + "@" + attrs[i]);
			}
		}
		if (type == null || value == null) {
			throw new RuntimeException("Invalid Filter: " + entry);
		}
		log.debug("type=" + type + ", value=" + value + ", props=" + props);
		Filter tmpFilter = LeafFilterFactory.create(type, value, props);
		if (filter == null) {
			filter = tmpFilter;
		} else {
			workingFilter.setNext(tmpFilter);
		}
		workingFilter = tmpFilter;
	}

	protected void init(String[] args) {
		for (String arg : args) {
			int i = arg.indexOf('=');
			if (i > 0) {
				String key = arg.substring(0, i).trim();
				String value = arg.substring(i + 1).trim();
				switch (key) {
				case "Root":
					setRoot(value);
					break;
				case "Filter":
					parseFilter(value);
					break;
				default:
					log.warn("unprocessed arg: " + arg);
				}
			}
		}
	}

	public void run() {
		log.info("Search: " + root);
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new FilePrintAction());
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(filter);
		engin.search(new File(root));
		log.info("searched " + engin.getSearchedCount());
		log.info("find " + ca.getNodes().size());
	}

	public SearchApp setRoot(String root) {
		this.root = root;
		return this;
	}

	public static void main(String[] args) {
		SearchApp app = new SearchApp();
		app.init(args);
		app.run();
		System.out.println("complete");

	}

}