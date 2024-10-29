package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import com.atikinbtw.returndirtbackground.accessors.ScreenDrawablesAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenDrawablesAccessor {
    @Shadow @Final private List<Drawable> drawables;

    /**
     * @author atikiNBTW
     * @reason to return the behavior of < 1.20.5
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        ReturnDirtBackground.renderBackgroundTexture(context);
    }

    @Override
    public List<Drawable> returndirtbackground$getDrawables() {
        return this.drawables;
    }
}