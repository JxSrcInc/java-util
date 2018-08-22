package jxsource.util.folder.compare.action;

import java.util.Set;

import jxsource.util.folder.compare.ComparableNode;
import jxsource.util.folder.compare.util.Constants;
import jxsource.util.folder.node.Node;

public class AdditionReport extends BaseReport {

	@Override
	public void proc(ComparableNode comparableNode) {
		Set<Node> nodes = comparableNode.getExtra();
		if (nodes.size() != 0) {
			String value = "";
			for (Node node : nodes) {
				value += node.getName() + ", ";
			}
			printer.printf(Constants.arrayPrintFormat, 
					comparableNode.getComparePath(), 
					Constants.srcSymbol + ".addition",
					value.substring(0, value.length() - 2));
		}
	}

}
