package com.atikinbtw.returndirtbackground.mixins.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementsScreenMixin extends Screen {
    @Shadow protected abstract void drawAdvancementTree(DrawContext context, int mouseX, int mouseY, int x, int y);
    @Shadow public abstract void drawWindow(DrawContext context, int x, int y);
    @Shadow protected abstract void drawWidgetTooltip(DrawContext context, int mouseX, int mouseY, int x, int y);

    protected AdvancementsScreenMixin(Text title) {
        super(title);
    }

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderInGameBackground(context); //
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;
        this.drawAdvancementTree(context, mouseX, mouseY, i, j);
        this.drawWindow(context, i, j);
        this.drawWidgetTooltip(context, mouseX, mouseY, i, j);
    }
}
