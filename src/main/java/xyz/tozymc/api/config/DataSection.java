package xyz.tozymc.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a section of data in {@link Configuration}.
 *
 * @author TozyMC
 * @since 1.0
 */
public interface DataSection {

  /**
   * Checks if the specified path exists or not.
   *
   * @param path Path to for existence.
   * @return Whether or not the specified path is exist.
   */
  boolean contains(@NotNull String path);

  /**
   * Gets the requested object by path.
   *
   * @param path Path of the object to get.
   * @return Requested object.
   */
  @Nullable Object getObject(@NotNull String path);

  /**
   * Gets the requested object by path, returning the default value if not found.
   *
   * @param path Path of the object to get.
   * @param def  The default value to return if path does not exist.
   * @return Requested object.
   */
  @Nullable Object getObject(@NotNull String path, Object def);

  /**
   * Checks if the specified path is a byte.
   *
   * <p>If the path does not exist or the path exists but is not a byte, this will return false.
   *
   * @param path Path of the byte to check.
   * @return Whether or not the specified path is a byte.
   */
  boolean isByte(@NotNull String path);

  /**
   * Gets the requested byte by path.
   *
   * <p>If the requested byte does not exist or is not a byte, this will return 0.
   *
   * @param path Path of the byte to get.
   * @return Requested byte.
   */
  byte getByte(@NotNull String path);

  /**
   * Gets the requested byte by path, returning the default value if not found.
   *
   * @param path Path of the byte to get.
   * @param def  The default value to return if path does not exist or is not a byte.
   * @return Requested byte.
   */
  byte getByte(@NotNull String path, byte def);

  /**
   * Checks if the specified path is an int.
   *
   * <p>If the path does not exist or the path exists but is not an int, this will return false.
   *
   * @param path Path of the int to check.
   * @return Whether or not the specified path is an int.
   */
  boolean isInt(@NotNull String path);

  /**
   * Gets the requested int by path.
   *
   * <p>If the requested int does not exist or is not an int, this will return 0.
   *
   * @param path Path of the int to get.
   * @return Requested int.
   */
  int getInt(@NotNull String path);

  /**
   * Gets the requested int by path, returning the default value if not found.
   *
   * @param path Path of the int to get.
   * @param def  The default value to return if path does not exist or is not an int.
   * @return Requested int.
   */
  int getInt(@NotNull String path, int def);

  /**
   * Checks if the specified path is a long.
   *
   * <p>If the path does not exist or the path exists but is not a long, this will return false.
   *
   * @param path Path of the long to check.
   * @return Whether or not the specified path is a long.
   */
  boolean isLong(@NotNull String path);

  /**
   * Gets the requested long by path.
   *
   * <p>If the requested long does not exist or is not a long, this will return 0.
   *
   * @param path Path of the long to get.
   * @return Requested long.
   */
  long getLong(@NotNull String path);

  /**
   * Gets the requested long by path, returning the default value if not found.
   *
   * @param path Path of the long to get.
   * @param def  The default value to return if path does not exist or is not a long.
   * @return Requested long.
   */
  long getLong(@NotNull String path, long def);

  /**
   * Checks if the specified path is a double.
   *
   * <p>If the path does not exist or the path exists but is not a double, this will return false.
   *
   * @param path Path of the double to check.
   * @return Whether or not the specified path is a double.
   */
  boolean isDouble(@NotNull String path);

  /**
   * Gets the requested double by path.
   *
   * <p>If the requested double does not exist or is not a double, this will return 0.
   *
   * @param path Path of the double to get.
   * @return Requested double.
   */
  double getDouble(@NotNull String path);

  /**
   * Gets the requested double by path, returning the default value if not found.
   *
   * @param path Path of the double to get.
   * @param def  The default value to return if path does not exist or is not a double.
   * @return Requested double.
   */
  double getDouble(@NotNull String path, double def);

  /**
   * Checks if the specified path is a boolean.
   *
   * <p>If the path does not exist or the path exists but is not a boolean, this will return false.
   *
   * @param path Path of the boolean to check.
   * @return Whether or not the specified path is a boolean.
   */
  boolean isBoolean(@NotNull String path);

  /**
   * Gets the requested boolean by path.
   *
   * <p>If the requested boolean does not exist or is not a boolean, this will return false.
   *
   * @param path Path of the boolean to get.
   * @return Requested boolean.
   */
  boolean getBoolean(@NotNull String path);

