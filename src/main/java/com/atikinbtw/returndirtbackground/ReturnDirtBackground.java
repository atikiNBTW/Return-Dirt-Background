package com.atikinbtw.returndirtbackground;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.util.Identifier;

public class ReturnDirtBackground implements ClientModInitializer {
    private static final Identifier DIRT_TEXTURE = Identifier.of("textures/block/dirt.png");
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        // - means that this line has been changed from the default method
    }

    public static void renderBackgroundTexture(DrawContext context) {
        Identifier texture;
        if (client.getResourcePackManager().getEnabledIds().contains("high_contrast")) {
            texture = Screen.MENU_BACKGROUND_TEXTURE;
        } else {
            texture = DIRT_TEXTURE;
        }

        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(texture, 0, 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), 32, 32);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
