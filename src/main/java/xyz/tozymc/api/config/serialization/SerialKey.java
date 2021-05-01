package xyz.tozymc.api.config.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a fields that may be serialized.
 *
 * @author TozyMC
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerialKey {

  /**
   * The name of the value will be stored in the configuration.
   *
   * <p>For example:
   * <br> {@code @SerialKey("name") String name;}
   * <br> Serializer will serial name field as name in configuration.
   *
   * @return Name of value key
   */
  String value();
}
