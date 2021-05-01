package xyz.tozymc.api.config.serialization.handler;

import xyz.tozymc.api.config.exception.ConfigurationSerializationException;
import xyz.tozymc.api.config.serialization.ConfigurationSerializable;
import xyz.tozymc.api.config.serialization.SerialKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

final class Reflections {

  private static final ConcurrentMap<Class<?>, Constructor<?>> constructorCache;
  private static final ConcurrentMap<Class<?>, Field[]> fieldsCache;
  private static final ConcurrentMap<Class<?>, Method> serializationMethodCache;
  private static final ConcurrentMap<Class<?>, Method> deserializationMethodCache;

  static {
    constructorCache = new ConcurrentHashMap<>();
    fieldsCache = new ConcurrentHashMap<>();
    serializationMethodCache = new ConcurrentHashMap<>();
    deserializationMethodCache = new ConcurrentHashMap<>();
  }

  private Reflections() {}

  protected static void setField(Field field, Object instance, Object value) {
    try {
      field.set(instance, value);
    } catch (IllegalAccessException ignored) {
    }
  }

  protected static Object getValue(Field field, Object instance) {
    try {
      return field.get(instance);
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  protected static Stream<Field> newSerializationFieldsStream(Class<?> clazz) {
    if (fieldsCache.containsKey(clazz)) {
      return Arrays.stream(fieldsCache.get(clazz));
    }
    return Arrays.stream(findSerializationFields(clazz));
  }

  private static Field[] findSerializationFields(Class<?> clazz) {
    Field[] fields = Arrays.stream(clazz.getDeclaredFields())
        .peek(field -> field.setAccessible(true))
        .filter(field -> field.getAnnotation(SerialKey.class) != null)
        .toArray(Field[]::new);
    fieldsCache.put(clazz, fields);
    return fields;
  }

  protected static Object newInstance(Class<?> clazz) {
    try {
      return getConstructor(clazz).newInstance();
    } catch (InstantiationException e) {
      throw new ConfigurationSerializationException("Class cannot be an abstract class", e);
    } catch (InvocationTargetException e) {
      throw new ConfigurationSerializationException("Constructor throws an exception", e);
    } catch (IllegalAccessException ignored) {
      return null;
    }
  }

  private static Constructor<?> getConstructor(Class<?> clazz) {
    if (constructorCache.containsKey(clazz)) {
      return constructorCache.get(clazz);
    }
    return findConstructor(clazz);
  }

  private static Constructor<?> findConstructor(Class<?> clazz) {
    try {
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructorCache.put(clazz, constructor);
      return constructor;
    } catch (NoSuchMethodException e) {
      throw new ConfigurationSerializationException(
          "Serialization class must have an empty constructor", e);
    }
  }

  protected static <T> T invoke(Method method, Class<T> type, Object instance, Object param) {
    try {
      return type.cast(method.invoke(instance, param));
    } catch (InvocationTargetException e) {
      throw new ConfigurationSerializationException("Method throws an exception", e);
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  protected static <T> T invokeStatic(Method method, Class<T> type, Object param) {
    return invoke(method, type, null, param);
  }

  protected static Method getSerializationMethod(Class<?> clazz) {
    if (serializationMethodCache.containsKey(clazz)) {
      return serializationMethodCache.get(clazz);
    }
    return findSerializationMethod(clazz);
  }

  private static Method findSerializationMethod(Class<?> clazz) {
    ConfigurationSerializable annotation = clazz.getAnnotation(ConfigurationSerializable.class);
    try {
      Method method = getMethod(clazz, annotation.serialize(), clazz);
      serializationMethodCache.put(clazz, method);
      return method;
    } catch (NoSuchMethodException e) {
      throw new ConfigurationSerializationException("No serialization method found", e);
    }
  }

  protected static Method getDeserializationMethod(Class<?> clazz) {
    if (deserializationMethodCache.containsKey(clazz)) {
      return deserializationMethodCache.get(clazz);
    }
    return findDeserializationMethod(clazz);
  }

  private static Method findDeserializationMethod(Class<?> clazz) {
    ConfigurationSerializable annotation = clazz.getAnnotation(ConfigurationSerializable.class);

    try {
      Method method = getMethod(clazz, annotation.deserialize(), Map.class);
      deserializationMethodCache.put(clazz, method);
      return method;
    } catch (NoSuchMethodException e) {
      throw new ConfigurationSerializationException("No deserialization method found", e);
    }
  }

  private static Method getMethod(Class<?> clazz, String name, Class<?> pType)
      throws NoSuchMethodException {
    Method method = clazz.getDeclaredMethod(name, pType);
    method.setAccessible(true);
    return method;
  }
}
