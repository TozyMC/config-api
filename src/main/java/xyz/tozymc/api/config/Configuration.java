package xyz.tozymc.api.config;

import org.jetbrains.annotations.NotNull;
import xyz.tozymc.api.config.setting.ConfigSetting;

/**
 * Represents a source of configurable options and settings. This class can use as {@link
 * ConfigSection}.
 *
 * @author TozyMC
 * @see ConfigSection
 * @since 1.0
 */
public interface Configuration extends ConfigSection {

  /**
   * Gets the {@link ConfigSetting} of this {@link Configuration}.
   *
   * @return Setting of this configuration.
   */
  @NotNull ConfigSetting getSetting();
}
