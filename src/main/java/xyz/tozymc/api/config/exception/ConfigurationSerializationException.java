package xyz.tozymc.api.config.exception;

import xyz.tozymc.api.config.Configuration;

/**
 * Thrown when handled an exception occurred during the serialization with a {@link Configuration}.
 *
 * @author TozyMC
 * @since 1.0
 */
public class ConfigurationSerializationException extends RuntimeException {

  private static final long serialVersionUID = -1870885016310189673L;

  /**
   * Constructs a new {@link ConfigurationSerializationException} with a details message.
   *
   * @param message The details message of exception
   */
  public ConfigurationSerializationException(String message) {
    super(message);
  }

  /**
   * Constructs a new {@link ConfigurationSerializationException} with a message and cause.
   *
   * @param message The details message of exception
   * @param cause   The cause message of exception
   */
  public ConfigurationSerializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
