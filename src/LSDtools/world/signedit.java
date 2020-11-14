package LSDtools.world;

import LSDtools.LSDtools;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.regex.Pattern;


public class signedit implements CommandExecutor{
    public static boolean isNumeric(String string){ //正整数判断
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) { //确保执行者是玩家
            sender.sendMessage(LSDtools.pname + "只有玩家能做这个!");
            return true;
        }

        if (args.length == 0 || args[0].isEmpty()) {
            sender.sendMessage(LSDtools.pname + "修改告示牌 - §r/signedit <line> <text>");
            sender.sendMessage(LSDtools.pname + "修改告示牌 - §r原句 = %d ,空格 = %s ,&为颜色字符 ,& = %&");
            return true;
        } else if (!isNumeric(args[0])) {
            sender.sendMessage(LSDtools.pname + "§c错误: 这不是一个合法的数");
            return true;
        } else if (Integer.parseInt(args[0]) > 3) {
            sender.sendMessage(LSDtools.pname + "§c错误: 行数必须介于 0~3 之间 (从 0 开始)");
            return true;
        } else if (args[1].isEmpty()) {
            sender.sendMessage(LSDtools.pname + "§c错误: 请输入内容");
            return true;
        }

        Block targetBlock = ((Player) sender).getPlayer().getTargetBlock((Set<Material>) null, 10);
        if (targetBlock.getState().getType() == Material.SIGN
                || targetBlock.getState().getType() == Material.WALL_SIGN
                || targetBlock.getState().getType() == Material.SIGN_POST
        ) {
            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) targetBlock.getState();
            String txt = args[1]
                    .replace("%&","&")
                    .replace("&","§")
                    .replace("%s"," ")
                    .replace("%d",sign.getLine(Integer.parseInt(args[0])));
            sign.setLine(Integer.parseInt(args[0]), txt);
            sign.update();
            sender.sendMessage(LSDtools.pname + "告示牌已修改为:§r " + txt + " §r(" + args[0] + ")");
        } else {
            sender.sendMessage(LSDtools.pname + "§c请对准告示牌! (10格以内)");
        }

        return true;
    }
}


