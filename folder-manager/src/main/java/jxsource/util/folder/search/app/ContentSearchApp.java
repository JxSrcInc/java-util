package jxsource.util.folder.search.app;

import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.contentfilter.SimpleContentFilter;
 
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
                Filter tmpFilter = new SimpleContentFilter(contentSearch).setWordMatch(word);
                if(workingFilter == null) {
                        filter = workingFilter = tmpFilter;
                } else {
                        workingFilter.setNext(tmpFilter);
                }
        }
 
        public static void main(String[] args) {
                ContentSearchApp app = new ContentSearchApp();
                app.init(args);
                app.run();
                System.out.println("complete");
                
        }
        
}