package xyz.tozymc.api.config.file;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class TomlConfiguration extends FileConfiguration {

  private final Toml toml = new Toml();

  public TomlConfiguration(File file) throws IOException {
    super(file);
  }

  @Override
  protected void read() throws IOException {
    Toml toml;
    try (Reader reader = newReader()) {
      toml = this.toml.read(reader);
    }
    convertMapsToSections(toml.toMap(), this);
  }

  @Override
  protected void write() throws IOException {
    TomlWriter tomlWriter = new TomlWriter();
    try (Writer writer = newWriter()) {
      tomlWriter.write(convertSectionsTopMap(this), writer);
    } finally {
      updateTimestamp();
    }
  }
}
