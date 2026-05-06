package liedge.emt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = EMTClient.MODID)
public final class EMTEventHandler
{
    private EMTEventHandler() { }

    @SubscribeEvent
    private static void onClientSwing(final InputEvent.InteractionKeyMappingTriggered event)
    {
        if (event.isAttack())
        {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.getAttackStrengthScale(0f) < 1f)
            {
                event.setCanceled(true);
                event.setSwingHand(false);
            }
        }
    }
}