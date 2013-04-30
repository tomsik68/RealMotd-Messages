package sk.tomsik68.realmotd.msgs;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import sk.tomsik68.realmotd.Util;
import sk.tomsik68.realmotd.api.MotdManager;

public class MessageListener implements Listener {
    private final MotdManager manager;
    private final String joinMessage;
    private final String quitMessage;

    public MessageListener(File dataFolder, boolean join, boolean quit, MotdManager manager) {
        this.manager = manager;
        joinMessage = join ? Util.readFile(new File(dataFolder, "join.txt")) : "";
        quitMessage = quit ? Util.readFile(new File(dataFolder, "quit.txt")) : "";
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (joinMessage.length() > 0) {
            event.setJoinMessage("");
            broadcast(manager.addVariables(joinMessage, event.getPlayer(), RealMotdMessages.getRealMotd()).split("/n"));
        }
    }
    // This function allows for multiple lines in join/quit message.
    private void broadcast(String... messages) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(messages);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (quitMessage.length() > 0) {
            event.setQuitMessage("");
            broadcast(manager.addVariables(quitMessage, event.getPlayer(), RealMotdMessages.getRealMotd()).split("/n"));
        }
    }

}
