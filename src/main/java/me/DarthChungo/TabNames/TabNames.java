package me.DarthChungo.TabNames;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.fabricmc.api.ModInitializer;

public class TabNames implements ModInitializer {
  public static final Logger logger = LogUtils.getLogger();

  @Override
  public void onInitialize() {
    logger.info("Loading TabNames version v1.0.0+1.20.1");
  }
}
