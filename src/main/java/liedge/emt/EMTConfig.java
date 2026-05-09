package liedge.emt;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.server.command.EnumArgument;

public final class EMTConfig
{
    public static final EMTConfig INSTANCE = new EMTConfig();

    public final ModConfigSpec.EnumValue<AttackMode> attackMode;
    private final ModConfigSpec spec;

    private EMTConfig()
    {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        attackMode = builder.defineEnum("mode", AttackMode.ALWAYS_ON);
        spec = builder.build();
    }

    ModConfigSpec getSpec()
    {
        return spec;
    }

    static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        var masterCommand = Commands.literal(EMTClient.MODID)
                .then(Commands.literal("mode").then(Commands.argument("mode", EnumArgument.enumArgument(AttackMode.class)).executes(EMTConfig::switchMode)))
                .then(Commands.literal("current").executes(EMTConfig::viewMode));

        dispatcher.register(masterCommand);
    }

    private static int switchMode(CommandContext<CommandSourceStack> context)
    {
        AttackMode newMode = context.getArgument("mode", AttackMode.class);
        AttackMode currentMode = INSTANCE.attackMode.get();

        if (newMode != currentMode)
        {
            INSTANCE.attackMode.set(newMode);
            INSTANCE.attackMode.save();
            context.getSource().sendSystemMessage(Component.translatable("msg.easymt.switch_success", newMode.name()));
        }
        else
        {
            context.getSource().sendFailure(Component.translatable("msg.easymt.switch_fail", currentMode.name()));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int viewMode(CommandContext<CommandSourceStack> context)
    {
        AttackMode mode = INSTANCE.attackMode.get();
        context.getSource().sendSystemMessage(Component.translatable("msg.easymt.current", mode.name()));
        return Command.SINGLE_SUCCESS;
    }
}