package jxsource.util.folder.compare;

public class CompareReportEngine {

	public void run(ComparableNode node) {
		for(ComparableNode child: node.getDiff()) {
			run(child);
		}
	}
}
