package jxsource.tool.folder.compare;

import java.io.File;

import org.junit.Test;

import jxsource.tool.folder.compare.action.PrintCAction;
import jxsource.tool.folder.compare.comparator.PathDiffer;
import jxsource.tool.folder.file.JFile;
import jxsource.tool.folder.file.SysFile;

public class CompareEngineTest {

	@Test
	public void test() {
		CompareEngine engine = new CompareEngine(new PathDiffer(), new PrintCAction());
		JFile src = new SysFile(new File("./src"));
		JFile compareTo = new SysFile(new File("./src"));
		engine.compare(src, compareTo);
	}
}
