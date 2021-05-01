package xyz.tozymc.api.config.setting;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tozymc.api.config.ConfigSection;
import xyz.tozymc.api.config.Configuration;

/**
 * The base class of settings for controlling {@link Configuration}.
 *
 * @author TozyMC
 * @since 1.0
 */
public abstract class ConfigSetting {

  private final Configuration configuration;
  private char pathSeparator = '.';

  /**
   * Constructs a new {@link ConfigSetting} used by {@link Configuration}.
   *
   * @param configuration The configuration that owned this setting.
   */
  @Contract(pure = true)
  protected ConfigSetting(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * Returns the {@link Configuration} that owned this setting.
   *
   * @return Configuration that owned this setting.
   */
  @NotNull
  public Configuration configuration() {
    return configuration;
  }

  /**
   * Gets the char that will be used to separate in {@link ConfigSection}, default is '.'.
   *
   * @return Path separator.
   */
  public char pathSeparator() {
    return pathSeparator;
  }

  /**
   * Sets the char that will be used to separate in {@link ConfigSection}.
   *
   * @param separator Path separator.
   * @return This object, for chaining.
   */
  public ConfigSetting pathSeparator(char separator) {
    this.pathSeparator = separator;
    return this;
  }
}
