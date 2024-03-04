package com.atikinbtw.returndirtbackground;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnDirtBackground implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnDirtBackground.class);
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello Fabric world!");
    }
    public static MinecraftClient getClient() {
        return client;
    }
}
