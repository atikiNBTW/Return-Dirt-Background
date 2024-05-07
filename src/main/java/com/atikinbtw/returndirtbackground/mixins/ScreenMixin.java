package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow
    protected MinecraftClient client;

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!(this.client.currentScreen instanceof GameMenuScreen) || isInGameAndInOptions()) {
            ReturnDirtBackground.renderBackgroundTexture(context);
        }
    }

    @Unique
    private boolean isInGameAndInOptions() {
        return this.client.currentScreen instanceof OptionsScreen && this.client.world == null;
    }
}