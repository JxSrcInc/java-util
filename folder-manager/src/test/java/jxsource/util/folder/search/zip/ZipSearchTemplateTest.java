package jxsource.util.folder.search.zip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.leaffilter.FilterProperties;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;
import jxsource.util.folder.search.filter.leaffilter.TimeFilter;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;
import jxsource.util.folder.search.zip.ZipSearchTemplate.ZipSearchTemplateBuilder;

public class ZipSearchTemplateTest {
	private Logger log = LogManager.getLogger(ZipSearchTemplateTest.class);
	ZipReportCollection zipReportAssert;
	ZipSearchTemplateBuilder builder;
	
	@Before
	public void init() {
		zipReportAssert = new ZipReportCollection();
		builder = ZipSearchTemplate.getBuilder();
	}
	@Test
	public void pathFilterTest() {
		Filter filter = new PathFilter("**/main");
		ZipSearchTemplate zst = builder
				.setZipFilter(filter)
				.setZipReport(zipReportAssert)
				.build();
			zst.search();		
		assertThat(zipReportAssert.getFoundFiles().size(), is(1));
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
			.setZipFilter(LeafFilterFactory.create(LeafFilterFactory.Ext, "class"))
			.setZipReport(zipReportAssert.setExt("class"))
			.build();
		zst.search();		
	}
	@Test
	public void fullnameFilterTest() {
		ZipSearchTemplate zst = builder
				.setZipFilter(LeafFilterFactory.create(LeafFilterFactory.FullName, "Filter.class"))
				.setZipReport(zipReportAssert.setName("Filter")) // ZipReportAssert removes extension from name 
				.build();
			zst.search();		
	}
	@Test
	public void nameFilterTest() {
		ZipSearchTemplate zst = builder
				.setZipFilter(LeafFilterFactory.create(LeafFilterFactory.Name, "Filter"))
				.setZipReport(zipReportAssert.setName("Filter"))
				.build();
			zst.search();		
	}
	@Test
	public void ignoreCaseNameFilterTest() {
		Filter filter = LeafFilterFactory.create(LeafFilterFactory.Name, "filter", FilterProperties.setIgnoreCase(true));
		ZipSearchTemplate zst = builder
				.setZipFilter(filter)
				.setZipReport(zipReportAssert.setName("Filter"))
				.build();
			zst.search();		
	}	
	@Test
	public void likeNameFilterTest() {
		Filter filter = LeafFilterFactory.create(LeafFilterFactory.Name, "Da", FilterProperties.setLike(true));
		ZipSearchTemplate zst = builder
				.setZipFilter(filter)
				.setZipReport(zipReportAssert.setName("Data, Date"))
				.build();
			zst.search();		
		log.debug(zipReportAssert.getFound());
		// found more than checked.
		assertThat(zipReportAssert.getFound().size(), greaterThanOrEqualTo(2));
	}	
	@Test
	public void pathAndNameFilterTest() {
		Filter filter = new PathFilter("test-data");
		filter.setNext(LeafFilterFactory.create(LeafFilterFactory.Name, "Data"));
		ZipSearchTemplate zst = builder
				.setZipFilter(filter)
				.setZipReport(zipReportAssert.setName("Data"))
				.build();
			zst.search();		
		log.debug(zipReportAssert.getFoundFiles());
		assertThat(zipReportAssert.getFoundFiles().size(), greaterThanOrEqualTo(2));
	}		
	@Test
	public void timeFilterTest() throws ParseException {
		ZipSearchTemplate zst = builder
				.setZipFilter(LeafFilterFactory.create(LeafFilterFactory.Time, "2001-01-01 00:00:00,2030-01-01 00:00:00"))
				.setZipReport(zipReportAssert.setStart(TimeFilter.convert("2001-01-01 00:00:00")))
				.build();
			zst.search();		
		log.debug(zipReportAssert.getFound().size());
		// found more than checked.
		assertThat(zipReportAssert.getFound().size(), greaterThanOrEqualTo(0));
		
	}
}
