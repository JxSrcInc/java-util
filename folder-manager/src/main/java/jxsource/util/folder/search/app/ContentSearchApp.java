package jxsource.util.folder.search.app;

import java.util.List;
import java.util.Map;

import jxsource.util.folder.search.filter.leaffilter.ContentFilter;
import jxsource.util.folder.search.util.RegexMatcher;

public class ContentSearchApp extends SearchApp {

	private ContentFilter contentFilter;

	String contentSearch;
	boolean word;

	public Map<String, List<String>> getMatchDisplay() {
		return contentFilter.getMatchDisplay();
	}
	@Override
	protected void init(String[] args) {
		super.init(args);
		for (String arg : args) {
			int i = arg.indexOf('=');
			if (i > 0) {
				String key = arg.substring(0, i).trim();
				String value = arg.substring(i + 1).trim();
				switch (key) {
				case "ContentSearch":
					contentSearch = value;
					break;
				case "SearchByWord":
					word = value.toLowerCase().charAt(0) == 't' ? true : false;
					break;
				default:
				}
			}
		}
//		contentFilter = (ContentFilter) LeafFilterFactory.create(LeafFilterFactory.Content, contentSearch);
//		contentFilter.setWordMatch(word);
		RegexMatcher matcher = RegexMatcher.builder()
				.setWord(word)
				.build(contentSearch);
		contentFilter = ContentFilter.builder().setRegexMatcher(matcher).build();
		if (workingFilter == null) {
			filter = workingFilter = contentFilter;
		} else {
			workingFilter.setNext(contentFilter);
		}
	}

	public static void main(String[] args) {
		ContentSearchApp app = new ContentSearchApp();
		app.init(args);
		app.run();
		for(Map.Entry<String, List<String>> entry: app.getMatchDisplay().entrySet()) {
			System.out.println(entry.getKey());
			for(String value: entry.getValue()) {
				System.out.println('\t'+value);
			}
		}
		System.out.println("complete");

	}

}