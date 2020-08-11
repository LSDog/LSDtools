package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class crashplayer implements CommandExecutor {
    public static boolean isNumeric(String string){ //正整数判断
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 2) { //没有参数则介绍
            sender.sendMessage(LSDtools.pname + "用法: /crash <序号> <玩家>");
            sender.sendMessage(LSDtools.pname + "1. §r过大的高度导致崩端");
            return true;
        } else if (args.length > 2) {
                sender.sendMessage(LSDtools.pname + "参数过多 - /crash <序号> <玩家>");
                return true;
        } else if (!isNumeric(args[0])) {
            sender.sendMessage(LSDtools.pname + "§c错误: 这不是一个合法的数");
            return true;
        } else if (Bukkit.getPlayer(args[1]) == null) {
            sender.sendMessage(LSDtools.pname + "未找到此玩家!");
            return true;
        }

        Player p = Bukkit.getPlayer(args[1]);
        int arg0 = Integer.parseInt(args[0]);
        if (arg0 == 0.1) { //TODO 寻找更美妙的崩端方法
            Location loc = new Location(p.getWorld(),p.getLocation().getX(),9223372036854775807L,p.getLocation().getY());
            p.teleport(loc);
            sender.sendMessage(LSDtools.pname + "成功执行崩端命令~");
            while (p.isOnline()) {
                p.teleport(loc);
            }
            return true;
        } else {
            sender.sendMessage(LSDtools.pname + "§c请输入正确的序号!");
        }
        return true;
    }
}
