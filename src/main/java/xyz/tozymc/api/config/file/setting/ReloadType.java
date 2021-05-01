package xyz.tozymc.api.config.file.setting;

import xyz.tozymc.api.config.file.FileConfiguration;

/**
 * The list of reload types used for {@link FileConfiguration}.
 *
 * @author TozyMC
 * @since 1.0
 */
public enum ReloadType {
  /**
   * Always reloads {@link FileConfiguration} before getting and setting a value.
   *
   * <p><b>Notes: </b>This can slow performance down. Better alternative: {@link #INTELLIGENT}.
   */
  AUTOMATIC,
  /**
   * Reloads {@link FileConfiguration} if the File is modified.
   */
  INTELLIGENT,
  /**
   * {@link FileConfiguration} will be reloaded manually.
   */
  MANUAL
}
