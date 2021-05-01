package xyz.tozymc.api.util;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("ResultOfMethodCallIgnored")
public final class Files {

  private Files() {}

  public static void createNewFile(@NotNull File file) throws IOException {
    Preconditions.checkNotNull(file, "File cannot be null");

    if (file.exists()) {
      return;
    }

    createParentDirs(file);
    file.createNewFile();
  }

  public static void createParentDirs(@NotNull File file) throws IOException {
    Preconditions.checkNotNull(file, "File cannot be null");

    File parent = file.getCanonicalFile().getParentFile();
    if (parent != null) {
      parent.mkdirs();
    }
  }

  public static BufferedReader newBufferedReader(@NotNull File file) throws FileNotFoundException {
    Preconditions.checkNotNull(file, "File cannot be null");

    InputStream in = new FileInputStream(file);
    InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
    return new BufferedReader(reader);
  }

  public static BufferedWriter newBufferedWriter(@NotNull File file) throws FileNotFoundException {
    Preconditions.checkNotNull(file, "File cannot be null");

    OutputStream out = new FileOutputStream(file);
    OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    return new BufferedWriter(writer);
  }
}
