package xyz.tozymc.api.config.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.api.config.ConfigSection;
import xyz.tozymc.api.util.Preconditions;

public final class Paths {

  private Paths() {}

  @NotNull
  public static String createPath(@NotNull ConfigSection parent, @NotNull String name) {
    Preconditions.checkNotNull(parent, "Parent cannot be null");
    Preconditions.checkNotNull(name, "Name cannot be null");

    String parentPath = parent.getFullPath();
    return parentPath + (parentPath.isEmpty() ? "" : ".") + name;
  }

  @NotNull
  public static String createPath(@NotNull ConfigSection parent, @NotNull String name,
      @Nullable ConfigSection relativeTo) {
    Preconditions.checkNotNull(parent, "Parent cannot be null");
    Preconditions.checkNotNull(name, "Name cannot be null");

    StringBuilder builder = new StringBuilder();
    char separator = parent.getRoot().getSetting().pathSeparator();
    for (ConfigSection sec = parent; (sec != null) && (sec != relativeTo); sec = sec.getParent()) {
      String previousName = sec.getName();
      if (!previousName.isEmpty()) {
        builder.insert(0, separator);
      }
      builder.insert(0, previousName);
    }
    return builder.append(name).toString();
  }
}
