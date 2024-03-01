package com.atikinbtw.returnbackgrounds;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnBackgrounds implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnBackgrounds.class);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello Fabric world!");
    }
}
