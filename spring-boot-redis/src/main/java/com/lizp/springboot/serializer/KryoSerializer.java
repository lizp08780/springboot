package com.lizp.springboot.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoSerializer implements Serializer<Object, byte[]> {
	private Kryo kryo = new Kryo();

	@Override
	public byte[] serialize(Object t) {
		byte[] buffer = new byte[2048];
		Output output = new Output(buffer);
		kryo.writeClassAndObject(output, t);
		return output.toBytes();
	}

	@Override
	public Object deserialize(byte[] r) {
		Input input = new Input(r);
		Object t = kryo.readClassAndObject(input);
		return t;
	}
}
