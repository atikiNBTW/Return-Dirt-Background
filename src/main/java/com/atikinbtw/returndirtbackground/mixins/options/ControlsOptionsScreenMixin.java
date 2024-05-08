package com.atikinbtw.returndirtbackground.mixins.options;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.screen.option.MouseOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenMixin extends GameOptionsScreen {

    public ControlsOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    // decompiled from 1.20.4
    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    protected void init(CallbackInfo ci) {
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("options.mouse_settings"), (button) -> {
            this.client.setScreen(new MouseOptionsScreen(this, this.gameOptions));
        }).dimensions(i, k, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("controls.keybinds"), (button) -> {
            this.client.setScreen(new KeybindsScreen(this, this.gameOptions));
        }).dimensions(j, k, 150, 20).build());

        k += 24;
        this.addDrawableChild(this.gameOptions.getSneakToggled().createWidget(this.gameOptions, i, k, 150));
        this.addDrawableChild(this.gameOptions.getSprintToggled().createWidget(this.gameOptions, j, k, 150));

        k += 24;
        this.addDrawableChild(this.gameOptions.getAutoJump().createWidget(this.gameOptions, i, k, 150));
        this.addDrawableChild(this.gameOptions.getOperatorItemsTab().createWidget(this.gameOptions, j, k, 150));

        k += 24;
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 100, k, 200, 20).build());

        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "initTabNavigation", cancellable = true)
    protected void initTabNavigation(CallbackInfo ci) {
        ci.cancel();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);
    }
}
