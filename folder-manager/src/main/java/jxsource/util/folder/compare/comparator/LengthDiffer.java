package jxsource.util.folder.compare.comparator;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

public class LengthDiffer extends Differ {

	@Override
	protected boolean isDiff(Node src, Node compareTo) {
		if (src.getChildren().size() == 0 && compareTo.getChildren().size() == 0) {
			if(src instanceof JFile && compareTo instanceof JFile) {
				return ((JFile)src).getLength() != ((JFile)compareTo).getLength();
			}
		}
		return false;
	}

}
