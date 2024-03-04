package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MessageScreen.class)
public class MessageScreenMixin {
    @Unique
    private static final Identifier OPTIONS_BACKGROUND_TEXTURE = new Identifier("textures/block/dirt.png");

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }

    @Unique
    private void renderBackgroundTexture(DrawContext context) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, 0, 0, 0, 0.0F, 0.0F, ReturnDirtBackground.getClient().getWindow().getWidth(), ReturnDirtBackground.getClient().getWindow().getHeight(), 32, 32);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
