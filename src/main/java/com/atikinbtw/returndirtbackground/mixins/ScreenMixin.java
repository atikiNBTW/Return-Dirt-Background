package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        ReturnDirtBackground.renderBackgroundTexture(context); //
    }
}