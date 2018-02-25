package jxsource.tool.folder.compare.action;

import java.util.Map;
import java.util.Set;

import jxsource.tool.folder.compare.Result;
import jxsource.tool.folder.file.Node;

public interface CAction {
	public void proc(Node src, Node target);
}
