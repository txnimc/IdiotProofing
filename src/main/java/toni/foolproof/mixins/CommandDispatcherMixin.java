package toni.foolproof.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = com.mojang.brigadier.CommandDispatcher.class, remap = false)
public class CommandDispatcherMixin {

    @Redirect(
        method = "execute(Lcom/mojang/brigadier/ParseResults;)I",
        at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/exceptions/BuiltInExceptionProvider;dispatcherUnknownCommand()Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;")
    )
    private SimpleCommandExceptionType onExecute(BuiltInExceptionProvider instance, @Local(argsOnly = true) ParseResults<?> result) {
        return new SimpleCommandExceptionType(Component.literal("Unknown command! You may not have permission to use commands."));
    }
}