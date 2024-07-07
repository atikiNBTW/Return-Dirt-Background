package com.atikinbtw.returndirtbackground;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnDirtBackground implements ClientModInitializer {
    private static final Identifier OPTIONS_BACKGROUND_TEXTURE = Identifier.of("textures/block/dirt.png");
    public static final Logger LOGGER = LoggerFactory.getLogger(ReturnDirtBackground.class);
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        // - means that this line has been changed from the default method
    }

    public static void renderBackgroundTexture(DrawContext context) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, 0, 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), 32, 32);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
