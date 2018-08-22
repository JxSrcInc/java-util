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
import jxsource.util.folder.search.filter.Filter;

public class CopyFilter extends FileFilter{
	private static Logger log = LogManager.getLogger(CopyFilter.class);
	@Override
	protected int delegateStatus(JFile file) {
		File moveTo = BackDirHolder.get().createTempFile(file);
        Path temp;
		try {
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
			// TODO Auto-generated catch block
			log.error("Error when delegating in "+getClass().getName(), e);;
			return Filter.REJECT;
		}
 
	}

}
