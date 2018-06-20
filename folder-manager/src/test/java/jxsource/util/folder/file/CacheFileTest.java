package jxsource.util.folder.file;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import jxsource.util.folder.node.CacheFile;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.util.Util;

public class CacheFileTest {
	@Test
//	@Ignore
	public void test() throws IOException {
		String path = "./test-data/resources/pom.xml";
		SysFile file = new SysFile(new File(path));
		CacheFile cache = new CacheFile(file);
		InputStream in = new FileInputStream(path);
		String content = Util.getContent(in);
		in.close();
		assertThat(content, is(new String(cache.getContent())));
	}
}
