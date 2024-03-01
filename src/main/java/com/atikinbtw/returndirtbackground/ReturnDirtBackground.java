package com.atikinbtw.returndirtbackground;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnDirtBackground implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnDirtBackground.class);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello Fabric world!");
    }

    public static Logger getLogger() {
        return LOGGER;
    };
}
