package xyz.tozymc.api.util;

public final class Preconditions {

  private Preconditions() {}

  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new IllegalArgumentException();
    }
    return reference;
  }

  public static <T> T checkNotNull(T reference, String message) {
    if (reference == null) {
      throw new IllegalArgumentException(message);
    }
    return reference;
  }

  public static void checkState(boolean expression) {
    if (!expression) {
      throw new IllegalStateException();
    }
  }

  public static void checkState(boolean expression, String message) {
    if (!expression) {
      throw new IllegalStateException(message);
    }
  }
}
