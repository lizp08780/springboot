package com.lizp.springboot.shiro;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

public class CustomByteSource implements ByteSource, Serializable {
	private static final long serialVersionUID = 453843947425730799L;
	private byte[] bytes;
	private String cachedHex;
	private String cachedBase64;

	public CustomByteSource() {
	}

	public CustomByteSource(byte[] bytes) {
		this.bytes = bytes;
	}

	public CustomByteSource(char[] chars) {
		this.bytes = CodecSupport.toBytes(chars);
	}

	public CustomByteSource(String string) {
		this.bytes = CodecSupport.toBytes(string);
	}

	public CustomByteSource(ByteSource source) {
		this.bytes = source.getBytes();
	}

	public CustomByteSource(File file) {
		this.bytes = (new BytesHelper()).getBytes(file);
	}

	public CustomByteSource(InputStream stream) {
		this.bytes = (new BytesHelper()).getBytes(stream);
	}

	public static boolean isCompatible(Object o) {
		return o instanceof byte[] || o instanceof char[] || o instanceof String || o instanceof ByteSource
				|| o instanceof File || o instanceof InputStream;
	}

	@Override
	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toHex() {
		if (this.cachedHex == null) {
			this.cachedHex = Hex.encodeToString(this.getBytes());
		}
		return this.cachedHex;
	}

	@Override
	public String toBase64() {
		if (this.cachedBase64 == null) {
			this.cachedBase64 = Base64.encodeToString(this.getBytes());
		}

		return this.cachedBase64;
	}

	@Override
	public boolean isEmpty() {
		return this.bytes == null || this.bytes.length == 0;
	}

	@Override
	public String toString() {
		return this.toBase64();
	}

	@Override
	public int hashCode() {
		return this.bytes != null && this.bytes.length != 0 ? Arrays.hashCode(this.bytes) : 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (o instanceof ByteSource) {
			ByteSource bs = (ByteSource) o;
			return Arrays.equals(this.getBytes(), bs.getBytes());
		} else {
			return false;
		}
	}

	private static final class BytesHelper extends CodecSupport {
		private BytesHelper() {
		}

		public byte[] getBytes(File file) {
			return this.toBytes(file);
		}

		public byte[] getBytes(InputStream stream) {
			return this.toBytes(stream);
		}
	}
}
