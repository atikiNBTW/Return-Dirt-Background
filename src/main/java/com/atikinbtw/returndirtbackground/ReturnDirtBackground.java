package com.atikinbtw.returndirtbackground;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

public class ReturnDirtBackground implements ClientModInitializer {
    private static final Identifier DIRT_TEXTURE = Screen.MENU_BACKGROUND_TEXTURE.withPath("textures/block/dirt.png"); // backwards compatible identifier
    private static final Identifier OPTIONS_BACKGROUND = Screen.MENU_BACKGROUND_TEXTURE.withPath("textures/gui/options_background.png"); // backwards compatible identifier
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static Method drawTextureFunc;
    private static boolean isNew;
    private static Identifier background = DIRT_TEXTURE;
    private static int width = 32;
    private static int height = 32;

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static Identifier getBackgroundTexture() {
        return background;
    }

    public static void onReloadComplete() {
        if (client.getResourcePackManager().getEnabledIds().stream().anyMatch(id -> id.equals("high_contrast"))) {
            background = Screen.MENU_BACKGROUND_TEXTURE;
            width = 16;
            height = 16;
            return;
        }

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
        try {
            if (isNew) {
                setShaderColor(context, 0.25F, 0.25F, 0.25F, 1.0F);
                context.drawTexture(RenderLayer::getGuiTextured, getBackgroundTexture(), 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), width, height);
                setShaderColor(context, 1.0F, 1.0F, 1.0F, 1.0F);
                //drawTextureFunc.invoke(context, RenderLayer::getGuiTextured, getBackgroundTexture(), 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), width, height);
            } else {
                setShaderColor(context, 0.25F, 0.25F, 0.25F, 1.0F);
                drawTextureFunc.invoke(context, getBackgroundTexture(), 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), width, height);
                setShaderColor(context, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            LoggerFactory.getLogger(ReturnDirtBackground.class).error("Error while rendering background:", e);
        }
    }

    private static void setShaderColor(DrawContext context, float red, float green, float blue, float alpha) {
        context.draw();
        RenderSystem.setShaderColor(red, green, blue, alpha);
    }

    @Override
    public void onInitializeClient() {
        try {
            // 1.21.3+
            drawTextureFunc = DrawContext.class.getDeclaredMethod("method_25290", Function.class, Identifier.class, int.class, int.class, float.class, float.class, int.class, int.class, int.class, int.class);
            isNew = true;
        } catch (NoSuchMethodException e) {
            try {
                // <1.21.1
                drawTextureFunc = DrawContext.class.getDeclaredMethod("method_25290", Identifier.class, int.class, int.class, float.class, float.class, int.class, int.class, int.class, int.class);
                isNew = false;
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException("Can't load drawTexture method, something is very bad with your game:", ex);
            }
        }
    }
}