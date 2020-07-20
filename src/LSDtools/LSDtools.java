package LSDtools;

import LSDtools.commands.checkplayer;
import LSDtools.commands.checkplayers;
import LSDtools.commands.info;
import LSDtools.player.checkIP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;

public final class LSDtools extends JavaPlugin {

    //设置插件前缀
    public static final String pname = "§e§lLSDtools §7>> §b";
    //设置插件版本
    public static final double pversion = 0.2;
    //方法: 获取时间戳
    public static final long startTime = Calendar.getInstance().getTimeInMillis();

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
        getCommand("checkplayer").setExecutor(new checkplayer());
        getCommand("checkplayers").setExecutor(new checkplayers());
        //注册事件
        Bukkit.getPluginManager().registerEvents(new checkIP(),this);
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件成功加载完毕");
        //提示信息
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "开服时间戳:" + startTime);
    }

    //插件关闭
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件已关闭");
    }
}