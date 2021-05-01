package xyz.tozymc.api.config.file;

import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;

import java.io.Reader;
import java.util.Map;

public final class YamlProvider {

  private static final LoadSettings loadSettings = LoadSettings.builder()
      .setAllowDuplicateKeys(false)
      .setLabel("config")
      .build();
  private static final Load load = new Load(loadSettings);
  private static final DumpSettings dumpSettings = DumpSettings.builder()
      .setDefaultFlowStyle(FlowStyle.BLOCK)
      .build();
  private static final Dump dump = new Dump(dumpSettings);

  private YamlProvider() {}

  protected static Map<?, ?> load(Reader reader) {
    return (Map<?, ?>) load.loadFromReader(reader);
  }

  protected static String dump(Object object) {
    return dump.dumpToString(object);
  }
}
