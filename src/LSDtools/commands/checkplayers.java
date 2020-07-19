package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class checkplayers implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("LSDtools.check")) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                sender.sendMessage(LSDtools.pname + "在线玩家的信息: ");
                sender.sendMessage(LSDtools.pname + player.getPlayer().getName() + " §r§l| §b" + player.getPlayer().getAddress() + " §r§l| " + "§b首次加入: §e" + !player.getPlayer().hasPlayedBefore());
            }
        }
        return true;
    }
}
