package xyz.tozymc.api.config.file;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tozymc.api.config.ConfigSection;
import xyz.tozymc.api.config.Configuration;
import xyz.tozymc.api.config.exception.ConfigurationException;
import xyz.tozymc.api.config.serialization.handler.ConfigurationSerializers;
import xyz.tozymc.api.config.util.Paths;
import xyz.tozymc.api.util.NumberConversations;
import xyz.tozymc.api.util.Preconditions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A type of {@link ConfigSection} that is handled with file.
 *
 * @author TozyMC
 * @since 1.0
 */
public class FileConfigSection implements ConfigSection {

  protected final Map<String, Object> values = new LinkedHashMap<>();
  private final FileConfiguration root;
  private final FileConfigSection parent;
  private final String name, path;

  protected FileConfigSection() {
    if (!(this instanceof FileConfiguration)) {
      throw new ConfigurationException("");
    }

    this.name = "";
    this.path = "";
    this.root = (FileConfiguration) this;
    this.parent = null;
  }

  public FileConfigSection(@NotNull FileConfigSection parent, @NotNull String name) {
    this.parent = Preconditions.checkNotNull(parent, "Parent cannot be null");
    this.root = parent.root;
    this.name = Preconditions.checkNotNull(name, "Name cannot be null");
    this.path = Paths.createPath(parent, name);
  }

  protected boolean contains0(String path) {
    if (path.isEmpty()) {
      return true;
    }
    int index = firsSeparatorIndex(path);
    if (index == -1) {
      return values.containsKey(path);
    }
    Object val = values.get(getShallowSection(path, index));
    if (!(val instanceof FileConfigSection)) {
      return false;
    }
    return ((FileConfigSection) val).contains0(getNextPath(path, index));
  }

  protected Object set0(String path, Object value) {
    if (path.isEmpty()) {
      return null;
    }

    int index = firsSeparatorIndex(path);
    if (index != -1) {
      String firstSec = getShallowSection(path, index);
      Object val = getAndValidateSectionObject(firstSec);
      FileConfigSection sec = val == null ? createSection0(firstSec) : (FileConfigSection) val;
      return sec.set0(getNextPath(path, index), value);
    }

    if (value == null) {
      return values.remove(path);
    }
    if (value instanceof Map) {
      return createSection(path, (Map<?, ?>) value);
    }
    return values.put(path, value);
  }

  protected Object get0(String path) {
    if (path.isEmpty()) {
      return null;
    }

    int index = firsSeparatorIndex(path);
    if (index == -1) {
      return values.get(path);
    }

    String firstSec = getShallowSection(path, index);
    Object val = getAndValidateSectionObject(firstSec);
    if (val == null) {
      return null;
    }
    return ((FileConfigSection) val).get0(getNextPath(path, index));
  }

  protected FileConfigSection createSection0(String path) {
    int index = firsSeparatorIndex(path);
    if (index == -1) {
      FileConfigSection section = new FileConfigSection(this, path);
      values.put(path, section);
      return section;
    }
    String firstSec = getShallowSection(path, index);
    Object val = getAndValidateSectionObject(firstSec);

    FileConfigSection sec;
    if (val == null) {
      sec = new FileConfigSection(this, firstSec);
      values.put(firstSec, sec);
    } else {
      sec = (FileConfigSection) val;
    }
    return sec.createSection0(getNextPath(path, index));
  }

  private <T> T getSerializable0(String path, Class<T> clazz) {
    if (!ConfigurationSerializers.isSerializableObject(clazz)) {
      return null;
    }
    Object val = get0(path);
    if (!(val instanceof Map)) {
      return null;
    }
    return ConfigurationSerializers.deserialize((Map<?, ?>) val, clazz);
  }

  private int firsSeparatorIndex(String path) {
    return path.indexOf(root.getSetting().pathSeparator());
  }

  private String getShallowSection(String path, int firstSeparatorIndex) {
    return path.substring(0, firstSeparatorIndex);
  }

  private String getNextPath(String path, int firstSeparatorIndex) {
    return path.substring(firstSeparatorIndex + 1);
  }

  private Object getAndValidateSectionObject(String shallowSection) {
    Object val = values.get(shallowSection);
    if (val != null && !(val instanceof FileConfigSection)) {
      throw new ConfigurationException(
          Paths.createPath(this, shallowSection) + " is not a StorageSection");
    }
    return val;
  }

  @Override
  public boolean contains(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    return contains0(path);
  }

  @Override
  public @Nullable Object getObject(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    return get0(path);
  }

  @Override
  public @Nullable Object getObject(@NotNull String path, Object def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val != null ? val : def;
  }

  @Override
  public boolean isByte(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Byte;
  }

