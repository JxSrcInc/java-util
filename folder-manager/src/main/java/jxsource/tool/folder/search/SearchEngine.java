package jxsource.tool.folder.search;

import java.util.HashSet;
import java.util.Set;

import jxsource.tool.folder.file.JFile;
import jxsource.tool.folder.search.action.Action;
import jxsource.tool.folder.search.filter.Filter;

public abstract class SearchEngine {
	private Set<Action> actions = new HashSet<Action>();
	private Filter filter;

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public SearchEngine addAction(Action action) {
		actions.add(action);
		return this;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	/**
	 * check filter to determine if file should be
	 * 	1. accept: apply all actions. the JFile can be directory or file
	 * 		Action may work on only one type or both.
	 * 		it is Action's responsibility to determine whether the JFile is directory or file.
	 *  2. pass: the file is on valid path
	 *  3. reject: the file is not on valid path
	 * @param file
	 * @return true to allow search engine to process its children
	 * 		false inform search engine to stop process its children
	 */
	protected int consum(JFile file) {
		int status = Filter.ACCEPT;
		if(filter != null) {
			status = filter.accept(file);
		}
		if(status == Filter.ACCEPT) {
			for (Action action : actions) {
			action.proc(file);
			}	
		}
		return status;
	}

}
