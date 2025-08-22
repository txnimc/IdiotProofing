//package toni.foolproof.mixins.ftb;
//
//import me.fallenbreath.conditionalmixin.api.annotation.Condition;
//import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Restriction(require = { @Condition("ftbquests") })
//@Mixin(targets = "dev.ftb.mods.ftbquests.quest.TeamData$PerPlayerData")
//public class QuestScreenMixin {
//
//    @Shadow(remap = false) private boolean chapterPinned;
//
//    @Inject(method = "<init>()V", at = @At("RETURN"))
//    private void onInit(CallbackInfo ci) {
//        chapterPinned = true;
//    }
//
//    @Inject(method = "<init>(ZZZLit/unimi/dsi/fastutil/longs/LongSet;)V", at = @At("RETURN"))
//    private void onInit2(CallbackInfo ci) {
//        chapterPinned = true;
//    }
//}
