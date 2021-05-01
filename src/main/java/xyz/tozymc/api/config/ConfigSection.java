package xyz.tozymc.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.api.config.exception.ConfigurationException;
import xyz.tozymc.api.config.exception.ConfigurationSerializationException;
import xyz.tozymc.api.config.serialization.ConfigurationSerializable;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a section of a {@link Configuration}, it stores configuration data values.
 *
 * @author TozyMC
 * @see DataSection
 * @since 1.0
 */
public interface ConfigSection extends DataSection {

  /**
   * Checks if this {@link ConfigSection} contains the specified path.
   *
   * @param path Path to check for existence.
   * @return Whether or not the requested path is exist.
   * @throws IllegalArgumentException Thrown when path is null.
   */
  @Override
  boolean contains(@NotNull String path);

  /**
   * Finds the requested object by path, returning a {@link Optional#empty()} if not found.
   *
   * @param path Path of the object to find.
   * @return Optional of requested object.
   * @throws IllegalArgumentException Thrown when path is null.
   */
  @NotNull Optional<?> findObject(@NotNull String path);

  /**
   * Finds the requested object which is casted by path, returning a {@link Optional#empty()} if not
   * found.
   *
   * <p><b>Notes: </b>The {@link ClassCastException} won't be caught, instead it will return an
   * empty {@link Optional}.
   *
   * @param path  Path of the object to find.
   * @param clazz Class to find into.
   * @param <T>   Type of the object.
   * @return Optional of requested object which is casted.
   * @throws IllegalArgumentException Thrown when path or type is null.
   */
  @NotNull <T> Optional<T> find(@NotNull String path, @NotNull Class<T> clazz);

  /**
   * Gets the requested object by path which is casted, returning null if not found.
   *
   * <p><b>Notes: </b>The {@link ClassCastException} won't be caught, instead it will return null.
   *
   * @param path  Path of the object.
   * @param clazz Class to get into.
   * @param <T>   Type of the object.
   * @return Requested object which is casted.
   * @throws IllegalArgumentException Thrown when path or type is null.
   */
  @Nullable <T> T get(@NotNull String path, @NotNull Class<T> clazz);

  /**
   * Gets the requested object by path which is casted, returning the default value if not found.
   *
   * <p><b>Notes: </b>The {@link ClassCastException} won't be caught, instead it will return null.
   *
   * @param path  Path of the object.
   * @param clazz Class to get into.
   * @param def   The default object to return if the object is not present at the path
   * @param <T>   Type of the object.
   * @return Requested object which is casted.
   * @throws IllegalArgumentException Thrown when path or type is null.
   */
  @Nullable <T> T getOrDefault(@NotNull String path, @NotNull Class<T> clazz, T def);

  /**
   * Sets the specified path to the given value.
   *
   * <p>If value is null, the entry will be removed. Any existing entry will be replaced,
   * regardless of what the new value is.
   *
   * @param path  Path of the object to set.
   * @param value New value to set the path to.
   * @return Optional of previous value.
   * @throws IllegalArgumentException Thrown when path is null.
   */
  @NotNull Optional<?> set(@NotNull String path, Object value);

  /**
   * Checks if the specified path is a {@link ConfigSection}.
   *
   * <p>If the path does not exist or the path exists but is not a {@link ConfigSection}, this will
   * return false.
   *
   * @param path Path of the section to check.
   * @return Whether or not the specified path is a section.
   * @throws IllegalArgumentException Thrown when path is null.
   */
  boolean isSection(@NotNull String path);

  /**
   * Finds the requested {@link ConfigSection} by path, returning a {@link Optional#empty()} if not
   * found.
   *
   * @param path Path of the section to find.
   * @return Optional of requested section.
   * @throws IllegalArgumentException Thrown when path is null.
   */
  @NotNull Optional<ConfigSection> findSection(@NotNull String path);

  /**
   * Gets the requested {@link ConfigSection} by path.
   * <p>If the requested {@link ConfigSection} does not exist or is not a section, this will return
   * null.
   *
   * @param path Path of the section to get.
   * @return Requested section.
   * @throws IllegalArgumentException Thrown when path is null.
   */
  @Nullable ConfigSection getSection(@NotNull String path);

  /**
   * Creates an empty {@link ConfigSection} at the specified path.
   *
   * @param path Path to create the section at.
   * @return Newly created section
   * @throws IllegalArgumentException Thrown when path is null.
   * @throws ConfigurationException   Thrown when section is exist.
   */
  @NotNull ConfigSection createSection(@NotNull String path);

  /**
   * Creates an empty {@link ConfigSection} at the specified path, with specified values.
   *
   * <p><b>Notes: </b>Any value that was previously set at this path will be overwritten.
   *
   * @param path   Path to create the section at.
   * @param values The values to used.
   * @return Newly created section
   * @throws IllegalArgumentException Thrown when path is null.
   */
  @NotNull ConfigSection createSection(@NotNull String path, @NotNull Map<?, ?> values);

