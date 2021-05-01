package xyz.tozymc.api.util;

import org.jetbrains.annotations.Nullable;

public final class NumberConversations {

  public static int toInt(@Nullable Object object) {
    if (object instanceof Number) {
      return ((Number) object).intValue();
    }

    if (object == null) {
      return 0;
    }

    try {
      return Integer.parseInt(object.toString());
    } catch (NumberFormatException ignored) {
      return 0;
    }
  }

  public static float toFloat(@Nullable Object object) {
    if (object instanceof Number) {
      return ((Number) object).floatValue();
    }

    if (object == null) {
      return 0;
    }

    try {
      return Float.parseFloat(object.toString());
    } catch (NumberFormatException ignored) {
      return 0;
    }
  }

  public static double toDouble(@Nullable Object object) {
    if (object instanceof Number) {
      return ((Number) object).doubleValue();
    }

    if (object == null) {
      return 0;
    }

    try {
      return Double.parseDouble(object.toString());
    } catch (NumberFormatException ignored) {
      return 0;
    }
  }

  public static long toLong(@Nullable Object object) {
    if (object instanceof Number) {
      return ((Number) object).longValue();
    }

    if (object == null) {
      return 0;
    }

    try {
      return Long.parseLong(object.toString());
    } catch (NumberFormatException ignored) {
      return 0;
    }
  }

  public static byte toByte(@Nullable Object object) {
    if (object instanceof Number) {
      return ((Number) object).byteValue();
    }

    if (object == null) {
      return 0;
    }

    try {
      return Byte.parseByte(object.toString());
    } catch (NumberFormatException ignored) {
      return 0;
    }
  }
}
