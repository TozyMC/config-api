package xyz.tozymc.api.config.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class JsonConfiguration extends FileConfiguration {

  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public JsonConfiguration(@NotNull File file)
      throws IOException {
    super(file);
  }

  @Override
  protected void read() throws IOException {
    try (BufferedReader reader = newReader()) {
      convertMapsToSections(gson.fromJson(reader, Map.class), this);
    }
  }

  @Override
  protected void write() throws IOException {
    String data = gson.toJson(convertSectionsTopMap(this));
    try (Writer writer = newWriter()) {
      writer.write(data);
    } finally {
      updateTimestamp();
    }
  }
}
