package jxsource.util.cl.cff;

import java.util.Vector;

public class CFFWrap {
	protected final CFFormat cff;
	protected final int magic;
	protected final int minor_version;
	protected final int major_version;
	protected final int constant_pool_count;
	protected final Vector constant_pool;
	protected final int access_flags;
	protected final int this_class;
	protected final int super_class;
	protected final int interfaces_count;
	protected final Vector interfaces;
	protected final int fields_count;
	protected final Vector fields;
	protected final int methods_count;
	protected final Vector methods;
	protected final int attributes_count;
	protected final Vector attributes;
	protected final int byteCount;
	protected final byte[] rest;
	protected final Vector args;
	
	public CFFWrap(CFFormat cff) {
		this.cff = cff;
		magic = cff.magic;
		minor_version = cff.minor_version;
		major_version = cff.major_version;
		constant_pool_count = cff.constant_pool_count;
		constant_pool = cff.constant_pool;
		access_flags = cff.access_flags;
		this_class = cff.this_class;
		super_class = cff.super_class;
		interfaces_count = cff.interfaces_count;
		interfaces = cff.interfaces;
		fields_count = cff.fields_count;
		fields = cff.fields;
		methods_count = cff.methods_count;
		methods = cff.methods;
		attributes_count = cff.attributes_count;
		attributes = cff.attributes;
		byteCount = cff.byteCount;
		rest = cff.rest;
		args = cff.args;
	}
}
