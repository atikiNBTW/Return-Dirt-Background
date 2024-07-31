package com.atikinbtw.returndirtbackground.mixins.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DemoScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DemoScreen.class)
public class DemoScreenMixin extends Screen {
    @Shadow @Final private static Identifier DEMO_BG;

    protected DemoScreenMixin(Text title) {
        super(title);
    }

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderInGameBackground(context);
        int i = (this.width - 248) / 2;
        int j = (this.height - 166) / 2;
        context.drawTexture(DEMO_BG, i, j, 0, 0, 248, 166);
    }
}