package jxsource.util.folder.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class RestoreManager {
	public static void main(String...args) {
		File backDir = new File("working-backup/modify");
		File restoreDir = new File("restore");
		try {
			new RestoreManager().restore(backDir, restoreDir);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void restore(File backup, File restore) throws IOException {
		System.out.println("restore "+backup+" to "+restore);
		for(File child: backup.listFiles()) {
			File restoreChild = new File(restore, child.getName());
			Files.copy
			(Paths.get(child.getPath()), 
			Paths.get(restoreChild.getPath()),
			StandardCopyOption.REPLACE_EXISTING);
			if(child.isDirectory()) {
				restore(child, restoreChild);
			}
		}
	}
}
