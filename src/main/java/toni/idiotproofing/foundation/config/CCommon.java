package toni.idiotproofing.foundation.config;

import toni.lib.config.ConfigBase;

#if FABRIC
#if after_21_1
    import net.neoforged.fml.config.ModConfig;
    import net.neoforged.neoforge.common.ModConfigSpec;
    import net.neoforged.neoforge.common.ModConfigSpec.*;
    #else
import net.minecraftforge.common.ForgeConfigSpec.*;
    #endif
#endif

#if FORGE
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.fml.config.ModConfig;
#endif

#if NEO
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.*;
#endif

public class CCommon extends ConfigBase {

    public final CValue<String, ConfigValue<String>> discord_url = new CValue<>("Discord URL", builder -> builder.define("Discord URL", ""), "URL for support, to direct users to.");

    @Override
    public String getName() {
        return "common";
    }
}
