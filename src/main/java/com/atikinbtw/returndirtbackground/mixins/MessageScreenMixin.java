package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MessageScreen.class)
public class MessageScreenMixin extends Screen {
    protected MessageScreenMixin(Text title) {
        super(title);
    }

    /**
     * @author atikiNBTW
     * @reason to return the behavior of < 1.20.5
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        ReturnDirtBackground.renderBackgroundTexture(context);
    }

    /**
     * @author atikiNBTW
     * @reason to return the behavior of < 1.20.5
     */
    @Overwrite
    public void init() {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 70, 16777215);
    }
}