  @Override
  public byte getByte(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return NumberConversations.toByte(val);
  }

  @Override
  public byte getByte(@NotNull String path, byte def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Number ? NumberConversations.toByte(val) : def;
  }

  @Override
  public boolean isInt(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Integer;
  }

  @Override
  public int getInt(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return NumberConversations.toInt(val);
  }

  @Override
  public int getInt(@NotNull String path, int def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Number ? NumberConversations.toInt(val) : def;
  }

  @Override
  public boolean isLong(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Long;
  }

  @Override
  public long getLong(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return NumberConversations.toLong(val);
  }

  @Override
  public long getLong(@NotNull String path, long def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Number ? NumberConversations.toLong(val) : def;
  }

  @Override
  public boolean isDouble(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Double;
  }

  @Override
  public double getDouble(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return NumberConversations.toDouble(val);
  }

  @Override
  public double getDouble(@NotNull String path, double def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Number ? NumberConversations.toDouble(val) : def;
  }

  @Override
  public boolean isBoolean(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Boolean;
  }

  @Override
  public boolean getBoolean(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Boolean ? (Boolean) val : false;
  }

  @Override
  public boolean getBoolean(@NotNull String path, boolean def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Boolean ? (Boolean) val : def;
  }

  @Override
  public boolean isChar(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Character;
  }

  @Override
  public char getChar(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Character ? (Character) val : 0;
  }

  @Override
  public char getChar(@NotNull String path, char def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof Character ? (Character) val : def;
  }

  @Override
  public boolean isString(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof String;
  }

  @Override
  public @NotNull String getString(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    return String.valueOf(get0(path));
  }

  @Override
  public @NotNull String getString(@NotNull String path, String def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof String ? String.valueOf(val) : def;
  }

