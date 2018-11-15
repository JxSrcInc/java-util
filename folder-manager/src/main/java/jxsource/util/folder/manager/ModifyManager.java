package jxsource.util.folder.manager;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;
import jxsource.util.folder.search.filter.filefilter.ModifyFilter;
import jxsource.util.folder.search.filter.filefilter.SaveFilter;
import jxsource.util.folder.search.filter.leaffilter.FilterProperties;
import jxsource.util.folder.search.filter.leaffilter.LeafFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;
import jxsource.util.folder.search.util.RegexMatcher;

/**
 * ModifyManager creates a Filter chain: 
 * 1. ModifyFilter - if content matchs RegexMatcher, modify content.
 * 2. CopyFilter - if the file is modified, copy file original content
 *                 from system to BackDir
 * 3. SaveFilter - if the file is modified, save modified file content 
 *                 back to the original file.
 *                 
 * Note: because the three filters are in above order in filter chain,
 * CopyFilter and SaveFilter will invoke only if ModifyFilter accepts the file.
 * 
 * You can add other filters before ModifyFilter to get better performance.
 */
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
		public ModifyManagerBuilder setEngine(SysSearchEngine engine) {
			super.setEngine(engine);
			return this;
		}
		public ModifyManager build() {
			ModifyFilter modifyFilter = FileFilterFactory.create(ModifyFilter.class);
			RegexMatcher matcher = RegexMatcher.builder().build(regex);
			modifyFilter.setRegexMatcher(matcher);
			modifyFilter.setReplacement(replacement);
			
			CopyFilter copyFilter = FileFilterFactory.create(CopyFilter.class);
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
