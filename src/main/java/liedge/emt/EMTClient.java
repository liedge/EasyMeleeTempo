package liedge.emt;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

@Mod(value = EMTClient.MODID, dist = Dist.CLIENT)
public final class EMTClient
{
    public static final String MODID = "easymt";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EMTClient(IEventBus modBus, ModContainer modContainer)
    {
        modBus.addListener(FMLClientSetupEvent.class, event -> LOGGER.info("EasyMeleeTempo initialized."));
    }
}