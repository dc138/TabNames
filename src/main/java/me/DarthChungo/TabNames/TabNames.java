package me.DarthChungo.TabNames;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.fabricmc.api.ModInitializer;

public class TabNames implements ModInitializer {
  public static final Logger logger = LogUtils.getLogger();

  public static TabNames instance = null;
  public static Config config = null;

  @Override
  public void onInitialize() {
    logger.info("Loading TabNames version v1.0.0+1.20.1");

    if (instance != null) {
      logger.error("Mod has already been loaded, please restart the server");
    }

    instance = this;
    config = new Config();

    config.load();
  }
}
