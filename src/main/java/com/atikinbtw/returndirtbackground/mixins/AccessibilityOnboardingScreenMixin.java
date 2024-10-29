package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.accessors.ScreenDrawablesAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AccessibilityOnboardingScreen.class)
public class AccessibilityOnboardingScreenMixin extends Screen {
    protected AccessibilityOnboardingScreenMixin(Text title) {
        super(title);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    public void render(Screen instance, DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderEdited(context, mouseX, mouseY, delta);
    }

    @Unique
    private void renderEdited(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderPanoramaBackground(context, delta);

        for (Drawable drawable : ((ScreenDrawablesAccessor) this).returndirtbackground$getDrawables()) {
            drawable.render(context, mouseX, mouseY, delta);
        }
    }
}