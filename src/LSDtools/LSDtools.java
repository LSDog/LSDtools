package LSDtools;

import LSDtools.player.checkIP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        //生成配置
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件已启用");
        //注册命令
        Bukkit.getPluginCommand("LSDtools").setExecutor(this);
        //注册事件
        getServer().getPluginManager().registerEvents(new checkIP(),this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("LSDtools")) {
            // 判断输入的指令是否是 LSDtools
            if (!(sender instanceof Player)) {
                // 判断输入者的类型 为了防止出现 控制台或命令方块 输入的情况
                sender.sendMessage("player only!");
                return true;
                // 这里返回true只是因为该输入者不是玩家,并不是输入错指令,所以我们直接返回true即可
            }
            // 如果我们已经判断好sender是一名玩家之后,我们就可以将其强转为Player对象,把它作为一个"玩家"来处理
            Player player = (Player) sender;
            player.sendMessage("§r>> §e§lLSDtools §a§lv " + LSDtools.pversion + " §r<<");
            player.sendMessage("§a/LSDtools: §rinfo of LSDtools" );
            return true; // 返回true防止返回指令的usage信息
        }
        return false;
    }

    //插件关闭
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[LSDtools] " + ChatColor.YELLOW + "插件已关闭");
    }
}