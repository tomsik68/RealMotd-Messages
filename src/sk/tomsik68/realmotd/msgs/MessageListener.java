package sk.tomsik68.realmotd.msgs;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import sk.tomsik68.realmotd.RealMotd;
import sk.tomsik68.realmotd.Util;
import sk.tomsik68.realmotd.api.FilesManager;
import sk.tomsik68.realmotd.api.groups.GroupsRegistry;

public class MessageListener implements Listener {
    private final FilesManager join, firstJoin, leave;
    private final ConfigSection joinSection, fjSection, leaveSection;
    private final GroupsRegistry groups;

    public MessageListener(RealMotdMessages plugin, ConfigFile config) {
        groups = RealMotdMessages.getRealMotd().getGroupRegistry();
        joinSection = config.getSection("join");
        join = new FilesManager(joinSection, plugin.getDataFolder(), "join", "join", "txt");
        fjSection = config.getSection("first-join");
        firstJoin = new FilesManager(fjSection, plugin.getDataFolder(), "firstjoin", "firstjoin", "txt");
        leaveSection = config.getSection("quit");
        leave = new FilesManager(leaveSection, plugin.getDataFolder(), "leave", "leave", "txt");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore() && fjSection.isEnabled()) {
            event.setJoinMessage("");
            sendMessage(firstJoin, fjSection, event.getPlayer());
        } else if (joinSection.isEnabled()) {
            event.setJoinMessage("");
            sendMessage(join, joinSection, event.getPlayer());
        }
    }

    public void sendMessage(FilesManager files, ConfigSection cfg, Player player) {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        String message = RealMotd.handler.addVariables(
                Util.readFile(files.getMotdFile(cfg.getMode(), groups.getGroupName(player), player.getWorld().getName(), month, day)), player,
                RealMotdMessages.getRealMotd());

        if (cfg.getDelay() > 0) {
            player.getServer().getScheduler()
                    .runTaskLaterAsynchronously(RealMotdMessages.getInstance(), new BroadcastMessageTask(message.split("\n")), cfg.getDelay());
        } else {
            broadcast(message.split("/n"));
        }

    }

    private void broadcast(String... messages) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(messages);

    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        if (leaveSection.isEnabled()) {
            event.setQuitMessage("");
            sendMessage(leave, leaveSection, event.getPlayer());
        }
    }

}
