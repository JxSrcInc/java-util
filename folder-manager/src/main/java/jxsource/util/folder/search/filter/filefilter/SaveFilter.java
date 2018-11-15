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

/**
 * If there is a ModifyFilter before this Filter in Filter chain
 * and content changes, save the modified content to its original file.
 * 
 * See jxsource.util.folder.manager.ModifyManager for how to use
 *
 */
public class SaveFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(SaveFilter.class);
	
	SaveFilter() {}
	@Override
	protected int delegateStatus(JFile file) {
		if(file instanceof SysFile) {
			Filter modifyFilter = getModifyFilterFromChainBeforeThis(this);
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
	 * Find ModifyFilter from all Filters in Filter chain before this.
	 * It is recursive search until no before filter.
	 * 
	 * @param filter - start filter
	 * @return ModifyFilter or null
	 */
	public Filter getModifyFilterFromChainBeforeThis(Filter filter) {
		Filter before = filter.getBefore();
		if(before instanceof ModifyFilter) {
			return before;
		} else if(before == null) {
			return null;
		} else {
			return getModifyFilterFromChainBeforeThis(before);
		}
	}
}
