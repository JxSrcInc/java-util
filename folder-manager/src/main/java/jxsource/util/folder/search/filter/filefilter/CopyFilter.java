package jxsource.util.folder.search.filter.filefilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.ZipFile;
import jxsource.util.folder.search.filter.Filter;

/**
 * 
 * Copy file from system to BackDir
 *
 */
public class CopyFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(CopyFilter.class);
	
	CopyFilter() {}
	@Override
	protected int delegateStatus(JFile file) {
		if(file instanceof ZipFile) {
			log.warn("Not support ZipFile copy");
			return REJECT;
		}
		File moveTo = BackDirHolder.get().createTempFile(file);
		log.debug("copy "+file.getPath()+" to "+moveTo.getPath());
        Path temp;
		try {
			// copy file from system to BackDir using Files.copy in java.nio.file package
			temp = Files.copy
			(Paths.get(file.getPath()), 
			Paths.get(moveTo.getPath()),
			StandardCopyOption.REPLACE_EXISTING);
			
	        if(temp != null)
	        {
	            return Filter.ACCEPT;
	        }
	        else
	        {
	            return Filter.REJECT;
	        }
		} catch (IOException e) {
			log.error("Error when delegating in "+getClass().getName(), e);;
			return Filter.REJECT;
		}
 
	}

}
