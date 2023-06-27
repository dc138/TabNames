package me.DarthChungo.TabNames;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import net.fabricmc.loader.api.FabricLoader;

public class Config {
  public static final ConfigEntry DISPLAY = new ConfigEntry("display", "%player:displayname%");
  private static final ConfigEntry[] CONFIG = new ConfigEntry[] { DISPLAY };

  private final File file;

  private final static String filename = "tabnames.cfg";

  public Config() {
    file = FabricLoader.getInstance().getConfigDir().resolve(filename).toFile();
  }

  public void load() {
    List<String> lines = null;

    try {
      if (!file.exists()) {
        lines = defaultStrings();

        file.createNewFile();
        FileUtils.writeLines(file, lines);

      } else {
        lines = FileUtils.readLines(file, "UTF-8");
      }

    } catch (Exception e) {
      TabNames.logger.error("Cannot load config file");
      TabNames.logger.error(e.getStackTrace().toString());

      return;
    }

    lines.stream()
      .map(line -> parseLine(line))
      .filter(entry -> entry != null)
      .forEach(entry -> {
        find(entry.key).set(entry.value);
      });

    try {
      file.delete();

      file.createNewFile();
      FileUtils.writeLines(file, defaultStrings());

    } catch (Exception e) {
      TabNames.logger.error("Cannot load config file");
      TabNames.logger.error(e.getStackTrace().toString());

      return;
    }

    save();
  }

  public void save() {
    List<String> lines = null;

    try {
      lines = FileUtils.readLines(file, "UTF-8");

    } catch (Exception e) {
      TabNames.logger.error("Cannot save config file");
      TabNames.logger.error(e.getStackTrace().toString());

      return;
    }

    List<String> parsed = lines
      .stream()
      .map(line -> {
        ConfigEntry entry = parseLine(line);
        if (entry == null) return line;

        ConfigEntry existing = find(entry.key);
        return existing.toString();
      })
      .collect(Collectors.toList());

    try {
      FileUtils.writeLines(file, "UTF-8", parsed);

    } catch (Exception e) {
      TabNames.logger.error("Cannot save config file");
      TabNames.logger.error(e.getStackTrace().toString());
    }
  }

  private ConfigEntry parseLine(String line) {
      if (line.isEmpty() || line.startsWith("#")) {
        return null;
      }

      String[] split = line.split("=", 2);

      if (split.length != 2) {
        return null;
      }

      split[0] = split[0].trim();
      split[1] = split[1].stripLeading();

      ConfigEntry entry = new ConfigEntry(split[0], null);
      entry.set(split[1]);

      return entry;
  }

  private ConfigEntry find(String key) {
    return Arrays.stream(CONFIG)
      .filter(entry -> entry.key.equals(key))
      .findAny()
      .orElse(null);
  }

  public String get(String key) {
    ConfigEntry entry = find(key);
    return entry == null ? null : entry.get();
  }

  private List<String> defaultStrings() {
    return Arrays.asList(
      "# Choose how to display player names in the tab list",
      "# Supports Placeholder API",
      "display = %player:displayname%"
    );
  }
}
