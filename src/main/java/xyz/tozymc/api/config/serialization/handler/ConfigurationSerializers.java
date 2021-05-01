package xyz.tozymc.api.config.serialization.handler;

import static xyz.tozymc.api.config.serialization.handler.Reflections.getDeserializationMethod;
import static xyz.tozymc.api.config.serialization.handler.Reflections.getSerializationMethod;
import static xyz.tozymc.api.config.serialization.handler.Reflections.getValue;
import static xyz.tozymc.api.config.serialization.handler.Reflections.invoke;
import static xyz.tozymc.api.config.serialization.handler.Reflections.invokeStatic;
import static xyz.tozymc.api.config.serialization.handler.Reflections.newInstance;
import static xyz.tozymc.api.config.serialization.handler.Reflections.newSerializationFieldsStream;
import static xyz.tozymc.api.config.serialization.handler.Reflections.setField;

import org.jetbrains.annotations.NotNull;
import xyz.tozymc.api.config.Configuration;
import xyz.tozymc.api.config.exception.ConfigurationSerializationException;
import xyz.tozymc.api.config.serialization.ConfigurationSerializable;
import xyz.tozymc.api.config.serialization.SerialKey;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for storing and retrieving classes for {@link Configuration}.
 *
 * @author TozyMC
 * @since 1.0
 */
public final class ConfigurationSerializers {

  private ConfigurationSerializers() {}

  /**
   * Attempts to serialize the given object into a {@link Map} to represent it.
   *
   * @param object Object to serialize.
   * @return Map containing the current state of this object.\
   * @throws ConfigurationSerializationException Thrown when class is not a serializable class or
   *                                             error when serialize object.
   */
  public static Map<?, ?> serialize(Object object) {
    Class<?> clazz = object.getClass();
    ConfigurationSerializable serializable = clazz.getAnnotation(ConfigurationSerializable.class);
    if (serializable == null) {
      throw new ConfigurationSerializationException("Cannot serialize this class");
    }

    if (hasSerializationMethod(serializable)) {
      return invoke(getSerializationMethod(clazz), Map.class, object, object);
    }

    Map<String, Object> map = new LinkedHashMap<>();
    newSerializationFieldsStream(clazz).forEach(field -> {
      SerialKey annotation = field.getAnnotation(SerialKey.class);
      map.put(annotation.value(), getValue(field, object));
    });
    return map;
  }

  /**
   * Attempts to deserialize the given arguments into a new instance of the given class.
   *
   * @param map   Arguments for deserialization.
   * @param clazz Class to deserialize into.
   * @param <T>   Type of object.
   * @return New instance of the specified class.
   * @throws ConfigurationSerializationException Thrown when class is not a deserializable class or
   *                                             error when deserialize object.
   */
  public static <T> T deserialize(Map<?, ?> map, @NotNull Class<T> clazz) {
    ConfigurationSerializable serializable = clazz.getAnnotation(ConfigurationSerializable.class);
    if (serializable == null) {
      throw new ConfigurationSerializationException("Cannot deserialize this class");
    }

    if (hasDeserializationMethod(serializable)) {
      return invokeStatic(getDeserializationMethod(clazz), clazz, map);
    }

    Object object = newInstance(clazz);
    newSerializationFieldsStream(clazz).forEach(field -> {
      SerialKey annotation = field.getAnnotation(SerialKey.class);
      setField(field, object, map.get(annotation.value()));
    });
    return clazz.cast(object);
  }

  /**
   * Checks if the specified object is a serializable object
   *
   * @param object Object to check.
   * @return Whether or not the specified path is a serializable object.
   */
  public static boolean isSerializableObject(Object object) {
    if (object == null) {
      return false;
    }
    return object.getClass().getAnnotation(ConfigurationSerializable.class) != null;
  }

  private static boolean hasSerializationMethod(ConfigurationSerializable annotation) {
    return !annotation.serialize().isEmpty();
  }

  private static boolean hasDeserializationMethod(ConfigurationSerializable annotation) {
    return !annotation.deserialize().isEmpty();
  }
}
