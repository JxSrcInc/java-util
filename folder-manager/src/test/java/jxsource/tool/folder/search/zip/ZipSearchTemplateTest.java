package jxsource.tool.folder.search.zip;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;
import jxsource.tool.folder.search.filter.pathfilter.FullNameFilter;
import jxsource.tool.folder.search.filter.pathfilter.NameFilter;
import jxsource.tool.folder.search.filter.pathfilter.PathFilter;
import jxsource.tool.folder.search.filter.pathfilter.TimeFilter;
import jxsource.tool.folder.search.zip.ZipSearchTemplate;
import jxsource.tool.folder.search.zip.ZipSearchTemplate.ZipSearchTemplateBuilder;

public class ZipSearchTemplateTest {
	private Logger log = LogManager.getLogger(ZipSearchTemplateTest.class);
	AssertZipReport zipReportAssert;
	ZipSearchTemplateBuilder builder;
	
	@Before
	public void init() {
		zipReportAssert = new AssertZipReport();
		builder = ZipSearchTemplate.getBuilder();
	}
	@Test
	public void builderTest() {
		assertNotNull(ZipSearchTemplate.getBuilder());
	}
	
	@Test
	public void defaultTemplateTest() {
		ZipSearchTemplate zst = builder
				.setZipReport(zipReportAssert).build();
		zst.search();
	}
	
	@Test
	public void extFilterTest() {
		ZipSearchTemplate zst = builder
			.setZipFilter(new ExtFilter("class"))
			.setZipReport(zipReportAssert.setExt("class"))
			.build();
		zst.search();		
	}
	@Test
	public void fullnameFilterTest() {
		ZipSearchTemplate zst = builder
				.setZipFilter(new FullNameFilter().add("Filter.class"))
				.setZipReport(zipReportAssert.setName("Filter")) // ZipReportAssert removes extension from name 
				.build();
			zst.search();		
	}
	@Test
	public void nameFilterTest() {
		ZipSearchTemplate zst = builder
				.setZipFilter(new NameFilter().add("Filter"))
				.setZipReport(zipReportAssert.setName("Filter"))
				.build();
			zst.search();		
	}
	@Test
	public void ignoreCaseNameFilterTest() {
		ZipSearchTemplate zst = builder
				.setZipFilter(new NameFilter().add("filter").setIgnoreCase(true))
				.setZipReport(zipReportAssert.setName("Filter"))
				.build();
			zst.search();		
	}	
	@Test
	public void likeNameFilterTest() {
		ZipSearchTemplate zst = builder
				.setZipFilter(new NameFilter().add("Da").setLike(true))
				.setZipReport(zipReportAssert.setName("Data"))
				.build();
			zst.search();		
		log.debug(zipReportAssert.getFound());
		// found more than checked.
		assertThat(zipReportAssert.getFound().size(), greaterThanOrEqualTo(2));
	}	
	@Test
	public void pathAndNameFilterTest() {
		Filter filter = new PathFilter("test-data");
		filter.setNext(new NameFilter().add("Data"));
		ZipSearchTemplate zst = builder
				.setZipFilter(filter)
				.setZipReport(zipReportAssert.setName("Data"))
				.build();
			zst.search();		
		log.debug(zipReportAssert.getFoundFiles());
		assertThat(zipReportAssert.getFoundFiles().size(), greaterThanOrEqualTo(2));
	}		
	@Test
	public void pathFilterTest() {
		Filter filter = new PathFilter("**/main");
		ZipSearchTemplate zst = builder
				.setZipFilter(filter)
				.setZipReport(zipReportAssert)
				.build();
			zst.search();		
		log.debug(zipReportAssert.getFoundFiles());
		assertThat(zipReportAssert.getFoundFiles().size(), is(1));
	}		
	@Test
	public void timeFilterTest() throws ParseException {
		ZipSearchTemplate zst = builder
				.setZipFilter(new TimeFilter("2001-01-01 00:00:00", "2030-01-01 00:00:00"))
				.setZipReport(zipReportAssert.setStart(TimeFilter.convert("2001-01-01 00:00:00")))
				.build();
			zst.search();		
		log.debug(zipReportAssert.getFound().size());
		// found more than checked.
		assertThat(zipReportAssert.getFound().size(), greaterThanOrEqualTo(0));
		
	}
}
