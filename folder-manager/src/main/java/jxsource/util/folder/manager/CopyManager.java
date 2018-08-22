package jxsource.util.folder.manager;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;

public class CopyManager extends Manager<SysFile>{

	public static ManagerBuilder<SysFile> builder() {
		ManagerBuilder<SysFile> builder =  new ManagerBuilder<SysFile>();
		try {
			builder.setFileFilter(FileFilterFactory.create(CopyFilter.class));
			builder.setWorkingDir("copy");
		} catch(Exception e) {
			throw new RuntimeException("Error when building "+CopyManager.class.getName(),e);
			
		}
		return builder;
	}
	public static void main(String...arg) {
			Manager<SysFile> manager = CopyManager.builder()
					.setEngine(new SysSearchEngine()).build();
			BackDirHolder.get().clear();
			manager.run("testdata/test-data/xyz");
			System.out.println(manager.getBackDir().get());
			System.out.println(manager.getEngine().getFilter());
	}

}
