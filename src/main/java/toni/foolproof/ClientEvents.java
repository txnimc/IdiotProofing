package toni.foolproof;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import toni.immersivemessages.api.ImmersiveMessage;
import toni.lib.animation.Binding;
import toni.lib.animation.easing.EasingType;
import toni.lib.utils.PlatformUtils;

public class ClientEvents {

    public static void init() {
        ClientPlayConnectionEvents.JOIN.register((clientPacketListener, packetSender, minecraft) -> {
            if (PlatformUtils.isModLoaded("ftbquests") && !Foolproof.hasSeenWarning("quests")) {
                var message = ImmersiveMessage.builder(26f, "You can open Quests from the book in the top-left of your inventory!");

                message.animation.transition(Binding.Alpha, 15f, 16f, 0.0F, 1.0F, EasingType.EaseOutSine);
                message.animation.transition(Binding.Alpha, 25f, 26f, 1.0F, 0.0F, EasingType.EaseOutSine);

                message.wrap();
                message.sendLocal(minecraft.player);
            }
        });
    }
}
