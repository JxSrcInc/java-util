package jxsource.util.folder.compare.action;

import jxsource.util.folder.compare.ComparableNode;
import jxsource.util.folder.compare.util.Constants;
import jxsource.util.folder.node.JFile;

public class LenDiffReport extends BaseReport {

	@Override
	public void proc(ComparableNode comparableNode) {
		String diffType = comparableNode.getDiffType();
		if(diffType != null && diffType.equals(ComparableNode.LengthDiff)) {
			printer.printf(Constants.diffPrintFormat,
					comparableNode.getComparePath(),
					diffType,
					Constants.srcSymbol,
					((JFile)comparableNode.getSrc()).getLength(),
					Constants.cmpSymbol,
					((JFile)comparableNode.getToCompare()).getLength()
					);
		}
		
	}
	
}
