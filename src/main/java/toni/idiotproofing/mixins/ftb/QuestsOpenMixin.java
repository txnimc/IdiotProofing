package toni.idiotproofing.mixins.ftb;

import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.idiotproofing.IdiotProofing;
import toni.immersivemessages.ImmersiveMessagesManager;
import toni.immersivemessages.api.ImmersiveMessage;

@Mixin(value = ClientQuestFile.class, remap = false)
public class QuestsOpenMixin {

    @Inject(method = "openGui()Ldev/ftb/mods/ftbquests/client/gui/quests/QuestScreen;", at = @At(value = "HEAD"))
    private static void openGui(CallbackInfoReturnable<QuestScreen> cir) {
        IdiotProofing.DATA.seenWarnings.add("quests");
    }
}
