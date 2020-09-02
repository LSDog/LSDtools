package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

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
            sender.sendMessage(LSDtools.pname + "1. §r超大容器 §7[踢出]");
            sender.sendMessage(LSDtools.pname + "2. §r假死 §7[踢出]");
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
        if (arg0 == 1) {
            p.openInventory(Bukkit.createInventory(p, 999999));
            sender.sendMessage(LSDtools.pname + "成功执行崩端命令~");
            return true;
        } else if (arg0 == 2) {
            p.playEffect(EntityEffect.DEATH);
            sender.sendMessage(LSDtools.pname + "成功执行崩端命令~");
            return true;
        } else {
            sender.sendMessage(LSDtools.pname + "§c请输入正确的序号!");
        }
        return true;
    }
}
