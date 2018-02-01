package jxsource.tool.folder.compare;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import jxsource.tool.folder.compare.Result.Bind;
import jxsource.tool.folder.compare.action.CAction;
import jxsource.tool.folder.compare.comparator.Differ;

public class CompareEngine {
	private static Logger log = LogManager.getLogger(CompareEngine.class);
	private Differ differ;
	private CAction action;
	private Comparator<CFile> comparator = (CFile o1, CFile o2) -> o1.getPath().compareTo(o2.getPath());

	public CompareEngine(Differ differ, CAction action) {
		this.differ = differ;
		this.action = action;
	}
	private void fire(CFile src, CFile compareTo) {
		if(action != null) {
			action.proc(src, compareTo);			
		}
	}
	// recursive function
	public void compare(CFile src, CFile compareTo) {
		log.debug("compare\n\tsrc = "+ src+"\n\tcompareTo = "+compareTo);

		if(differ.diff(src, compareTo)) {
			// include cases one file and one directory
			// take action
			fire(src, compareTo);
			// no more further process
		} else {
			if(src.isDirectory() && compareTo.isDirectory()) {
				// both are directories, compare children
				Result result = compareNodes(src, compareTo);
				Iterator<Bind> diff = result.getDiff().iterator();
				while(diff.hasNext()) {
					Bind child = diff.next();
					fire(child.src, child.compareTo);
				}
				Iterator<CFile> missing = result.getMissing().iterator();
				while(missing.hasNext()) {
					fire(missing.next(), null);
				}
				Iterator<CFile> extra = result.getMissing().iterator();
				while(extra.hasNext()) {
					fire(null, extra.next());
				}
				Iterator<Bind> same = result.getSame().iterator();
				while(same.hasNext()) {
					Bind child = same.next();
					// recursive call
					compare(child.src, child.compareTo);
				}
			} else {
				// both are files, do nothing
			}
		}
	}
	public Result compareNodes(CFile src, CFile compareTo) {
		List<CFile> sList = src.listFiles();
		Collections.sort(sList, comparator);
		List<CFile> cList = compareTo.listFiles();
		Collections.sort(cList, comparator);
		Result r = new Result();
		Iterator<CFile> sIter = sList.iterator();
		Iterator<CFile> cIter = cList.iterator();
		CFile sChild = null; 
		CFile cChild = null;
		if(sIter.hasNext()) {
			sChild = sIter.next();
		}
		if(cIter.hasNext()) {
			cChild = cIter.next();
		}
		return recursiveCall(sIter, cIter, sChild, cChild, r);
	}
	
	public Result recursiveCall(Iterator<CFile> sIter,
			Iterator<CFile> cIter,
			CFile sChild,
			CFile cChild,
			Result r) {
		if(sChild == null && cChild == null) {
			// complete
			return r;
		} else
		if(sChild == null) {
			// end src
			while(cIter.hasNext()) {
				r.addExtra(cIter.next());
			}
			return r;
		} else 
		if(cChild == null) {
			// end compareTo
			while(sIter.hasNext()) {
				r.addMissing(sIter.next());
			}
			return r;
		} else {
			Bind bind = new Result().new Bind(sChild, cChild);
			if(differ.diff(sChild, cChild)) {
				r.addDiff(bind);
			} else {
				r.addSame(bind);
			}
			CFile sNext = null;
			if(sIter.hasNext()) sNext = sIter.next();
			CFile cNext = null;
			if(cIter.hasNext()) cNext = cIter.next();
			Result _r = recursiveCall(sIter, cIter, sNext, cNext, r);
			return _r;
		}
		
	}
}
