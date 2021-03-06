package jxsource.util.folder.compare.comparator;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

public class LengthDiffer extends LeafDiffer {

	@Override
	protected boolean isDiff(Node src, Node toCompare) {
		if(src instanceof JFile && toCompare instanceof JFile) {
			return ((JFile)src).getLength() != ((JFile)toCompare).getLength();
		} else {
			if(!(src instanceof JFile)) {
				throw new RuntimeException(src+" is not a JFile.");		
			} else {
				throw new RuntimeException(toCompare+" is not a JFile.");						
			}
		}
	}

}
