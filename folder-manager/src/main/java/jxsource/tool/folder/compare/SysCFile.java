package jxsource.tool.folder.compare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxsource.tool.folder.file.SysFile;

/**
 * Lazy child load
 *
 */
public class SysCFile extends SysFile implements CFile{
	File file;
	List<CFile> files;
	public SysCFile(File file) {
		super(file);
		this.file = file;
	}
	public List<CFile> listFiles() {
		if(files == null) {
			// for lazy child load
			files = new ArrayList<CFile>();
			for(File f: file.listFiles()) {
				files.add(new SysCFile(f));
			}
		}
		return files; 
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysCFile other = (SysCFile) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}

}
