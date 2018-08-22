package jxsource.util.folder.search.filter.filefilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.util.Util;

public class ModifyFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(ModifyFilter.class);
	private String replacement;
	private String content;
	private Pattern p;
	private boolean changed;
	
	
	public String getReplacement() {
		return replacement;
	}

	public ModifyFilter setReplacement(String replacement) {
		this.replacement = replacement;
		return this;
	}

	public String getContent() {
		return content;
	}

	public ModifyFilter setRegex(String regex) {
		p = Pattern.compile(regex);
		return this;
	}

	public boolean isChanged() {
		return changed;
	}

	@Override
	protected int delegateStatus(JFile file) {
		try {
			InputStream in = file.getInputStream();
			content = Util.getContent(in).toString();
			changed =p.matcher(content).find();
			if(changed) {
				content = content.replaceAll(p.pattern(), replacement);
			}
	        return Filter.ACCEPT;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error when delegating in "+getClass().getName(), e);;
			return Filter.REJECT;
		}
 
	}

}
