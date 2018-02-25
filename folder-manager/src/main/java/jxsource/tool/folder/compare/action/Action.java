package jxsource.tool.folder.compare.action;

import java.util.Map;
import java.util.Set;

import jxsource.tool.folder.compare.Result;
import jxsource.tool.folder.node.Node;

public interface Action {
	public void proc(Node src, Node target);
}
