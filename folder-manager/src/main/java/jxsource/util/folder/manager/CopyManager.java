package jxsource.util.folder.manager;

import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;
import jxsource.util.folder.search.filter.filefilter.TempDir;

public class CopyManager extends Manager{

	public static class CopyManagerBuilder extends ManagerBuilder{
		// default working dir to "copy"
		TempDir copyDir = TempDir.builder().setSubWorkingDir("copy").build();
		@Override
		public Manager build() {
			try {
				super.s
				super.setFilter(FileFilterFactory.create(CopyFilter.class, copyDir));
				return super.build();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Error when building Manager for Copy", e);
			}
		}
		
	}
	public static ManagerBuilder builder() {
		ManagerBuilder builder =  new CopyManagerBuilder();
		return builder;
	}
	public static void main(String...arg) {
			Manager manager = CopyManager.builder().build();
			manager.run("testdata/test-data/xyz");
			System.out.println(manager.getTempDir().get());
			System.out.println(manager.getEngine().getFilter());
	}

}
