package jxsource.tool.folder.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XFile extends AbstractJFile{

	public XFile(char fileSeparator) {
		super(fileSeparator);
	}
	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(new byte[0]);
	}

	@Override
	public void close() {
	}

	@Override
	public long getLastModified() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getContent() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setContent(Object node) {
		// TODO Auto-generated method stub
		
	}

}
