package LSDtools.player;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class checkIP implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent p) {
        //获取时间戳(joinTime)
//        final long joinTime = Calendar.getInstance().getTimeInMillis();
        //向控制台发送消息
        if (LSDtools.MainTool.getConfig().getBoolean("joinInfoConsole", true)) {
            Bukkit.getConsoleSender().sendMessage(LSDtools.pname + p.getPlayer().getName() + " §r§l| §b" + p.getPlayer().getAddress() + " §r§l| " + "§b首次加入: §e" + !p.getPlayer().hasPlayedBefore());
            return;
        }
        //向所有有 "LSDtools.notice" 权限的玩家发送玩家消息
        if (LSDtools.MainTool.getConfig().getBoolean("joinInfoOP", true)) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.hasPermission("LSDtools.notice")) {
                    player.sendMessage(LSDtools.pname + p.getPlayer().getName() + " §r§l| §b" + p.getPlayer().getAddress() + " §r§l| " + "§b首次加入: §e" + !p.getPlayer().hasPlayedBefore());
                }
            }
        }
    }
}