package xyz.tozymc.api.config.file;

import org.jetbrains.annotations.NotNull;
import xyz.tozymc.api.config.Configuration;
import xyz.tozymc.api.config.exception.ConfigurationException;
import xyz.tozymc.api.config.file.setting.FileConfigSetting;
import xyz.tozymc.api.util.Files;
import xyz.tozymc.api.util.Preconditions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The base class for all file based implement of {@link Configuration}.
 *
 * @author TozyMC
 * @since 1.0
 */
public abstract class FileConfiguration extends FileConfigSection implements Configuration,
    Comparable<FileConfiguration> {

  private final File file;
  private final FileConfigSetting setting;

  protected long timestamp;

  /**
   * Constructs new {@link FileConfiguration} with specified file.
   *
   * <p>This constructor also creates new file if it doesn't exist, and update timestamp.
   *
   * @param file The file to store data into.
   * @throws IOException Thrown when initial file failed.
   */
  protected FileConfiguration(@NotNull File file)
      throws IOException {
    this.file = Preconditions.checkNotNull(file, "File cannot be null");
    this.setting = new FileConfigSetting(this);

    Files.createNewFile(file);
    updateTimestamp();
  }

  protected abstract void read() throws IOException;

  protected abstract void write() throws IOException;

  protected boolean hasFileChanged() {
    return file.lastModified() < timestamp;
  }

  protected long getTimestamp() {
    return timestamp;
  }

  protected void updateTimestamp() {
    this.timestamp = file.lastModified();
  }

  /**
   * Loads this {@link FileConfiguration} from file.
   *
   * <p>All the values contained within this configuration will be removed, leaving only settings
   * and defaults, and the new values will be loaded from the given file.
   *
   * <p>If the file cannot be loaded for any reason, an exception will be thrown. After loading,
   * this configuration will update timestamp.
   */
  public void load() {
    try {
      read();
    } catch (IOException e) {
      throw new ConfigurationException("Error when loading `" + file.getName() + "` configuration",
          e);
    } finally {
      updateTimestamp();
    }
  }

  private void forceSave() {
    try {
      Files.createNewFile(file);
      write();
    } catch (IOException e) {
      throw new ConfigurationException("Error when saving `" + file.getName() + "` configuration",
          e);
    } finally {
      updateTimestamp();
    }
  }


  private void saveIfNeeded(Object val, Object oldVal) {
    if (!val.equals(oldVal)) {
      forceSave();
    }
  }

  protected void save0(Object... values) {
    switch (setting.reloadType()) {
      case AUTOMATIC:
        forceSave();
        break;
      case INTELLIGENT:
        // For optimize performance
        if (values.length == 2) {
          saveIfNeeded(values[0], values[1]);
          break;
        }
        forceSave();
        break;
      case MANUAL:
      default:
        break;
    }
  }

  /**
   * Saves this {@link FileConfiguration} to file.
   *
   * <p>If the file does not exist, it will be created. If already exists, it will be overwritten.
   *
   * <p>If it cannot be overwritten or created, an exception will be thrown. After saving, this
   * configuration will update timestamp.
   *
   * <p>This method will save using the system default encoding, or possibly using UTF8.
   */
  public void save() {
    forceSave();
  }

  private void forceReload() {
    try {
      write();
      read();
    } catch (IOException e) {
      throw new ConfigurationException(
          "Error when reloading `" + file.getName() + "` configuration", e);
    } finally {
      updateTimestamp();
    }
  }

  private void reloadIfNeeded() {
    if (hasFileChanged()) {
      forceReload();
    }
  }

  /**
   * Reloads this {@link FileConfiguration} if the file has changed.
   *
   * <p><b>Notes: </b>This method will save this {@link FileConfiguration} to file before reload.
   *
   * @see #load()
   */
  public void reload() {
    reloadIfNeeded();
  }

  protected void reload0() {
    switch (setting.reloadType()) {
      case AUTOMATIC:
        forceReload();
        break;
      case INTELLIGENT:
        reloadIfNeeded();
        break;
      case MANUAL:
      default:
        break;
    }
  }

  @Override
  public @NotNull FileConfigSetting getSetting() {
    return setting;
  }

  protected void convertMapsToSections(Map<?, ?> input, FileConfigSection section) {
    input.forEach((key, val) -> {
      String secKey = key.toString();
      if (val instanceof Map) {
        convertMapsToSections((Map<?, ?>) val, section.createSection0(secKey));
        return;
      }

      section.set0(secKey, val);
    });
  }


  protected Map<String, Object> convertSectionsTopMap(FileConfigSection section) {
    Map<String, Object> map = new LinkedHashMap<>();
    section.values.forEach((key, value) -> {
      if (!(value instanceof FileConfigSection)) {
        map.put(key, value);
        return;
      }
      map.put(key, convertSectionsTopMap((FileConfigSection) value));
    });
    return map;
  }

  protected BufferedReader newReader() throws FileNotFoundException {
    return Files.newBufferedReader(file);
  }

  protected BufferedWriter newWriter() throws FileNotFoundException {
    return Files.newBufferedWriter(file);
  }

  /**
   * Gets the file in which it stores the data.
   *
   * @return File to store data into.
   */
  public File getFile() {
    return file;
  }

  @Override
  public int compareTo(@NotNull FileConfiguration o) {
    return file.compareTo(o.file);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FileConfigSection)) {
      return false;
    }
    FileConfiguration storage = (FileConfiguration) o;
    return file.equals(storage.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(file);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[file=" + file.getName() + "]";
  }
}
