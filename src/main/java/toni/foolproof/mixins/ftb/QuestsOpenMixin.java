package toni.foolproof.mixins.ftb;

import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.foolproof.Foolproof;

@Mixin(value = ClientQuestFile.class, remap = false)
public class QuestsOpenMixin {

    @Inject(method = "openGui()Ldev/ftb/mods/ftbquests/client/gui/quests/QuestScreen;", at = @At(value = "HEAD"))
    private static void openGui(CallbackInfoReturnable<QuestScreen> cir) {
        Foolproof.DATA.seenWarnings.add("quests");
    }
}
