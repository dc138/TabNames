package me.DarthChungo.TabNames;

public class ConfigEntry {
  public String key = null;
  public String value = null;
  public String def = null;

  ConfigEntry(String k, String d) {
    key = k;
    def = d;
  }

  void set(String v) {
    value = v;
  }

  String get() {
    return value == null ? def : value;
  }

  public String toString() {
    return key + " = " + get();
  }
}
