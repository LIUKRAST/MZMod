package net.frozenblock.mz_informatica.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.frozenblock.mz_informatica.MZMod;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;

public class ImpersonateCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("impersonate")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(3))
                .then(Commands.argument("name", StringArgumentType.word())
                        .then(Commands.argument("color", ColorArgument.color())
                                .then(Commands.argument("message", MessageArgument.message())
                                        .executes(context -> execute(
                                                context.getSource(),
                                                StringArgumentType.getString(context, "name"),
                                                ColorArgument.getColor(context, "color"),
                                                MessageArgument.getMessage(context, "message")))
                                )
                        )
                        .then(Commands.argument("message", MessageArgument.message())
                                .executes(context -> execute(
                                        context.getSource(),
                                        StringArgumentType.getString(context, "name"),
                                        ChatFormatting.GRAY,
                                        MessageArgument.getMessage(context, "message"))))
                )
        );
    }

    public static int execute(CommandSourceStack sourceStack, String name, ChatFormatting color, Component message) {
        final var msg = Component.literal("[+] " + name).withStyle(color).append(Component.literal(" : ").withStyle(ChatFormatting.WHITE)).append(Component.literal(message.getString()).withStyle(ChatFormatting.GRAY));
        MZMod.LOGGER.info("Sending fake message as ${}, with color ${}", name, color);
        sourceStack.getServer().getPlayerList().broadcastSystemMessage(msg, false);

        return 1;
    }
}
