package net.frozenblock.mz_informatica;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerLifecycleEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MZMod.MOD_ID)
public class MZMod {
    public static final String MOD_ID = "mz_informatica";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MZMod() {
        @SuppressWarnings("all")
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatEvent(ServerChatEvent event) {
        final var player = event.getPlayer();
        final var message = event.getMessage();
        event.setCanceled(true);
        final var msg = player.getDisplayName().copy().append(Component.literal(" : ")).append(message);
        LOGGER.debug(msg.toString());
        player.level().players().forEach(p -> p.sendSystemMessage(msg));
    }

    @SubscribeEvent
    public void onServerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        final var p = event.getEntity();
        if(p.getTeam() == null && p instanceof ServerPlayer player) {
            final var server = player.server;
            final var scoreboard = server.getScoreboard();
            final var team = scoreboard.getPlayerTeam("default");
            if(team != null) scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
        }
    }
}
