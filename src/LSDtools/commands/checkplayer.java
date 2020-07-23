package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class checkplayer implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            //参数不足的提示
            if (args.length < 1) {
                sender.sendMessage(LSDtools.pname + "参数不足 - /checkplayer <player>");
                return false;
            }
            //参数过多的提示
            if (args.length > 1) {
                sender.sendMessage(LSDtools.pname + "参数过多 - /checkplayer <player>");
                return false;
            }
            //查询某一玩家
            if (sender.getServer().getPlayer(args[0]) != null) {
                //若第一个参数中玩家存在，则发送信息
                Bukkit.getPlayer(args[0]);
                sender.sendMessage(LSDtools.pname + sender.getServer().getPlayer(args[0]).getName() + " §r§l| §b" + sender.getServer().getPlayer(args[0]).getAddress() + " §r§l| " + "§b首次加入: §e" + !sender.getServer().getPlayer(args[0]).hasPlayedBefore());
                return true;
                } else {
                //否则提示没有此玩家
                    sender.sendMessage(LSDtools.pname + "§c没有找到该玩家！");
                    return false;
            }
    }
}
