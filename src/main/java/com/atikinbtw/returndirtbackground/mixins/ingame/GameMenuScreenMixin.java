package com.atikinbtw.returndirtbackground.mixins.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    @Shadow @Final private boolean showMenu;

    private GameMenuScreenMixin(Text title) {
        super(title);
    }

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.showMenu)
            this.renderInGameBackground(context);
    }
}