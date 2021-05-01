package xyz.tozymc.api.config.exception;

import xyz.tozymc.api.config.Configuration;

/**
 * Thrown when an unhandled exception occurred during the interaction with a {@link Configuration}.
 *
 * @author TozyMC
 * @since 1.0
 */
public class ConfigurationException extends RuntimeException {

  private static final long serialVersionUID = 1388531990180241827L;

  /**
   * Constructs a new {@link ConfigurationException} without a message and cause.
   */
  public ConfigurationException() {
    super();
  }

  /**
   * Constructs a new {@link ConfigurationException} with a details message.
   *
   * @param message The details message of exception
   */
  public ConfigurationException(String message) {
    super(message);
  }

  /**
   * Constructs a new {@link ConfigurationException} with a message and cause.
   *
   * @param message The details message of exception
   * @param cause   The cause message of exception
   */
  public ConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new {@link ConfigurationException} with a cause.
   *
   * @param cause The cause message of exception
   */
  public ConfigurationException(Throwable cause) {
    super(cause);
  }
}
