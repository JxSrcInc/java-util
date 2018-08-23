package jxsource.util.folder.search.filter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;

public class CopyFilterTest {
	
	@BeforeClass
	public static void init() {
		BackDirHolder.get().clear();
	}
	
	@Test
	public void test() {
		CopyFilter copyFilter = FileFilterFactory.create(CopyFilter.class);
		assertThat(copyFilter.delegateAccept(new SysFile(
				new File("testdata/test-data/xyz/java/Date.java"))), is(Filter.ACCEPT));
		File parent = new File("working-backup/testdata/test-data/xyz/java");
		boolean found = false;
		for(String child: parent.list()) {
			int i = child.lastIndexOf(".");
			if(child.substring(0,i).equals("Date.java")) {
				found = true;
				break;
			}
		}
		assert(found);
	}
}