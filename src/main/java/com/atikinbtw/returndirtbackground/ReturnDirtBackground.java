package com.atikinbtw.returndirtbackground;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

public class ReturnDirtBackground implements ClientModInitializer {
    private static final Identifier DIRT_TEXTURE = Screen.MENU_BACKGROUND_TEXTURE.withPath("textures/block/dirt.png"); // backwards compatible identifier
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        // - means that this line has been changed from the default method
    }

    public static Identifier getBackgroundTexture() {
        if (client.getResourcePackManager().getEnabledIds().contains("high_contrast")) {
            return Screen.MENU_BACKGROUND_TEXTURE;
        } else {
            return DIRT_TEXTURE;
        }
    }

    public static void renderBackgroundTexture(DrawContext context) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(getBackgroundTexture(), 0, 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), 32, 32);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
