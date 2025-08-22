package toni.idiotproofing.features;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.damagesource.DamageTypes;
import toni.lib.utils.PlatformUtils;

import java.util.Set;

public class VoidGraveReminder {
    private static final Set<String> GRAVE_MODS = Set.of(
        "yigd",
        "corpse"
    );

    public static void init() {
        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            if (!damageSource.is(DamageTypes.FELL_OUT_OF_WORLD))
                return true;

            if (!GRAVE_MODS.stream().anyMatch(PlatformUtils::isModLoaded))
                return true;

            PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "Your grave is still accessible at Y=0!");
            var cmd = player.createCommandSourceStack();
            cmd.sendChatMessage(
                new OutgoingChatMessage.Player(chatMessage),
                false,
                ChatType.bind(ChatType.MSG_COMMAND_INCOMING, cmd.registryAccess(), Component.literal("The Void")));

            return true;
        });
    }
}
