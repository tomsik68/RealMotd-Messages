package sk.tomsik68.realmotd.msgs;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import sk.tomsik68.realmotd.Util;
import sk.tomsik68.realmotd.api.FilesManager;
import sk.tomsik68.realmotd.api.MotdManager;

public class MessageListener implements Listener {
    private final MotdManager manager;
    private final FilesManager firstJoin, join, quit;

    public MessageListener(File dataFolder, FilesManager firstJoinMessages, FilesManager joinMessages, FilesManager quitMessages, MotdManager manager) {
        firstJoin = firstJoinMessages;
        join = joinMessages;
        quit = quitMessages;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore() && firstJoin != null) {
            event.setJoinMessage("");
        } else {
            if(join != null){
                event.setJoinMessage("");
                
            }
        }
    }

    

    // This function allows for multiple lines in join/quit message.
    private void broadcast(String... messages) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(messages);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }

}
