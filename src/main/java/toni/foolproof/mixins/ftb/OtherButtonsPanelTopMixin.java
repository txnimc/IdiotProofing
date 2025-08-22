package toni.foolproof.mixins.ftb;

import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftbquests.client.gui.quests.OtherButtonsPanelTop;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = { @Condition("ftbquests") })
@Mixin(value = OtherButtonsPanelTop.class, remap = false)
public class OtherButtonsPanelTopMixin {

    @Redirect(method = "addWidgets", at = @At(value = "INVOKE", ordinal = 1, target = "Ldev/ftb/mods/ftbquests/client/gui/quests/OtherButtonsPanelTop;add(Ldev/ftb/mods/ftblibrary/ui/Widget;)V"))
    private void removeAutoPin(OtherButtonsPanelTop instance, Widget widget) {

    }
}
