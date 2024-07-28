package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreditsScreen.class)
public class CreditsScreenMixin extends Screen {

    @Shadow private float time;
    @Shadow @Final private float baseSpeed;
    @Shadow private int creditsHeight;

    protected CreditsScreenMixin(Text title) {
        super(title);
    }

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        int i = this.width;
        float f = this.time * 0.5F;
        boolean j = true;
        float g = this.time / this.baseSpeed;
        float h = g * 0.02F;
        float k = (float)(this.creditsHeight + this.height + this.height + 24) / this.baseSpeed;
        float l = (k - 20.0F - g) * 0.005F;
        if (l < h) {
            h = l;
        }

        if (h > 1.0F) {
            h = 1.0F;
        }

        h *= h;
        h = h * 96.0F / 255.0F;
        context.setShaderColor(h, h, h, 1.0F);
        context.drawTexture(ReturnDirtBackground.getBackgroundTexture(), 0, 0, 0, 0.0F, f, i, this.height, 64, 64);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
