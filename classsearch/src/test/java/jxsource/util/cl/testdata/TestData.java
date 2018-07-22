package jxsource.util.cl.testdata;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class TestData extends BaseClass implements Test{
	File file;
	public String getPath(List<String> s) {
		return file.getPath();
	}
	public InputStream get(int b, File file, String...s) {
		return new ByteArrayInputStream(new byte[1024*8]);
	}
	public void set(List<Boolean> b, boolean s, float[] f) {}
	public void set(Super obj) {}
}
