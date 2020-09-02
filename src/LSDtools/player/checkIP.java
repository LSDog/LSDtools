package LSDtools.player;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class checkIP implements Listener {
    //加载配置data.yml
    File data = new File(LSDtools.MainTool.getDataFolder() + File.separator + "data.yml");
    FileConfiguration Data = YamlConfiguration.loadConfiguration(data);

    @EventHandler
    public void onJoin(PlayerJoinEvent p) throws IOException {

        FileReader fileReader = new FileReader(data);
        LineNumberReader reader = new LineNumberReader(fileReader);

        String name = p.getPlayer().getName();
        String ip = p.getPlayer().getAddress().toString();
        boolean isFirstJoin = !p.getPlayer().hasPlayedBefore();

        //向控制台发送消息
        if (LSDtools.MainTool.getConfig().getBoolean("joinInfoConsole", true)) {
            Bukkit.getConsoleSender().sendMessage(LSDtools.pname + name + " §r§l| §b" + ip + " §r§l| " + "§b首次加入: §e" + isFirstJoin);
        }
        //向所有有 "LSDtools.notice" 权限的玩家发送玩家消息
        if (LSDtools.MainTool.getConfig().getBoolean("joinInfoOP", true)) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.hasPermission("LSDtools.notice") || player.isOp()) {
                    player.sendMessage(LSDtools.pname + name + " §r§l| §b" + ip + " §r§l| " + "§b首次加入: §e" + isFirstJoin);
                }
            }
        }

        Date date = new Date(); //日期
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //日期格式转换
        String uuid = p.getPlayer().getUniqueId().toString(); //UUID
        String IP = name + ".IP"; //确定配置的键值
        String JoinTime = name + ".JoinTime";
        String UUID = name + ".UUID";

        //保存玩家信息
        if (Data.get(UUID) == null) { Data.set(UUID, uuid); }
        if (Data.get(JoinTime) == null) { Data.set(JoinTime, formatter.format(date)); }
        Data.set(IP, ip);
        Data.save(data);

        //检测小号: 比对IP前三位 (例如 /127.0.0.1:25565 -> 127.0.0)
        String Cip = p.getPlayer().getAddress().toString(); //得到这玩家的ip
        String IP3s = Cip.substring(Cip.indexOf("/")+1, Cip.lastIndexOf(".")); //变成 x.x.x 样式
        int line;
        line = 1; //重置行数
        int sameIpPlayersC = 0; //小号数量
        List<String> sameIpPlayers = new ArrayList<>(10); //创建数组
        while (line <= Files.lines(Paths.get(data.getPath())).count()) { //历遍data.yml每一行
            reader.setLineNumber(line);
            String thisLine = reader.readLine(); //读行
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
            if (LSDtools.MainTool.getConfig().getInt("MostAccountWithSameIP") != 0) { //若限制IP则↓
                if (sameIpPlayersC > LSDtools.MainTool.getConfig().getInt("MostAccountWithSameIP")) {
                    p.getPlayer().kickPlayer(LSDtools.pname + "请不要设置过多小号!");
                    Bukkit.getConsoleSender().sendMessage(LSDtools.pname + "玩家§r§l " + name + " §b因为在同一IP下拥有过多账号被踢出");
                }
                return;
            }
            Bukkit.getConsoleSender().sendMessage(LSDtools.pname + "玩家§r§l " + name + " §b共有以下§e§l " + sameIpPlayersC +" §b个相同IP账户:"); //给控制台发
            Bukkit.getConsoleSender().sendMessage(LSDtools.pname + sameIpPlayers.toString());
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.hasPermission("LSDtools.notice")) { //给有权限的人发
                    player.sendMessage(LSDtools.pname + "玩家§r§l " + name + " §b共有以下§e§l " + sameIpPlayersC +" §b个相同IP账户:");
                    player.sendMessage(LSDtools.pname + sameIpPlayers.toString());
                }
            }
        }

    }
}