package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Screen.class)
public class ScreenMixin {
    /**
     * @author atikiNBTW
     * @reason to return the behavior of < 1.20.5
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        ReturnDirtBackground.renderBackgroundTexture(context);
    }
}