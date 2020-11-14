package LSDtools;

import LSDtools.AI.message;
import LSDtools.commands.*;
import LSDtools.player.checkIP;
import LSDtools.world.signedit;
import LSDtools.world.tools.tpTool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class LSDtools extends JavaPlugin {
    //主类 extends JavaPlugin

    //设置插件前缀
    public static final String pname = "§e§lLSDtools §7>> §b";
    //设置插件版本
    public static final double pversion = 0.38;
    //将插件主类储存起来以供其他类访问
    public static LSDtools MainTool;

    public LSDtools() {
        MainTool = this;
    }

    //插件启用
    @Override
    public void onEnable() {
        //生成配置
        File config = new File(getDataFolder() + File.separator + "config.yml");
        File data = new File(getDataFolder() + File.separator + "data.yml");
        if (!config.exists() || getConfig().getDouble("version") != 0.3) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
        if(!data.exists()){
            saveResource("data.yml", true);
        }
        //注册命令
        getCommand("LSDtools").setExecutor(new info());
        getCommand("checkplayer").setExecutor(new checkplayer());
        getCommand("gettools").setExecutor(new gettools());
        getCommand("lore").setExecutor(new lore());
        getCommand("signedit").setExecutor(new signedit());
        getCommand("crash").setExecutor(new crashplayer());
        //注册事件
        Bukkit.getPluginManager().registerEvents(new checkIP(),this);
        Bukkit.getPluginManager().registerEvents(new tpTool(), this);
        Bukkit.getPluginManager().registerEvents(new message(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件成功加载完毕");
    }

    //插件关闭
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件已关闭");
    }
}