  /**
   * Gets the requested boolean by path, returning the default value if not found.
   *
   * @param path Path of the boolean to get.
   * @param def  The default value to return if path does not exist or is not a boolean.
   * @return Requested boolean.
   */
  boolean getBoolean(@NotNull String path, boolean def);

  /**
   * Checks if the specified path is a char.
   *
   * <p>If the path does not exist or the path exists but is not a char, this will return false.
   *
   * @param path Path of the char to check.
   * @return Whether or not the specified path is a char.
   */
  boolean isChar(@NotNull String path);

  /**
   * Gets the requested char by path.
   *
   * <p>If the requested char does not exist or is not a boolean, this will return empty char.
   *
   * @param path Path of the char to get.
   * @return Requested char.
   */
  char getChar(@NotNull String path);

  /**
   * Gets the requested char by path, returning the default value if not found.
   *
   * @param path Path of the char to get.
   * @param def  The default value to return if path does not exist or is not a char.
   * @return Requested char.
   */
  char getChar(@NotNull String path, char def);

  /**
   * Checks if the specified path is a string.
   *
   * <p>If the path does not exist or the path exists but is not a string, this will return false.
   *
   * @param path Path of the string to check.
   * @return Whether or not the specified path is a string.
   */
  boolean isString(@NotNull String path);

  /**
   * Gets the requested string by path.
   *
   * <p>If the requested string does not exist or is not a string, this will always return a string
   * in which the requested object is wrapped by {@link String#valueOf}.
   *
   * @param path Path of the string to get.
   * @return Requested string.
   */
  @NotNull String getString(@NotNull String path);

  /**
   * Gets the requested string by path, returning the default value if not found.
   *
   * @param path Path of the double to get.
   * @param def  The default value to return if path does not exist.
   * @return Requested string.
   */
  @NotNull String getString(@NotNull String path, String def);

  /**
   * Checks if the specified path is a list.
   *
   * <p>If the path does not exist or the path exists but is not a list, this will return false.
   *
   * @param path Path of the list to check.
   * @return Whether or not the specified path is a list.
   */
  boolean isList(@NotNull String path);

  /**
   * Gets the requested list by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return null.
   *
   * @param path Path of the list to get.
   * @return Requested list.
   */
  @Nullable List<?> getList(@NotNull String path);

  /**
   * Gets the requested list by path, returning the default value if not found.
   *
   * @param path Path of the list to get.
   * @param def  The default value to return if path does not exist or is not a list.
   * @return Requested list.
   */
  @Nullable List<?> getList(@NotNull String path, List<?> def);

  /**
   * Gets the requested list of string by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into a string using {@link
   * String#valueOf}.
   *
   * @param path Path of the list to get.
   * @return Requested list of string.
   */
  @NotNull List<String> getStringList(@NotNull String path);

  /**
   * Gets the requested list of byte by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into a byte if possible.
   *
   * @param path Path of the list to get.
   * @return Requested list of byte.
   */
  @NotNull List<Byte> getByteList(@NotNull String path);

  /**
   * Gets the requested list of integer by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into an integer if possible.
   *
   * @param path Path of the list to get.
   * @return Requested list of integer.
   */
  @NotNull List<Integer> getIntegerList(@NotNull String path);

  /**
   * Gets the requested list of long by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into a long if possible.
   *
   * @param path Path of the list to get.
   * @return Requested list of long.
   */
  @NotNull List<Long> getLongList(@NotNull String path);

  /**
   * Gets the requested list of float by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into a float if possible.
   *
   * @param path Path of the list to get.
   * @return Requested list of float.
   */
  @NotNull List<Float> getFloatList(@NotNull String path);

  /**
   * Gets the requested list of double by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into a double if possible.
   *
   * @param path Path of the list to get.
   * @return Requested list of double.
   */
  @NotNull List<Double> getDoubleList(@NotNull String path);

  /**
   * Gets the requested list of boolean by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into a boolean if possible.
   *
   * @param path Path of the list to get.
   * @return Requested list of boolean.
   */
  @NotNull List<Boolean> getBooleanList(@NotNull String path);

  /**
   * Gets the requested list of character by path.
   *
   * <p>If the requested list does not exist or is not a list, this will return an empty list.
   *
   * <p><b>Notes: </b>This method will attempt to cast any values into a character if possible.
   *
   * @param path Path of the list to get.
   * @return Requested list of character.
   */
  @NotNull List<Character> getCharacterList(@NotNull String path);
}
