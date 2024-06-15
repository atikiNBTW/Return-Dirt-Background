package com.atikinbtw.returndirtbackground.mixins;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.*;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    @Shadow @Final private ThreePartsLayoutWidget layout;

    @Shadow @Final private static Text SKIN_CUSTOMIZATION_TEXT;
    @Shadow @Final private static Text SOUNDS_TEXT;
    @Shadow @Final private static Text VIDEO_TEXT;
    @Shadow @Final private static Text LANGUAGE_TEXT;
    @Shadow @Final private static Text CHAT_TEXT;
    @Shadow @Final private static Text RESOURCE_PACK_TEXT;
    @Shadow @Final private static Text ACCESSIBILITY_TEXT;
    @Shadow @Final private static Text CREDITS_AND_ATTRIBUTION_TEXT;
    @Shadow @Final private static Text CONTROL_TEXT;
    @Shadow @Final private static Text TELEMETRY_TEXT;
    @Shadow @Final private GameOptions settings;
    @Shadow @Final private static Text TITLE_TEXT;

    @Shadow @Final private static Tooltip TELEMETRY_DISABLED_TOOLTIP;

    @Shadow protected abstract ButtonWidget createButton(Text message, Supplier<Screen> screenSupplier);
    @Shadow protected abstract Widget createTopRightButton();

    @Shadow protected abstract void refreshResourcePacks(ResourcePackManager resourcePackManager);

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    protected void init(CallbackInfo ci) {
        DirectionalLayoutWidget directionalLayoutWidget = layout.addHeader(DirectionalLayoutWidget.vertical()); //
        directionalLayoutWidget.getMainPositioner().marginBottom(20); //
        directionalLayoutWidget.add(new TextWidget(TITLE_TEXT, this.textRenderer), Positioner::alignHorizontalCenter);

        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter(); //
        GridWidget.Adder adder = gridWidget.createAdder(2);
        adder.add(this.settings.getFov().createWidget(this.client.options)); //
        adder.add(this.createTopRightButton()); //
        adder.add(EmptyWidget.ofHeight(26), 2); //

        adder.add(this.createButton(SKIN_CUSTOMIZATION_TEXT, () -> new SkinOptionsScreen(this, settings)));
        adder.add(this.createButton(SOUNDS_TEXT, () -> new SoundOptionsScreen(this, this.settings)));
        adder.add(this.createButton(VIDEO_TEXT, () -> new VideoOptionsScreen(this, this.client, this.settings)));
        adder.add(this.createButton(CONTROL_TEXT, () -> new ControlsOptionsScreen(this, this.settings)));
        adder.add(this.createButton(LANGUAGE_TEXT, () -> new LanguageOptionsScreen(this, this.settings, this.client.getLanguageManager())));
        adder.add(this.createButton(CHAT_TEXT, () -> new ChatOptionsScreen(this, this.settings)));
        adder.add(this.createButton(RESOURCE_PACK_TEXT, () -> new PackScreen(this.client.getResourcePackManager(), this::refreshResourcePacks, this.client.getResourcePackDir(), Text.translatable("resourcePack.title"))));
        adder.add(this.createButton(ACCESSIBILITY_TEXT, () -> new AccessibilityOptionsScreen(this, this.settings)));
        ButtonWidget buttonWidget = adder.add(this.createButton(TELEMETRY_TEXT, () -> new TelemetryInfoScreen(this, this.settings)));
        if (!this.client.isTelemetryEnabledByApi()) {
            buttonWidget.active = false;
            buttonWidget.setTooltip(TELEMETRY_DISABLED_TOOLTIP);
        }
        adder.add(this.createButton(CREDITS_AND_ATTRIBUTION_TEXT, () -> new CreditsAndAttributionScreen(this)));
        adder.add(ButtonWidget.builder(ScreenTexts.DONE, (button) -> this.close()).width(200).build(), 2, adder.copyPositioner().marginTop(6)); //

        this.layout.addBody(gridWidget);
        this.layout.forEachChild(this::addDrawableChild);
        this.initTabNavigation();

        ci.cancel();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client.world != null)
            super.renderInGameBackground(context);

        ReturnDirtBackground.renderBackgroundTexture(context);
    }
}
