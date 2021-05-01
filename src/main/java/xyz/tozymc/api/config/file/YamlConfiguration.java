package xyz.tozymc.api.config.file;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class YamlConfiguration extends FileConfiguration {

  public YamlConfiguration(File file) throws IOException {
    super(file);
  }

  @Override
  protected void read() throws IOException {
    try (Reader reader = newReader()) {
      convertMapsToSections(YamlProvider.load(reader), this);
    }
  }

  @Override
  protected void write() throws IOException {
    String data = YamlProvider.dump(convertSectionsTopMap(this));
    try (Writer writer = newWriter()) {
      writer.write(data);
    } finally {
      updateTimestamp();
    }
  }
}
