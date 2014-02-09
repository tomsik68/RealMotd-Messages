package sk.tomsik68.realmotd.msgs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastMessageTask implements Runnable {
    private final String[] messageLines;

    public BroadcastMessageTask(String[] messageLines) {
        this.messageLines = messageLines;
    }

    @Override
    public void run() {
        broadcast(messageLines);
    }

    private void broadcast(String... messages) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(messages);
    }

}
