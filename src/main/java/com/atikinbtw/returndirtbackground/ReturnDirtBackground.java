package com.atikinbtw.returndirtbackground;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
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
    private static int textureWidth = 32;
    private static int textureHeight = 32;

    public static int getTextureWidth() {
        return textureWidth;
    }

    public static int getTextureHeight() {
        return textureHeight;
    }

    public static Identifier getBackgroundTexture() {
        return background;
    }

    public static void onReloadComplete() {
        if (client.getResourcePackManager().getEnabledIds().stream().anyMatch(id -> id.equals("high_contrast"))) {
            background = Screen.MENU_BACKGROUND_TEXTURE;
            textureWidth = 16;
            textureHeight = 16;
            return;
        }

        Map<Identifier, Resource> resourceMap = client.getResourceManager().findResources(Path.of("textures/gui").toString(), path -> path.toString().endsWith("options_background.png"));
        if (resourceMap.isEmpty()) {
            background = DIRT_TEXTURE;
            textureWidth = 32;
            textureHeight = 32;
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

        textureWidth = nativeImage.getWidth();
        textureHeight = nativeImage.getHeight();
        background = OPTIONS_BACKGROUND;
    }

    public static void renderBackgroundTexture(DrawContext context) {
        try {
            if (isNew) {
                setShaderColor(context, 0.25F, 0.25F, 0.25F, 1.0F);
                context.drawTexture(RenderLayer::getGuiTextured, getBackgroundTexture(), 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), textureWidth, textureHeight);
                setShaderColor(context, 1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                setShaderColor(context, 0.25F, 0.25F, 0.25F, 1.0F);
                drawTextureFunc.invoke(context, getBackgroundTexture(), 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), textureWidth, textureHeight);
                setShaderColor(context, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            LoggerFactory.getLogger(ReturnDirtBackground.class).error("Error while rendering background:", e);
        }
    }

    public static void renderBackgroundTexture(DrawContext context, Identifier background, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, float red, float green, float blue, float alpha) {
        try {
            if (isNew) {
                setShaderColor(context, red, green, blue, alpha);
                context.drawTexture(RenderLayer::getGuiTextured, background, x, y, u, v, width, height, textureWidth, textureHeight);
                setShaderColor(context, 1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                setShaderColor(context, red, green, blue, alpha);
                drawTextureFunc.invoke(context, background, x, y, u, v, width, height, textureWidth, textureHeight);
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
        String methodName = FabricLoader.getInstance().getMappingResolver().mapMethodName("intermediary", "net.minecraft.class_332", "method_25290", "(Ljava/util/function/Function;Lnet/minecraft/class_2960;IIFFIIII)V");
        try {
            // 1.21.3+
            drawTextureFunc = DrawContext.class.getDeclaredMethod(methodName, Function.class, Identifier.class, int.class, int.class, float.class, float.class, int.class, int.class, int.class, int.class);
            isNew = true;
        } catch (NoSuchMethodException e) {
            try {
                // <1.21.2
                drawTextureFunc = DrawContext.class.getDeclaredMethod(methodName, Identifier.class, int.class, int.class, float.class, float.class, int.class, int.class, int.class, int.class);
                isNew = false;
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException("Can't load drawTexture method, something is very bad with your game:", ex);
            }
        }
    }
}