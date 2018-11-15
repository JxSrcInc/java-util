package jxsource.util.folder.search.zip;

import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.search.zip.ZipSearchTemplate.ZipSearchTemplateBuilder;

public class ZipSearchTemplateTreeFactoryTest {
	ZipReportCollection zipReportAssert;
	ZipSearchTemplateBuilder builder;
	
	@Before
	public void init() {
		zipReportAssert = new ZipReportCollection();
		builder = ZipSearchTemplate.getBuilder();
	}
	@Test
	public void defaultTemplateTest() {
		ZipSearchTemplate zst = builder
				.setZipReport(new TreeZipReport())
				.build();
		zst.search();
		// TODO; finish test
	}

}
