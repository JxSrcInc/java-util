package jxsource.tool.folder.search.zip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import jxsource.tool.folder.search.zip.ZipSearchTemplate.ZipSearchTemplateBuilder;

public class ZipSearchTemplateTreeFactoryTest {
	private Logger log = LogManager.getLogger(ZipSearchTemplateTreeFactoryTest.class);
	ZipReportAssert zipReportAssert;
	ZipSearchTemplateBuilder builder;
	
	@Before
	public void init() {
		zipReportAssert = new ZipReportAssert();
		builder = ZipSearchTemplate.getBuilder().buildTree(true);
	}
	@Test
	public void defaultTemplateTest() {
		ZipSearchTemplate zst = builder
//				.setZipReport(new ZipReportPrinter())
				.build();
		zst.search();
	}

}
