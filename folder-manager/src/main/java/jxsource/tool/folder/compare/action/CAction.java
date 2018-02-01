package jxsource.tool.folder.compare.action;

import java.util.Map;
import java.util.Set;

import jxsource.tool.folder.compare.CFile;
import jxsource.tool.folder.compare.Result;

public interface CAction {
	public void proc(CFile src, CFile target);
}
