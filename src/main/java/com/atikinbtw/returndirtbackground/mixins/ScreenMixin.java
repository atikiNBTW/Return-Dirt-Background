package com.atikinbtw.returndirtbackground.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Screen.class)
public class ScreenMixin {
    @Unique
    private static final Identifier OPTIONS_BACKGROUND_TEXTURE = new Identifier("textures/block/dirt.png");
    @Shadow
    protected MinecraftClient client;
    @Shadow
    public int width, height;

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!(this.client.currentScreen instanceof GameMenuScreen) || isInGameAndInOptions()) {
            this.renderBackgroundTexture(context);
        }
    }

    // method from old version to render dirt image across all the screen
    @Unique
    private void renderBackgroundTexture(DrawContext context) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Unique
    private boolean isInGameAndInOptions() {
        return this.client.currentScreen instanceof OptionsScreen && this.client.world == null;
    }
}