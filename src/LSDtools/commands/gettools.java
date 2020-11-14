package LSDtools.commands;

import LSDtools.LSDtools;
import LSDtools.utils.Isnumber;
import LSDtools.world.tools.Tools;
import LSDtools.world.tools.tpTool;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class gettools implements CommandExecutor {
    private final Tools Tools = new Tools();
    private final Isnumber Isnumber = new Isnumber();

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) { //确保执行者是玩家
            sender.sendMessage(LSDtools.pname + "只有玩家能做这个!");
            return true;
        }
        Player p = ((Player) sender).getPlayer();
        if (args.length < 1) { //没有参数则介绍小工具
            p.sendMessage(LSDtools.pname + "用法: /gettools <序号> [玩家]");
            p.sendMessage(LSDtools.pname + "1. §r传送棒");
            return true;
        }
        if (args.length > 2) { //参数过多提示
            p.sendMessage(LSDtools.pname + "参数过多 - /gettools <序号> [玩家]");
            return true;
        }
        //获取tool
        if (Isnumber.isnum(args[0])) {
            int tool = Integer.parseInt(args[0]);
            if (Tools.getTool(tool) != null) {
                String DisplayName = Tools.getTool(tool).getItemMeta().getDisplayName();
                if (args.length == 2) { //若指定玩家
                    if (p.getServer().getPlayer(args[1]) != null) {
                        Player p1 = p.getServer().getPlayer(args[1]);
                        p1.getInventory().addItem(Tools.getTool(tool));
                        p.sendMessage(LSDtools.pname + p1.getName() + "§r§l ➡ " + DisplayName);
                        p1.sendMessage(LSDtools.pname + "收到一个" + DisplayName);
                        return true;
                    } else {
                        p.sendMessage(LSDtools.pname + "§c没有找到该玩家!");
                        return true;
                    }
                } else { //若未指定玩家，则发给自己
                    p.getPlayer().getInventory().addItem(Tools.getTool(tool));
                    p.sendMessage(LSDtools.pname + "收到一个" + DisplayName);
                    return true;
                }
            } else { //未找到此工具
                p.sendMessage(LSDtools.pname + "§c没有找到此工具!");
                return true;
            }
        } else {
            p.sendMessage(LSDtools.pname + "§c序号应为正整数！");
            return true;
        }
    }
}