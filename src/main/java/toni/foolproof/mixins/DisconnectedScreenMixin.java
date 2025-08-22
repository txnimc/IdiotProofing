package toni.foolproof.mixins;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import toni.foolproof.Foolproof;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;

#if mc >= 211
import net.minecraft.network.DisconnectionDetails;
#endif

@Mixin(DisconnectedScreen.class)
public class DisconnectedScreenMixin {


    //@Inject(method = "<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V", at = @At("RETURN"))

    #if mc >= 211
    @Shadow @Final @Mutable
    private DisconnectionDetails details;

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/LinearLayout;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
    private <T extends LayoutElement> T onInit(LinearLayout instance, T child) {
    #else
    @Shadow @Final @Mutable
    private Component reason;

    @Redirect(method = "init", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
    private <T extends LayoutElement> T onInit(GridLayout.RowHelper instance, T child) {

    #endif
        try {
            #if mc >= 211
            var messages = ((TranslatableContents) details.reason().getContents()).getArgs();
            var reasonObj = details.reason();
            #else
            var messages = ((TranslatableContents) reason.getContents()).getArgs();
            var reasonObj = reason;
            #endif

            if (messages.length == 0)
                return null;

            var message = messages[0].toString();
            if (message.equals("Connection refused: getsockopt")) {

                var screen = ((DisconnectedScreen) (Object) this);

                var title = Component.literal("This usually means the server is down, or the network is blocking it!");

                instance.addChild(new MultiLineTextWidget(title, Minecraft.getInstance().font)
                    .setMaxWidth(screen.width - 50)
                    .setCentered(true));

                var newReason = Component.literal("▪ Check if your server is visible to others with ")
                    .append(Component.literal("https://mcsrvstat.us/")
                        .withStyle(ChatFormatting.AQUA)
                        .withStyle(ChatFormatting.UNDERLINE)
                        .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mcsrvstat.us/"))))
                    .append("\n▪ If it's not reachable, make sure the port is open!")
                    .append("\n▪ If it is up, check your internet connection and firewall settings!");

                var widget = new MultiLineTextWidget(reasonObj, Minecraft.getInstance().font)
                    .setMaxWidth(screen.width - 50);

                #if mc >= 211
                details = new DisconnectionDetails(newReason, details.report(), details.bugReportLink());
                #else
                reason = newReason;
                #endif

                return (T) instance.addChild(widget);
            }

        } catch (Exception e) {
            Foolproof.LOGGER.error(e);
        }

        return instance.addChild(child);
    }
}
