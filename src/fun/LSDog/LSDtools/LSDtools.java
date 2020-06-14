package fun.LSDog.LSDtools;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LSDtools extends JavaPlugin {
    @Override   public void onLoad() { getLogger().info(" 插件已加载！"); }
    @Override   public void onEnable() {
        saveDefaultConfig();
        getLogger().info(" 插件已启用！"); }
    @Override   public void onDisable() { getLogger().info(" 插件已关闭！"); }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        e.getPlayer().sendMessage(new String[] {
                "§b§l搭路练习 §7>> §e输入 §6/bridge §e可以更改练习参数哦~"
        });
    }
/*
    @EventHandler
    public void onJoin(final PlayerJoinEvent p) {
        p.getPlayer().sendMessage("§bLSDtools §7>> §b" + p.getPlayer().getName() + " | §c" +p.getPlayer().getAddress() + "§r 加入服务器" + "(自加入此插件起第 §e" + "NULL" + "§r 次)");
        getLogger().info("Player join");
            Bukkit.getConsoleSender().sendMessage("§bLSDtools §7>> §b" + p.getPlayer().getName() + " | §c" +p.getPlayer().getAddress() + "§r 加入服务器" + "(自加入此插件起第 §e" + "NULL" + "§r 次)");
                p.getPlayer().sendMessage("§bLSDtools §7>> §b" + p.getPlayer().getName() + " | §c" +p.getPlayer().getAddress() + "§r 加入服务器" + "(自加入此插件起第 §e" + "NULL" + "§r 次)");
    }
*/
}