package LSDtools.AI;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class message implements Listener {

    String msg;
    String name;
    String AIname = "SABI"; //AI的名字
    String AInameRE = "§r§l[§r§bSABI§r§l]§r "; //AI的信息前缀
    String[] LongSentences = new String[10];
    String ColorTextRE = "[&][0-9a-zA-Z]"; //关于颜色符号的正则表达式 例如: &1 , &b , &r ......
    String PunctuationRE = "[~，。？：；‘’！“”—\\-…、,.?:;'\"!`\\[\\] ]|(－{2})|(/.{3})"; //标点符号 正则表达式

    Pattern ColorTextREC = Pattern.compile(ColorTextRE); //返回正则表达式
    Pattern PunctuationREC = Pattern.compile(PunctuationRE);

    public HashMap<String, Boolean> waiting = new HashMap<>(); // 是否在等待此玩家回应
    public HashMap<String, String> message = new HashMap<>(); // 玩家发出的信息
    public HashMap<String, Long> coolDown = new HashMap<>(); // 冷却时间
    public HashMap<String, Boolean> isLongMessage = new HashMap<>(); // 反馈的信息是否是多条信息
    public HashMap<String, Integer> sentences = new HashMap<>(); // 有几条句子
    public HashMap<String, String> sendMessage = new HashMap<>(); // 发给玩家的信息

    // 退出游戏
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        waiting.put(e.getPlayer().getName(), false);
    }

    // 聊天
    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent e) {
        if (!LSDtools.MainTool.getConfig().getBoolean("AI", false)) {
            return;
        }

        name = e.getPlayer().getName(); //get名字
        msg = e.getMessage(); //get信息
        int i = (int)(1+Math.random()*(5-1+1)); //1~5的随机数
        Player p = Bukkit.getPlayer(name); //得到玩家
        Matcher ColorTextMatcher = ColorTextREC.matcher(msg); //构建Matcher类
        msg = ColorTextMatcher.replaceAll(""); //把所有的颜色符号变没
        message.put(name, msg); //把去掉颜色符号的句子放到Hashmap"message"的对应玩家里

        coolDown.putIfAbsent(name, 0L); // 冷却
        Matcher PunctuationMatcher = PunctuationREC.matcher(message.get(name));
        if (PunctuationMatcher.replaceAll("").toUpperCase().contains(AIname)) {
            if (System.currentTimeMillis() < coolDown.get(name)) {
                e.setCancelled(true);
                p.sendMessage("§7抱歉,请稍等一下再向SABI畅聊吧~");
                return;
            }
            coolDown.put(name, System.currentTimeMillis() + 3 * 1000L);
            waiting.put(name, true);
        }

        new BukkitRunnable() {
            @Override
            public void run() {

                waiting.putIfAbsent(name, false);
                isLongMessage.put(name, false);

                if (message.get(name).toUpperCase().contains(AIname) || waiting.get(name)) {

                    if (PunctuationMatcher.replaceAll("").equalsIgnoreCase(AIname)) {
                        if (i == 1) {sendMessage.put(name, "在呢, 有什么事?");}
                        if (i == 2) {sendMessage.put(name, "干嘛? 忙着呢...");}
                        if (i == 3) {sendMessage.put(name, "诶。");}
                        if (i == 4) {sendMessage.put(name, "怎么了?");}
                        if (i == 5) {sendMessage.put(name, "想干点啥?");}
                        waiting.put(name, true);

                    }

                    if (isLongMessage.get(name)) {
                        sentences.put(name, sentences.get(name)-1);
                            new BukkitRunnable() {
                                @Override
                                public void run()
                                {
                                    if (sentences.get(name) >= 0) {
                                        p.getServer().broadcastMessage(AInameRE + LongSentences[sentences.get(name)]);
                                        sentences.put(name, sentences.get(name)-1);
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(LSDtools.MainTool, 0L, 20L); // 插件主类  延时  定时
                    } else if (sendMessage.get(name) != null) {
                        p.getServer().broadcastMessage(AInameRE + sendMessage.get(name));
                    }
                }
            }
        }.runTaskLater(LSDtools.MainTool,40);
    }
    
}
