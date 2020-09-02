package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class checkplayer implements CommandExecutor {
    //加载配置data.yml
    File data = new File(LSDtools.MainTool.getDataFolder() + File.separator + "data.yml");
    FileConfiguration Data = YamlConfiguration.loadConfiguration(data);
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            //参数过多的提示
            if (args.length > 1) {
                sender.sendMessage(LSDtools.pname + "参数过多 - /checkplayer <player>");
                return true;
            }
            //查询某一玩家
            if (args.length < 1) {
                sender.sendMessage("§b所有在线玩家的信息: ");
                sender.sendMessage("==========");
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    String name = player.getPlayer().getName();
                    String ip = player.getPlayer().getAddress().toString();
                    boolean isFirstJoin = !player.getPlayer().hasPlayedBefore();
                    sender.sendMessage("§b" + name + " §r§l| §b" + ip + " §r§l| " + "§b首次加入: §e" + isFirstJoin);
                }
                sender.sendMessage("==========");
                return true;
            } else if (sender.getServer().getPlayer(args[0]) != null) {
                //若第一个参数中玩家存在，则发送信息
                String name = Bukkit.getPlayer(args[0]).getName();
                String ip = Bukkit.getPlayer(args[0]).getAddress().toString();
                boolean isFirstJoin = !Bukkit.getPlayer(args[0]).hasPlayedBefore();
                sender.sendMessage(LSDtools.pname + name + " §r§l| §b" + ip + " §r§l| " + "§b首次加入: §e" + isFirstJoin);
                Player p = Bukkit.getPlayer(args[0]);

                //检测小号: 比对IP前三位 (例如 /127.0.0.1:25565 -> 127.0.0)

                FileReader fileReader;
                try {
                    fileReader = new FileReader(data);
                } catch (FileNotFoundException e) {
                    return true;
                }
                LineNumberReader reader = new LineNumberReader(fileReader);
                String Cip = p.getPlayer().getAddress().toString(); //得到这玩家的ip
                String IP3s = Cip.substring(Cip.indexOf("/")+1, Cip.lastIndexOf(".")); //变成 x.x.x 样式
                int line;
                line = 1; //重置行数
                int sameIpPlayersC = 0; //小号数量
                List<String> sameIpPlayers = new ArrayList<>(10); //创建数组
                while (true) {
                    try {
                        if (!(line <= Files.lines(Paths.get(data.getPath())).count())) break;
                    } catch (IOException e) {
                        return true;
                    } //历遍data.yml每一行
                    reader.setLineNumber(line);
                    String thisLine; //读行
                    try {
                        thisLine = reader.readLine();
                    } catch (IOException e) {
                        return true;
                    }
                    if (thisLine.endsWith(":")) { //检测这一行是不是个玩家名
                        if (Bukkit.getOfflinePlayer(thisLine.substring(0, thisLine.indexOf(":"))) != null) { //得到玩家名↓
                            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(thisLine.substring(0, thisLine.indexOf(":"))); //得到data.yml里的玩家之一
                            String CfgIp = Data.getString(targetPlayer.getName() + ".IP"); //得到他的IP
                            String IP3sInCfg = CfgIp.substring(CfgIp.indexOf("/") + 1, CfgIp.lastIndexOf(".")); //变成 x.x.x 样式
                            if (IP3s.equals(IP3sInCfg)) { //如果找到俩ip前三段一样
                                sameIpPlayersC++; //相同ip人数+1
                                sameIpPlayers.add(targetPlayer.getName()); //数组加这个人名
                            }
                        }
                    }
                    line++; //行数+1 ↑
                }
                if (sameIpPlayersC > 1) { //如果相同IP账户多于一个(因为是算自己的) -> 打印信息
                    sender.sendMessage(LSDtools.pname + "玩家§r§l " + name + " §b共有以下§e§l " + sameIpPlayersC +" §b个相同IP账户:"); //给控制台发
                    sender.sendMessage(LSDtools.pname + sameIpPlayers.toString());
                }
                return true;
            } else {
                //否则提示没有此玩家
                sender.sendMessage(LSDtools.pname + "§c没有找到该玩家！");
                return true;
        }
    }
}
