package com.atikinbtw.returndirtbackground;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ReturnDirtBackground implements ClientModInitializer {
    private static Identifier OPTIONS_BACKGROUND_TEXTURE;
    public static final Logger LOGGER = LoggerFactory.getLogger(ReturnDirtBackground.class);
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        // - this line has been changed from the default method
        LOGGER.info("Initialized!");

        if (isNewEnough()) {
            OPTIONS_BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/block/dirt.png");
        } else {
            try {
                OPTIONS_BACKGROUND_TEXTURE = (Identifier) createOldClass("net.minecraft.util.Identifier", "of", "textures/block/dirt.png");
            } catch (Exception e) {
                LOGGER.error("Something went wrong, please report to developers!");
            }
        }
    }

    public static void renderBackgroundTexture(DrawContext context) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, 0, 0, 0, 0.0F, 0.0F, client.getWindow().getWidth(), client.getWindow().getHeight(), 32, 32);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static Object createOldClass(String className, String methodName, String identifier) throws Exception {
        Class<?> clazz = Class.forName(className);
        LOGGER.info(String.valueOf(clazz));
        Method method = clazz.getMethod(methodName);
        LOGGER.info(String.valueOf(method));
        Object instance = clazz.getDeclaredConstructor().newInstance(identifier);
        LOGGER.info(String.valueOf(instance));
        return method.invoke(instance, identifier);
    }

    // returns true if the version has new Identifier class
    public static boolean isNewEnough() {
        String version = client.getGameVersion();

        return version.startsWith("1.21");
    }
}