package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpv implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§5/tpv <ID> 隐身传送到某玩家");
            sender.sendMessage("§5/tpv #stop 解除隐身");
        } else if (!(sender instanceof Player)) { //确保执行者是玩家
            sender.sendMessage(LSDtools.pname + "只有玩家能做这个!");
            return true;
        } else if (args[0].equalsIgnoreCase("#stop")) {
            Player s = Bukkit.getPlayer(sender.getName());
            for (Player player : Bukkit.getServer().getOnlinePlayers()) { // 对所有玩家显示指令发送者
                player.showPlayer(s);
            }
            s.sendMessage(LSDtools.pname + "取消了对任何人的隐身！");
            return true;
        } else if (Bukkit.getPlayer(args[0]) != null) {
            Player p = Bukkit.getPlayer(args[0]);
            Player s = Bukkit.getPlayer(sender.getName());
            Location loc = new Location(p.getWorld(),p.getLocation().getX(),p.getLocation().getY() + 1,p.getLocation().getZ(),s.getLocation().getYaw(),s.getLocation().getPitch());
            for (Player player : Bukkit.getServer().getOnlinePlayers()) { // 对所有非OP玩家隐身
                if (!player.isOp()) {
                    player.hidePlayer(s);
                }
            }
            s.setFlying(true);
            s.teleport(loc);
            s.sendMessage(LSDtools.pname + "成功隐身传送至 " + p.getName() + " !");
        } else {
            sender.sendMessage("§c没有找到该玩家!");
        }
        return true;
    }
}
