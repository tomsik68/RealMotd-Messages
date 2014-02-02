package sk.tomsik68.realmotd.msgs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessageListener implements Listener {
    public MessageListener() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO: add -- &&doesHandleFirstJoin
        if (!event.getPlayer().hasPlayedBefore()) {
            // first join
        } else {
            // regular join
        }
    }

    public void onPlayerQuit(PlayerQuitEvent event) {

    }

}
