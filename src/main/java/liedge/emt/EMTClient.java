package liedge.emt;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(value = EMTClient.MODID, dist = Dist.CLIENT)
public final class EMTClient
{
    public static final String MODID = "easymt";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EMTClient(IEventBus modBus, ModContainer modContainer)
    {
        modBus.addListener(FMLClientSetupEvent.class, event -> LOGGER.info("EasyMeleeTempo initialized."));

        modContainer.registerConfig(ModConfig.Type.CLIENT, EMTConfig.INSTANCE.getSpec());

        NeoForge.EVENT_BUS.addListener(RegisterClientCommandsEvent.class, event ->
                EMTConfig.registerCommands(event.getDispatcher()));
    }
}