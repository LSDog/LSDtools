package LSDtools;

import LSDtools.commands.checkplayers;
import LSDtools.commands.info;
import LSDtools.player.checkIP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class LSDtools extends JavaPlugin {

    //设置插件前缀
    public static final String pname = "§e§lLSDtools §7>> §b";
    //设置插件版本
    public static final double pversion = 0.1;

    //插件加载
    @Override
    public void onLoad() {
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD+"[LSDtools] " + ChatColor.YELLOW + "插件已加载");
    }

    //插件启用
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件已启用");
        //生成配置
        saveDefaultConfig();
        //注册命令
        getCommand("LSDtools").setExecutor(new info());
        getCommand("checkplayers").setExecutor(new checkplayers());
        //注册事件
        Bukkit.getPluginManager().registerEvents(new checkIP(),this);
    }


    //插件关闭
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件已关闭");
    }
}