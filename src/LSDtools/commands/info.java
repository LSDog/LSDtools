package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class info implements CommandExecutor {
    //单独的命令类需要继承CommandExecutor来执行命令
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            sender.sendMessage("§r>> §e§lLSDtools §a§lv " + LSDtools.pversion + " §r<<");
            sender.sendMessage("§a/LSDtools: §r插件信息");
            sender.sendMessage("§a/checkplayer: §r检查某玩家信息");
            sender.sendMessage("§a/checkplayers: §r检查所有玩家信息");
        return true;
    }
}