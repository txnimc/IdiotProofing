package toni.idiotproofing.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.commands.Commands.class)
public class CommandsMixin {

    @Redirect(
        method = "getParseException",
        at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/exceptions/BuiltInExceptionProvider;dispatcherUnknownCommand()Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;")
    )
    private static SimpleCommandExceptionType onExecute(BuiltInExceptionProvider instance, @Local(argsOnly = true) ParseResults<?> result) {
        return new SimpleCommandExceptionType(Component.literal("Unknown command! You may not have permission to use commands"));
    }
}