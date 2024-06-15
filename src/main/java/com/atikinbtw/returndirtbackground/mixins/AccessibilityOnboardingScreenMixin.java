package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AccessibilityOnboardingScreen.class)
public abstract class AccessibilityOnboardingScreenMixin extends Screen {

    @Shadow protected abstract void tickNarratorPrompt();

    @Shadow @Final private LogoDrawer logoDrawer;

    protected AccessibilityOnboardingScreenMixin(Text title) {
        super(title);
    }

    /**
     * @author atikiNBTW
     * @reason to render dirt image across all screen
     */
    @Overwrite
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        super.renderPanoramaBackground(context, delta); //
        this.tickNarratorPrompt();
        this.logoDrawer.draw(context, this.width, 1.0f);
    }
}
