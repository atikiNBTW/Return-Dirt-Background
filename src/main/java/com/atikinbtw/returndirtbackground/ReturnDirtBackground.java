package com.atikinbtw.returndirtbackground;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class ReturnDirtBackground implements ClientModInitializer {
    private static final Identifier DIRT_TEXTURE = Screen.MENU_BACKGROUND_TEXTURE.withPath("textures/block/dirt.png"); // backwards compatible identifier
    private static final Identifier OPTIONS_BACKGROUND = Screen.MENU_BACKGROUND_TEXTURE.withPath("textures/gui/options_background.png"); // backwards compatible identifier
    private static Identifier background = DIRT_TEXTURE;
    private static int width = 32;
    private static int height = 32;
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        // - means that this line has been changed from the default method
    }

    public static Identifier getBackgroundTexture() {
        return background;
    }

    public static void onReloadComplete() {
        Map<Identifier, Resource> resourceMap = client.getResourceManager().findResources(Path.of("textures/gui").toString(), path -> path.toString().endsWith("options_background.png"));
        if (resourceMap.isEmpty()) {
            background = DIRT_TEXTURE;
            width = 32;
            height = 32;
            return;
        }
        Resource resource = resourceMap.entrySet().iterator().next().getValue();

        NativeImage nativeImage;
        try {
            nativeImage = NativeImage.read(resource.getInputStream());
        } catch (IOException e) {
            LoggerFactory.getLogger(ReturnDirtBackground.class).error("Failed to load background image", e);
            return;
        }

        width = nativeImage.getWidth();
        height = nativeImage.getHeight();
        background = OPTIONS_BACKGROUND;
    }

    public static void renderBackgroundTexture(DrawContext context) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(getBackgroundTexture(), 0, 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), width, height);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}