  @Override
  public boolean isList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof List;
  }

  @Override
  public @Nullable List<?> getList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof List ? (List<?>) val : null;
  }

  @Override
  public @Nullable List<?> getList(@NotNull String path, List<?> def) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    return val instanceof List ? (List<?>) val : def;
  }

  @Override
  public @NotNull List<String> getStringList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream().map(String::valueOf).collect(Collectors.toList());
  }

  @Override
  public @NotNull List<Byte> getByteList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream()
        .filter(Number.class::isInstance)
        .map(NumberConversations::toByte)
        .collect(Collectors.toList());
  }

  @Override
  public @NotNull List<Integer> getIntegerList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream()
        .filter(Number.class::isInstance)
        .map(NumberConversations::toInt)
        .collect(Collectors.toList());
  }

  @Override
  public @NotNull List<Long> getLongList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream()
        .filter(Number.class::isInstance)
        .map(NumberConversations::toLong)
        .collect(Collectors.toList());
  }

  @Override
  public @NotNull List<Float> getFloatList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream()
        .filter(Number.class::isInstance)
        .map(NumberConversations::toFloat)
        .collect(Collectors.toList());
  }

  @Override
  public @NotNull List<Double> getDoubleList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream()
        .filter(Number.class::isInstance)
        .map(NumberConversations::toDouble)
        .collect(Collectors.toList());
  }

  @Override
  public @NotNull List<Boolean> getBooleanList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream()
        .filter(Boolean.class::isInstance)
        .map(Boolean.class::cast)
        .collect(Collectors.toList());
  }

  @Override
  public @NotNull List<Character> getCharacterList(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!(val instanceof List)) {
      return new ArrayList<>();
    }
    return ((List<?>) val).stream()
        .filter(Character.class::isInstance)
        .map(Character.class::cast)
        .collect(Collectors.toList());
  }

  @Override
  public @NotNull Optional<?> findObject(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    return Optional.ofNullable(get0(path));
  }

  @Override
  public @NotNull <T> Optional<T> find(@NotNull String path, @NotNull Class<T> clazz) {
    Preconditions.checkNotNull(path, "Path cannot be null");
    Preconditions.checkNotNull(clazz, "Clazz cannot be null");

    root.reload0();
    Object val = get0(path);
    return clazz.isInstance(val) ? Optional.of(clazz.cast(val)) : Optional.empty();
  }

  @Override
  public <T> @Nullable T get(@NotNull String path, @NotNull Class<T> clazz) {
    Preconditions.checkNotNull(path, "Path cannot be null");
    Preconditions.checkNotNull(clazz, "Clazz cannot be null");

    root.reload0();
    Object val = get0(path);
    if (!clazz.isInstance(val)) {
      return null;
    }
    return clazz.isInstance(val) ? clazz.cast(val) : null;
  }

  @Override
  public <T> @Nullable T getOrDefault(@NotNull String path, @NotNull Class<T> clazz, T def) {
    Preconditions.checkNotNull(path, "Path cannot be null");
    Preconditions.checkNotNull(clazz, "Clazz cannot be null");

    root.reload0();
    Object val = get0(path);
    return clazz.isInstance(val) ? clazz.cast(val) : def;
  }

  @Override
  public @NotNull Optional<?> set(@NotNull String path, Object value) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    Object oldVal = null;
    boolean isSerializableObj = ConfigurationSerializers.isSerializableObject(value);
    if (isSerializableObj) {
      oldVal = get0(path);

      createSection(path, ConfigurationSerializers.serialize(value));
    }

    if (!isSerializableObj) {
      oldVal = set0(path, value);
    }

    root.save0(value, oldVal);
    return Optional.ofNullable(oldVal);
  }

  @Override
  public boolean isSection(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    return get0(path) instanceof ConfigSection;
  }

  @Override
  public @NotNull Optional<ConfigSection> findSection(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);

    return val instanceof ConfigSection ? Optional.of((ConfigSection) val) : Optional.empty();
  }

  @Override
  public @Nullable ConfigSection getSection(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    root.reload0();
    Object val = get0(path);

    return val instanceof ConfigSection ? (ConfigSection) val : null;
  }

  @Override
  public @NotNull ConfigSection createSection(@NotNull String path) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    if (path.isEmpty()) {
      return this;
    }

    Object val = get0(path);
    if (val != null) {
      throw new ConfigurationException(Paths.createPath(this, path) + " already exists");
    }
    FileConfigSection sec = createSection0(path);
    root.save0();
    return sec;
  }

  @Override
  public @NotNull ConfigSection createSection(@NotNull String path, @NotNull Map<?, ?> values) {
    Preconditions.checkNotNull(path, "Path cannot be null");

    if (path.isEmpty()) {
      return this;
    }
    FileConfigSection sec = createSection0(path);
    values.forEach((key, val) -> sec.set0(String.valueOf(key), val));
    return sec;
  }

  @Override
  public @NotNull <T> Optional<T> findSerializable(@NotNull String path, @NotNull Class<T> clazz) {
    Preconditions.checkNotNull(path, "Path cannot be null");
    Preconditions.checkNotNull(clazz, "Clazz cannot be null");

    return Optional.ofNullable(getSerializable0(path, clazz));
  }

  @Override
  public <T> @Nullable T getSerializable(@NotNull String path, @NotNull Class<T> clazz) {
    Preconditions.checkNotNull(path, "Path cannot be null");
    Preconditions.checkNotNull(clazz, "Clazz cannot be null");

    return getSerializable0(path, clazz);
  }

  @Override
  public <T> @Nullable T getSerializableOrDefault(@NotNull String path, @NotNull Class<T> clazz,
      T def) {
    Preconditions.checkNotNull(path, "Path cannot be null");
    Preconditions.checkNotNull(clazz, "Clazz cannot be null");

    T val = getSerializable0(path, clazz);
    return val != null ? val : def;
  }

  protected void mapChildrenKeys(Set<String> output, FileConfigSection section, boolean deep) {
    section.values.forEach((key, value) -> {
      output.add(Paths.createPath(section, key, this));

      if (deep && value instanceof ConfigSection) {
        mapChildrenKeys(output, (FileConfigSection) value, true);
      }
    });
  }

  protected void mapChildrenValues(Map<String, Object> output, FileConfigSection section,
      boolean deep) {
    section.values.forEach((key, value) -> {
      output.put(Paths.createPath(section, key, this), value);

      if (deep && value instanceof ConfigSection) {
        mapChildrenValues(output, (FileConfigSection) value, true);
      }
    });
  }

  @Override
  public @NotNull Set<String> getKeys() {
    return getKeys(false);
  }

  @Override
  public @NotNull Set<String> getKeys(boolean deep) {
    Set<String> result = new LinkedHashSet<>();
    mapChildrenKeys(result, this, deep);
    return result;
  }

  @Override
  public @NotNull Map<String, ?> toFlatMap() {
    return toFlatMap(false);
  }

  @Override
  public @NotNull Map<String, ?> toFlatMap(boolean deep) {
    Map<String, Object> result = new LinkedHashMap<>();
    mapChildrenValues(result, this, deep);
    return result;
  }

  @Override
  public @NotNull String getName() {
    return name;
  }

  @Override
  public @NotNull String getFullPath() {
    return path;
  }

  @Override
  public @Nullable ConfigSection getParent() {
    return parent;
  }

  @Override
  public @NotNull Configuration getRoot() {
    return root;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[path=" + path + ", root=" + root + "]";
  }
}
