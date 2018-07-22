package jxsource.util.folder.compare.action;

import jxsource.util.folder.compare.ComparableNode;
import jxsource.util.folder.node.Node;

public class PrintAction implements Action
{
	public void proc(ComparableNode node) {
		Node src = node.getSrc();
		Node target = node.getToCompare();
		System.out.println("PrintCAction: "+src);
		System.out.println("PrintCAction: "+target);
	}

}
