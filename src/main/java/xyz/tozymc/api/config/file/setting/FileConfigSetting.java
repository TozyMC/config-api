package xyz.tozymc.api.config.file.setting;

import org.jetbrains.annotations.NotNull;
import xyz.tozymc.api.config.Configuration;
import xyz.tozymc.api.config.file.FileConfigSection;
import xyz.tozymc.api.config.file.FileConfiguration;
import xyz.tozymc.api.config.setting.ConfigSetting;

/**
 * Various settings for controlling {@link FileConfiguration}.
 *
 * @author TozyMC
 * @see ConfigSetting
 * @since 1.0
 */
public class FileConfigSetting extends ConfigSetting {

  private ReloadType reloadType = ReloadType.MANUAL;

  /**
   * Constructs a new {@link ConfigSetting} used by {@link Configuration}.
   *
   * @param configuration The configuration that owned this setting.
   */
  public FileConfigSetting(FileConfigSection configuration) {
    super((Configuration) configuration);
  }

  /**
   * Gets the {@link ReloadType} of the {@link FileConfiguration}, default is {@link
   * ReloadType#MANUAL}.
   *
   * @return Type of reloading.
   */
  @NotNull
  public ReloadType reloadType() {
    return reloadType;
  }

  /**
   * Sets {@link ReloadType} of the {@link FileConfiguration}.
   *
   * @param reloadType Type of reloading.
   * @return This object, for chaining.
   */
  public FileConfigSetting reloadType(@NotNull ReloadType reloadType) {
    this.reloadType = reloadType;
    return this;
  }

  @Override
  public @NotNull FileConfiguration configuration() {
    return (FileConfiguration) super.configuration();
  }
}
