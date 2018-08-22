package jxsource.util.folder.search;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.action.Action;
import jxsource.util.folder.search.filter.Filter;

public abstract class SearchEngine<T extends Node> {
	static Logger log = LogManager.getLogger(SearchEngine.class);
	private Set<Action<T>> actions = new HashSet<Action<T>>();
	private Filter filter;
	private int count;

	public abstract void search(File file);
	public int getSearchedCount() {
		return count;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	public Filter getFilter() {
		return filter;
	}
	public SearchEngine<T> addAction(Action<T> action) {
		actions.add(action);
		return this;
	}

	public void setActions(Set<Action<T>> actions) {
		this.actions = actions;
	}
	public Set<Action<T>> getActions() {
		return actions;
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
	protected int consume(T file) {
		int status = Filter.ACCEPT;
		if(filter != null) {
			status = filter.accept(file);
		}
		if(file.isDir()) {
			count++;			
		}
		log.debug(Filter.getStatusName(status)+", "+file.getPath());
		if(status == Filter.ACCEPT) {
			for (Action<T> action : actions) {
			action.proc(file);
			}	
		}
		return status;
	}

}
