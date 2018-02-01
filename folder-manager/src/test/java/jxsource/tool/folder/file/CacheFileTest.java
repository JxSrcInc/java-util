package jxsource.tool.folder.file;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import jxsource.tool.folder.search.util.Util;

public class CacheFileTest {
	@Test
	public void test() throws IOException {
		String path = "./test-data/resources/pom.xml";
		SysFile file = new SysFile(new File(path));
		CacheFile<SysFile> cache = new CacheFile<SysFile>(file);
		InputStream in = new FileInputStream(path);
		String content = Util.getContent(in);
		in.close();
		assertThat(content, is(new String(cache.getContent())));
	}
}
