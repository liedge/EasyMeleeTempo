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
                AttackMode mode = EMTConfig.INSTANCE.attackMode.get();
                boolean cancelAttack = switch (mode)
                {
                    case DISABLED -> false;
                    case SHIFT_TO_ENABLE -> player.isShiftKeyDown();
                    case SHIFT_TO_DISABLE -> !player.isShiftKeyDown();
                    case ALWAYS_ON -> true;
                };

                if (cancelAttack)
                {
                    event.setCanceled(true);
                    event.setSwingHand(false);
                }
            }
        }
    }
}