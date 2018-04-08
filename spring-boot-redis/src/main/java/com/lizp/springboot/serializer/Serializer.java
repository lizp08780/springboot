package com.lizp.springboot.serializer;

public interface Serializer<T, R> {
	R serialize(T t);

	T deserialize(R r);
}
