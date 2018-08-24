package jxsource.util.folder.manager;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SearchEngine;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;
import jxsource.util.folder.search.filter.filefilter.ModifyFilter;
import jxsource.util.folder.search.filter.filefilter.SaveFilter;
import jxsource.util.folder.search.filter.leaffilter.ExtFilter;
import jxsource.util.folder.search.filter.leaffilter.FilterProperties;
import jxsource.util.folder.search.filter.leaffilter.LeafFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;

public class ModifyManager extends Manager<SysFile>{

	public static class ModifyManagerBuilder extends ManagerBuilder<SysFile>{
		private String regex;
		private String replacement;
		
		public ModifyManagerBuilder setRegex(String regex) {
			this.regex = regex;
			return this;
		}
		public ModifyManagerBuilder setReplacement(String replacement) {
			this.replacement = replacement;
			return this;
		}
		public ModifyManagerBuilder setEngine(SearchEngine engine) {
			super.setEngine(engine);
			return this;
		}
		public ModifyManager build() {
			ModifyFilter modifyFilter = FileFilterFactory.create(ModifyFilter.class);
			CopyFilter copyFilter = FileFilterFactory.create(CopyFilter.class);
			modifyFilter.setRegex(regex);
			modifyFilter.setReplacement(replacement);
			SaveFilter saveFilter = FileFilterFactory.create(SaveFilter.class);
			modifyFilter.setNext(copyFilter);
			copyFilter.setBefore(modifyFilter);
			copyFilter.setNext(saveFilter);
			saveFilter.setBefore(copyFilter);
			if(filter == null) {
				filter = modifyFilter;
			} else {
				filter.setNext(modifyFilter);
			}
			setWorkingDir("modify");
			if(engine == null) {
				setEngine(new SysSearchEngine());
			}
			return super.build(ModifyManager.class);
		}
	}
	public static ModifyManagerBuilder builder() {
		return new ModifyManagerBuilder();
	}
	public static void main(String...arg) {
		SysSearchEngine engine = new SysSearchEngine();
		FilterProperties pros = FilterProperties.setExclude(true);
		LeafFilter filter = LeafFilterFactory.create(LeafFilterFactory.Ext, "log", pros);
		engine.setFilter(filter);
		ModifyManagerBuilder builder = ModifyManager.builder();
		ModifyManager manager = builder
				.setRegex("content")
				.setReplacement("updated content")
				.setEngine(engine)
				.build();
		manager.run("testdata/test-save");
		System.out.println(manager.getBackDir().get());
		System.out.println("... completed");
	}

}
