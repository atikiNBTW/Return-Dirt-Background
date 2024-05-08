package com.atikinbtw.returndirtbackground.mixins.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SocialInteractionsScreen.class)
public abstract class SocialInteractionsScreenMixin extends Screen {
    @Shadow @Final private static Identifier SEARCH_ICON_TEXTURE;
    @Shadow @Final private static Identifier BACKGROUND_TEXTURE;

    @Shadow protected abstract int getSearchBoxX();
    @Shadow protected abstract int getScreenHeight();

    protected SocialInteractionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "renderBackground", cancellable = true)
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        super.renderInGameBackground(context); //
        int i = this.getSearchBoxX() + 3;
        context.drawGuiTexture(BACKGROUND_TEXTURE, i, 64, 236, this.getScreenHeight() + 16);
        context.drawGuiTexture(SEARCH_ICON_TEXTURE, i + 10, 76, 12, 12);

        ci.cancel();
    }

}
