package LSDtools.player;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Calendar;

public class checkIP implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent p) {
        final long joinTime = Calendar.getInstance().getTimeInMillis();
        Bukkit.getConsoleSender().sendMessage(LSDtools.pname + p.getPlayer().getName() + " §r§l| §b" + p.getPlayer().getAddress() + " §r§l| " + "§b首次加入: §e" + !p.getPlayer().hasPlayedBefore());
        //TODO 记录加入次数
        //向所有有 "LSDtools.notice" 权限的玩家发送玩家消息
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("LSDtools.notice")) {
                player.sendMessage(LSDtools.pname + p.getPlayer().getName() + " §r§l| §b" + p.getPlayer().getAddress() + " §r§l| " + "§b首次加入: §e" + !p.getPlayer().hasPlayedBefore());
            }
        }
    }
}