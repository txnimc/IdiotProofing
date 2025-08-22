package toni.foolproof.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toni.lib.utils.PlatformUtils;

@Mixin(CreateWorldScreen.WorldTab.class)
public abstract class CreateWorldScreenMixin {
    @Shadow @Final
    CreateWorldScreen field_42182;

    @Inject(method = "method_48672(Lnet/minecraft/client/gui/components/CycleButton;Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;)V",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void onConstructed(CycleButton<WorldCreationUiState.WorldTypeEntry> arg, WorldCreationUiState.WorldTypeEntry arg2, CallbackInfo ci) {
        if (!PlatformUtils.isModLoaded("bclib"))
            return;

        Minecraft.getInstance().setScreen(new ConfirmScreen(
            confirmed -> {
                if (confirmed) {
                    field_42182.getUiState().setWorldType(arg2);
                }

                Minecraft.getInstance().setScreen(field_42182);
                arg.setValue(field_42182.getUiState().getWorldType());
            },
            Component.literal("Changing World Type May Break World Generation!"),
            Component.literal("You have BetterEnd or BetterNether installed, changing the world type is not recommended! Are you sure you want to change it?"),
            Component.literal("I'm sure!"),
            Component.literal("Cancel")
        ));

        ci.cancel();
    }
}