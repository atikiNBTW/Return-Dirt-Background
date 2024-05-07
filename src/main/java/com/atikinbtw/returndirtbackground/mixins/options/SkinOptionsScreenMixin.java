package com.atikinbtw.returndirtbackground.mixins.options;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkinOptionsScreen.class)
public abstract class SkinOptionsScreenMixin extends GameOptionsScreen {

    public SkinOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    // decompiled from 1.20.4
    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    protected void init(CallbackInfo ci) {
        int i = 0;
        PlayerModelPart[] var2 = PlayerModelPart.values();

        for (PlayerModelPart playerModelPart : var2) {
            this.addDrawableChild(
                    CyclingButtonWidget.onOffBuilder(this.gameOptions.isPlayerModelPartEnabled(playerModelPart))
                            .build(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, playerModelPart.getOptionName(), (button, enabled)
                                    -> this.gameOptions.togglePlayerModelPart(playerModelPart, enabled))
            );
            ++i;
        }

        this.addDrawableChild(this.gameOptions.getMainArm().createWidget(this.gameOptions, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150));
        ++i;
        if (i % 2 == 1) {
            ++i;
        }

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) ->
                this.client.setScreen(this.parent))
                .dimensions(this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), 200, 20)
                .build()
        );
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "initTabNavigation", cancellable = true)
    protected void initTabNavigation(CallbackInfo ci) {
        ci.cancel();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);
    }
}
