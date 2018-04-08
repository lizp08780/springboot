package com.lizp.springboot.serializer;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JsonSerializer<T> implements Serializer<T, String> {
	private final Logger log = LoggerFactory.getLogger(JsonSerializer.class);

	private ObjectMapper mapper;

	public synchronized ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			return mapper;
		} else {
			return mapper;
		}
	}

	@Override
	public String serialize(T t) {
		String jsonStr = "";
		try {
			if (t != null) {
				jsonStr = getMapper().writeValueAsString(t);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return jsonStr;
	}

	@Override
	public T deserialize(String r) {
		T obj = null;
		/*
		 * if (js) obj = getMapper().readValue(jsonStr, );
		 */
		return obj;
	}

	public List<?> parseCollection(String jsonStr, Class<?> collect, Class<?> bean) {
		List<?> obj = null;
		JavaType javaType = getCollectionType(collect, bean);
		try {
			if (!StringUtils.isEmpty(jsonStr)) {
				obj = getMapper().readValue(jsonStr, javaType);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return getMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
}
