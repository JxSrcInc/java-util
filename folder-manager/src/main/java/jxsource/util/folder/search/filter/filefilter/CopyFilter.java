package jxsource.util.folder.search.filter.filefilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.search.filter.Filter;

public class CopyFilter extends FileFilter{
	@Override
	protected int delegateStatus(JFile file) {
		File moveTo = getManager().getTempDir().createTempFile(file);
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
			e.printStackTrace();
			return Filter.REJECT;
		}
 
	}

}
