package jxsource.util.folder.search.filter.filefilter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.Filter;

public class SaveFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(SaveFilter.class);
	
	SaveFilter() {}
	@Override
	protected int delegateStatus(JFile file) {
		if(file instanceof SysFile) {
			Filter modifyFilter = getModifyFilter(this);
			if(modifyFilter != null) {
				String content = ((ModifyFilter)modifyFilter).getContent();
				String path = file.getPath();
				if(((ModifyFilter)modifyFilter).isChanged() && content != null) {
					log.debug("save content("+content.length()+") -> "+path);
					try {
						OutputStream out = new FileOutputStream(path);
						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
						writer.write(content);
						writer.flush();
						out.close();
						return Filter.ACCEPT;						
					} catch (Exception e) {
						log.error("Error when saving file: "+path, e);
						return Filter.REJECT;
					}
				} else {
					log.error("No content to save to file: "+path);
					return Filter.REJECT;					
				}
			} else {
				log.error("Cannot find ModifyFilter in parent filter chain.");
				return Filter.REJECT;
			}
		} else {
			log.error("Cannot save "+file.getClass().getName());
			return Filter.REJECT;
		}
	}
	/**
	 * Make public for mock in JUnit test
	 * @param filter
	 * @return
	 */
	public Filter getModifyFilter(Filter filter) {
		Filter before = filter.getBefore();
		if(before instanceof ModifyFilter) {
			return before;
		} else if(before == null) {
			return null;
		} else {
			return getModifyFilter(before);
		}
	}
}
