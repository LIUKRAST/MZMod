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

//Annotazione che specifica "questa classe è una mod con mod id: "mz_informatica" "
@Mod(MZMod.MOD_ID)
public class MZMod {
    //Variabile pubblica e static dell'id, così che può essere usata da tutte le parti
    public static final String MOD_ID = "mz_informatica";
    //Il logger, per inviare errori etc
    private static final Logger LOGGER = LogUtils.getLogger();

    //Costruttore della mod
    public MZMod() {
        @SuppressWarnings("all") //Nascondiamo i warning della seguente variabile
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //Registriamo questa classe come listener per gli eventi di forge
        MinecraftForge.EVENT_BUS.register(this);
    }

    //Evento che viene chiamato quando inviamo un messaggio
    @SubscribeEvent
    public void onChatEvent(ServerChatEvent event) {
        final var player = event.getPlayer(); //Otteniamo il player che ha inviato il messaggio
        final var message = event.getMessage(); //Otteniamo il messaggio
        event.setCanceled(true); //Cancelliamo l'evento (Non inviamo il messaggio)
        // Creiamo un messaggio custom abbellito
        final var msg = player.getDisplayName().copy().append(Component.literal(" : ")).append(message);
        //Inviamo ai log il messaggio, così che venga visto dalla console
        LOGGER.debug(msg.toString());
        //Iteriamo per ogni player del server e inviamo il messaggio ad ognuno
        player.level().players().forEach(p -> p.sendSystemMessage(msg));
    }

    //Evento che viene chiamato quando un player entra nel server
    @SubscribeEvent
    public void onServerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        //Prendiamo il player
        final var p = event.getEntity();
        //Se il player non è in un team
        if(p.getTeam() == null && p instanceof ServerPlayer player) {
            //Prendiamo server, scoreboard e il team "default", e assegnamo il team al player
            final var server = player.server;
            final var scoreboard = server.getScoreboard();
            final var team = scoreboard.getPlayerTeam("default");
            if(team != null) scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
        }
    }
}
