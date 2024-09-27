package com.atikinbtw.returndirtbackground.mixins.resourcepacks;

import com.atikinbtw.returndirtbackground.ReturnDirtBackground;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "method_29339", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;complete(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
    public void onReloadComplete(CallbackInfo ci) {
        ReturnDirtBackground.onReloadComplete();
    }

    @Inject(method = "method_29338", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;onFinishedLoading(Lnet/minecraft/client/MinecraftClient$LoadingContext;)V", shift = At.Shift.BEFORE))
    public void reloadOnStart(CallbackInfo ci) {
        ReturnDirtBackground.onReloadComplete();
    }
}