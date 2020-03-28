package com.enhance.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

	private JsonUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setDateFormat(new SimpleDateFormat(DateFormatUtil.DATETIME_FORMAT));
	}

	public static String marshal(Object object) {
		try (StringWriter writer = new StringWriter();) {
			objectMapper.writeValue(writer, object);
			return writer.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	// 无法确定 <T>T 的类型参数；对于上限为 T,java.lang.Object 的类型变量 T，不存在唯一最大实例
	public static <T> T unmarshal(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T> T unmarshal(String json, JavaType javaType) {
		try {
			return objectMapper.readValue(json, javaType);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T> T unmarshalList(String json, Class<?> clazz) {
		try {
			return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T> JavaType constructParametricType(Class<T> clazz, Class<?>... parameterClasses) {
		return objectMapper.getTypeFactory().constructParametricType(clazz, parameterClasses);
	}

	public static <T> JavaType constructParametricType(Class<T> clazz, JavaType... javaTypes) {
		return objectMapper.getTypeFactory().constructParametricType(clazz, javaTypes);
	}

	public static <T> T unmarshalWithGeneric(String json, Class<?> clazz, Class<?> genericType) {
		try {
			return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(clazz, genericType));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T> T unmarshal(String json, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException();
		}
	}

	public static JsonNode unmarshal(String json) {
		try {
			return objectMapper.readTree(json);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException();
		}
	}

	public static <T> byte[] serialize(T obj) {
		try {
			return objectMapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static <T> T deserialize(byte[] data, Class<T> clazz) {
		try {
			return objectMapper.readValue(data, clazz);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
