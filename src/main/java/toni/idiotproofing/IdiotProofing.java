package toni.idiotproofing;

import lombok.SneakyThrows;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import toni.idiotproofing.features.VoidGraveReminder;
import toni.idiotproofing.foundation.IdiotProofingPersistentData;
import toni.idiotproofing.foundation.config.AllConfigs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.LifeCycle;

#if FABRIC
    import net.fabricmc.api.ClientModInitializer;
    import net.fabricmc.api.ModInitializer;
    #if after_21_1
    import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
    import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
    import net.neoforged.neoforge.client.gui.ConfigurationScreen;
    #endif

    #if current_20_1
    import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
    #endif
#endif

#if FORGE
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
#endif


#if NEO
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
#endif


#if FORGELIKE
@Mod("idiotproofing")
#endif
public class IdiotProofing #if FABRIC implements ModInitializer, ClientModInitializer #endif
{
    public static final String MODNAME = "Idiot-proofing";
    public static final String ID = "idiotproofing";
    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(MODNAME);
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final AtomicLong serverStartTime = new AtomicLong(0);

    public static IdiotProofingPersistentData DATA;

    public IdiotProofing(#if NEO IEventBus modEventBus, ModContainer modContainer #endif) {
        #if FORGE
        var context = FMLJavaModLoadingContext.get();
        var modEventBus = context.getModEventBus();
        #endif

        #if FORGELIKE
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        AllConfigs.register((type, spec) -> {
            #if FORGE
            ModLoadingContext.get().registerConfig(type, spec);
            #elif NEO
            modContainer.registerConfig(type, spec);
            #endif
        });
        #endif
    }

    public static boolean hasSeenWarning(String warning) {
        return DATA.seenWarnings.contains(warning);
    }


    #if FABRIC @Override #endif
    @SneakyThrows
    public void onInitialize() {
        #if FABRIC
            AllConfigs.register((type, spec) -> {
                #if AFTER_21_1
                NeoForgeConfigRegistry.INSTANCE.register(IdiotProofing.ID, type, spec);
                #else
                ForgeConfigRegistry.INSTANCE.register(IdiotProofing.ID, type, spec);
                #endif
            });
        #endif

        VoidGraveReminder.init();
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
            DATA.save();
        });

        try {
            Field log4jField = org.apache.logging.slf4j.Log4jLogger.class.getDeclaredField("logger");
            log4jField.setAccessible(true);
            Logger log4jLogger = (Logger) log4jField.get(MinecraftServer.LOGGER);

            log4jLogger.addFilter(new AbstractFilter() {
                @Override
                public Result filter(LogEvent event) {
                    String msg = event.getMessage().getFormattedMessage();
                    if (msg != null && msg.contains("Can't keep up!")) {
                        long elapsed = System.currentTimeMillis() - serverStartTime.get();
                        if (serverStartTime.get() == 0 || elapsed < 120_000) {
                            return Result.DENY;
                        }
                    }
                    return Result.NEUTRAL;
                }
            });
        } catch (Exception e) {
            LOGGER.warn("Could not install log filter:");
            LOGGER.warn(e.getMessage());
        }
    }

    #if FABRIC @Override #endif
    public void onInitializeClient() {
        DATA = IdiotProofingPersistentData.load();

        #if AFTER_21_1
            #if FABRIC
            ConfigScreenFactoryRegistry.INSTANCE.register(IdiotProofing.ID, ConfigurationScreen::new);
            #endif
        #endif

        ClientEvents.init();
    }

    private void onServerStarted(MinecraftServer server) {
        serverStartTime.set(System.currentTimeMillis());
        scheduler.schedule(() -> {
            System.out.println("[SERVER] The server is fully started and ready to join!");
        }, 5, TimeUnit.SECONDS);
    }

    // Forg event stubs to call the Fabric initialize methods, and set up cloth config screen
    #if FORGELIKE
    public void commonSetup(FMLCommonSetupEvent event) { onInitialize(); }
    public void clientSetup(FMLClientSetupEvent event) { onInitializeClient(); }
    #endif
}