  /**
   * Finds the requested {@link ConfigurationSerializable} object by path, returning a {@link
   * Optional#empty()} if not found.
   *
   * <p><b>Notes: </b>The {@link ClassCastException} won't be caught, instead it will return an
   * empty {@link Optional}.
   *
   * @param path  Path of the requested object to find.
   * @param clazz Class to serialize into.
   * @param <T>   Type of requested object
   * @return Optional of requested serializable object.
   * @throws IllegalArgumentException            Thrown when path or type is null.
   * @throws ConfigurationSerializationException Thrown when class is not a serializable class or
   *                                             error when serialize object.
   */
  @NotNull <T> Optional<T> findSerializable(@NotNull String path, @NotNull Class<T> clazz);

  /**
   * Gets the requested {@link ConfigurationSerializable} object by path, returning null if not
   * found.
   *
   * <p><b>Notes: </b>The {@link ClassCastException} won't be caught, instead it will return null.
   *
   * @param path  Path of the object.
   * @param clazz Class to get into.
   * @param <T>   Type of the object.
   * @return Requested serializable object.
   * @throws IllegalArgumentException            Thrown when path or type is null.
   * @throws ConfigurationSerializationException Thrown when class is not a serializable class or
   *                                             error when serialize object.
   */
  @Nullable <T> T getSerializable(@NotNull String path, @NotNull Class<T> clazz);

  /**
   * Gets the requested {@link ConfigurationSerializable} object by path, returning the default
   * value if not found.
   *
   * <p><b>Notes: </b>The {@link ClassCastException} won't be caught, instead it will return null.
   *
   * @param path  Path of the object.
   * @param clazz Class to get into.
   * @param def   The default object to return if the object is not present at the path
   * @param <T>   Type of the object.
   * @return Requested serializable object.
   * @throws IllegalArgumentException            Thrown when path or type is null.
   * @throws ConfigurationSerializationException Thrown when class is not a serializable class or
   *                                             error when serialize object.
   */
  @Nullable <T> T getSerializableOrDefault(@NotNull String path, @NotNull Class<T> clazz, T def);

  /**
   * Gets a set containing all the top layer keys in this {@link ConfigSection}.
   *
   * @return Shallow set of keys contained within this section.
   */
  @NotNull Set<String> getKeys();

  /**
   * Gets a set containing all keys in this {@link ConfigSection}.
   *
   * <p>If deep is set to true, then this will contain all the keys within any child {@link
   * ConfigSection}s (and their children, etc). These will be in a valid path notation for you to
   * use.
   *
   * <p>If deep is set to false, then this will contain only the top layer keys and not their own
   * children.
   *
   * @param deep Whether or not to get a deep list, as opposed to a shallow list.
   * @return Set of keys contained within this section.
   */
  @NotNull Set<String> getKeys(boolean deep);

  /**
   * Converts this {@link ConfigSection} to a map containing top player keys and their values in
   * this {@link ConfigSection}.
   *
   * @return Map of keys and values of this section.
   */
  @NotNull Map<String, ?> toFlatMap();

  /**
   * Converts this {@link ConfigSection} to a map containing all keys and their values in this
   * {@link ConfigSection}.
   *
   * <p>If deep is set to true, then this will contain all the keys within any child {@link
   * ConfigSection}s (and their children, etc). These will be in a valid path notation for you to
   * use.
   *
   * <p>If deep is set to false, then this will contain only the top layer keys and not their own
   * children.
   *
   * @param deep Whether or not to get a deep list, as opposed to a shallow list.
   * @return Map of keys and values of this section.
   */
  @NotNull Map<String, ?> toFlatMap(boolean deep);

  /**
   * Gets the name of this {@link ConfigSection}.
   *
   * <p>Returns an empty if {@link ConfigSection} constructs as {@link Configuration}. This will
   * always be the final part of {@link #getFullPath()}, unless the section is orphaned.
   *
   * @return Name of this section.
   */
  @NotNull String getName();

  /**
   * Gets the current path of this {@link ConfigSection}, from the root {@link Configuration}.
   *
   * <p>Returns an empty if {@link ConfigSection} constructs as {@link Configuration}.
   *
   * @return Path of this section.
   */
  @NotNull String getFullPath();

  /**
   * Gets the parent of this {@link ConfigSection}, returning null if this {@link ConfigSection}
   * constructs as root {@link Configuration}.
   *
   * @return Parent of this section.
   */
  @Nullable ConfigSection getParent();

  /**
   * Gets the root {@link Configuration} that contains this {@link ConfigSection}.
   *
   * <p>Returns themselves if {@link ConfigSection} constructs as {@link Configuration}.
   *
   * <p><b>Notes: </b>{@link ConfigSection} must always have a root {@link ConfigSection}.
   *
   * @return Root configuration containing this section.
   */
  @NotNull Configuration getRoot();
}
