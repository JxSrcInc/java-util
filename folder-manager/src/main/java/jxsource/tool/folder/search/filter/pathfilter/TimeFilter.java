package jxsource.tool.folder.search.filter.pathfilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.search.filter.Filter;

public class TimeFilter extends AbstractFilter{
	private static Logger log = LogManager.getLogger(TimeFilter.class);
    private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	private long start, end;
	public TimeFilter(String start) throws ParseException {
		this.start = convert(start);
	}
	public TimeFilter(String start, String end) throws ParseException {
		
		this.start = convert(start);
		this.end = convert(end);
	}
	
	public static long convert(String time) throws ParseException {
	    Date date = sdfDate.parse(time);
	    return date.getTime();
		
	}
	public int getStatus(JFile f) {
		if(f.isArray()) {
			return Filter.PASS;
		}
		log.debug(f.getPath()+", "+sdfDate.format(new Date(f.getLastModified())));
		if(f.getLastModified() >= start && (end == 0 || f.getLastModified() < end)) {
			return Filter.ACCEPT;
		} else {
			return Filter.REJECT;
		}
	}

}
