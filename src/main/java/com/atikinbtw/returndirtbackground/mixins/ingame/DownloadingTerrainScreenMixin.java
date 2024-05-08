package com.atikinbtw.returndirtbackground.mixins.ingame;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DownloadingTerrainScreen.class)
public abstract class DownloadingTerrainScreenMixin extends Screen {
    @Shadow @Final private DownloadingTerrainScreen.WorldEntryReason worldEntryReason;
    @Shadow protected abstract Sprite getBackgroundSprite();

    protected DownloadingTerrainScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "renderBackground", cancellable = true)
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        switch (this.worldEntryReason) {
            case OTHER: {
                ReturnDirtBackground.renderBackgroundTexture(context);
                break;
            }
            case NETHER_PORTAL: {
                context.drawSprite(0, 0, -90, context.getScaledWindowWidth(), context.getScaledWindowHeight(), this.getBackgroundSprite());
                break;
            }
            case END_PORTAL: {
                context.fillWithLayer(RenderLayer.getEndPortal(), 0, 0, this.width, this.height, 0);
            }
        }

        ci.cancel();
    }
}
