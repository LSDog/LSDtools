package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class info implements CommandExecutor {
    //单独的命令类需要继承CommandExecutor来执行命令，若要实现TAB补全则继承TabExecutor

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        //插件的信息
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("§r>> §e§lLSDtools §a§lv " + LSDtools.pversion + " §r<< §7by LSDog.");
            sender.sendMessage("§a/LSDtools: §r插件信息");
            sender.sendMessage("§a/lore: §r修改物品信息");
            sender.sendMessage("§a/crash: §r使某玩家崩溃");
            sender.sendMessage("§a/signedit: §r修改告示牌");
            sender.sendMessage("§a/gettools: §r获取一些小工具");
            sender.sendMessage("§a/checkplayer: §r检查某玩家信息");
            sender.sendMessage("§a/LSDtools reload: §r重载插件");
        }
        //重载插件
        else if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(LSDtools.pname + "重载中...");
            File config = new File(LSDtools.MainTool.getDataFolder() + File.separator + "config.yml");
            if (!config.exists()) { //如果文件不存在就创建
                LSDtools.MainTool.getConfig().options().copyDefaults(true); //从自己那里复制过去....
                LSDtools.MainTool.saveDefaultConfig(); //保存默认的配置
            } else {
                LSDtools.MainTool.reloadConfig(); //有的话就重载配置好了
            }
            sender.sendMessage(LSDtools.pname + "重载完毕！");
            return true;
        }
        //参数过多的提示
        else if (args.length > 1) {
            sender.sendMessage(LSDtools.pname + "参数过多");
            return false;
        }
        //保险起见
        else sender.sendMessage(LSDtools.pname + "发生了未知的错误");
        return false;
    }
}