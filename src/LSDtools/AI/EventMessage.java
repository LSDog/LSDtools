package LSDtools.AI;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EventMessage implements Listener {
    String AInameRE = "§r§l[§r§bSABI§r§l]§r ";

    // 加入游戏
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!LSDtools.MainTool.getConfig().getBoolean("AI", false)) {
            return;
        }
        new BukkitRunnable(){
            @Override
            public void run(){
                String onJoin_name = e.getPlayer().getName();
                if (!e.getPlayer().hasPlayedBefore()){
                    Bukkit.broadcastMessage(AInameRE + "欢迎新玩家" + onJoin_name + " ~");
                } else {
                    int i = (int)(1+Math.random()*(5-1+1));
                    String joinMessage = null;
                    if (i == 1) {joinMessage = "欢迎回来！ " + onJoin_name + "~";}
                    if (i == 2) {joinMessage = "呦, " + onJoin_name + "!";}
                    if (i == 3) {joinMessage = "来啦 " + onJoin_name + "?";}
                    if (i == 4) {joinMessage = "hey " + onJoin_name;}
                    if (i == 5) {joinMessage = "欢迎 " + onJoin_name + "~";}
                    Bukkit.broadcastMessage(AInameRE + joinMessage);
                }
            }
        }.runTaskLater(LSDtools.MainTool,60);
    }

    // 死亡事件
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!LSDtools.MainTool.getConfig().getBoolean("AI", false)) {
            return;
        }
        new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.broadcastMessage(AInameRE + "RIP " + e.getEntity().getPlayer().getName() + ".");
            }
        }.runTaskLater(LSDtools.MainTool,40);
    }

}
