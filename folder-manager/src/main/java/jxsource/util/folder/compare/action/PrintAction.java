package jxsource.util.folder.compare.action;

import jxsource.util.folder.node.Node;

public class PrintAction implements Action
{
	public void proc(Node src, Node target) {
		System.out.println("PRintCAction: "+src);
		System.out.println("PRintCAction: "+target);
	}

}
