package xyz.tozymc.api.config.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * Annotates an object that may be serialized.
 *
 * <p>If The serialization/deserialization method is not set, serializer will serialize/deserialize
 * the fields annotated by {@link SerialKey}. Otherwise, it will serialize/deserialize the objects
 * using the specified serialization/deserialization method.
 *
 * <p>This class must have an EMPTY constructor. If this class has final fields, all final fields
 * must be set to null.
 *
 * @author TozyMC
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigurationSerializable {

  /**
   * The serialization method to use for serialize an object to a {@link Map}.
   *
   * <p>This method <b>must</b> be following the instructions:
   * <ul>
   *   <li>The method must be placed in serializable class.</li>
   *   <li>A static or non-static method.</li>
   *   <li>Return type: The {@link Map} which keys is string.</li>
   *   <li>Only one parameter: The type of the serializable class.</li>
   * </ul>
   * <p>For example:
   * <pre>Map&lt;String, Object> serialize(T object) {
   *   // somethings
   *   return map;
   * }</pre>
   *
   * @return Name of serialization method to use.
   */
  String serialize() default "";

  /**
   * The deserialization method to use for deserialize a {@link Map} to an object.
   *
   * <p>This method <b>must</b> be following the instructions:
   * <ul>
   *   <li>The method must be placed in serializable class.</li>
   *   <li>A static method.</li>
   *   <li>Return type: The type of the serializable class.</li>
   *   <li>Only one parameter: The serialized {@link Map}.</li>
   * </ul>
   * <p>For example:
   * <pre>static T deserialize(Map&lt;String, Object> map) {
   *   // somethings
   *   return object;
   * }</pre>
   *
   * @return Name of deserialization method to use.
   */
  String deserialize() default "";
}
