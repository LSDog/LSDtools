package LSDtools.AI;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class message implements Listener {
    File AIMessage = new File(LSDtools.MainTool.getDataFolder() + File.separator + "AIMessage.yml");
    FileConfiguration AIMsg = YamlConfiguration.loadConfiguration(AIMessage);

    String msg;
    String name;
    String AIname = "SABI"; //AI的名字
    String AInameRE = "§r§l[§r§bSABI§r§l]§r "; //AI的信息前缀
    String sendMessage;
    String ColorTextRE = "[&][0-9a-zA-Z]"; //关于颜色符号的正则表达式 例如: &1 , &b , &r ......
    String PunctuationRE = "[~，。？：；‘’！“”—\\-…、,.?:;'\"!`\\[ ]|(－{2})|(/.{3})"; //标点符号 正则表达式
    String whoAreYouRE = "([你][是|谁]+[啊|?？]*)|([谁][啊][你][?|？]*)"; //你是谁?
    String ChaXunRE = "[查]+[一]*([询]|[下])*"; //查询相关
    String hypixelRE = "[hypixel]|[hy]|[嗨皮咳嗽]"; //hy的称呼
    String jokeRE = "[讲][个]*[笑][话]"; //讲笑话
    String ghostStoryRE = "[讲][个]*[鬼][故][事]"; //讲鬼故事
    String badWordRE = "" +
            "[操|c][你|n][妈|m]" +
            "|[傻][逼]" +
            "|[脑]([瘫]|[残])" +
            "|[L]" +
            "|[智][障]" +
            "|[你|n][妈|m][死|s][了|l]" +
            "|[w]([d]|[c])[n][m][d]" +
            "|[f][u][c][k][ ]([u]|[y][o][u])" +
            "|[s][b]" +
            "|[n][t]" +
            "|[你][妈]"; //检测骂人
    String turnOnRE = "([开][个|下]*[灯])|([灯][开])|([调].*[早])"; //开灯关灯
    String turnOffRE = "([关][个|下]*[灯])|([灯][关])|([调].*[晚])";
    String rainRE = "[下][个|场|会][雨]"; //下雨
    String bigRainRE = "[下][个|场|会][大][雨]";
    String sunRE = "([放][晴])|([雨].*[停])|([停].*[雨])";
    String doULMRE = "[你]((([爱]|[喜][欢])[我][吗|么|嘛][?]*)|([喜][不][喜][欢][我]))"; //你喜欢我吗
    String math = "([计][算])|([算]+[一][下])|([算]{2,})|([等][于][几])";
    //[0-9]+([.]{1}[0-9]+)*([\\+-\\*/\\^%√]{1}[0-9]+([.]{1}[0-9]+)*)+

    Pattern ColorTextREC = Pattern.compile(ColorTextRE); //返回正则表达式
    Pattern PunctuationREC = Pattern.compile(PunctuationRE);
    Pattern whoAreYouREC = Pattern.compile(whoAreYouRE);
    Pattern ChaXunREC = Pattern.compile(ChaXunRE);
    Pattern hypixelREC = Pattern.compile(hypixelRE);
    Pattern jokeREC = Pattern.compile(jokeRE);
    Pattern ghostStoryREC = Pattern.compile(ghostStoryRE);
    Pattern badWordREC = Pattern.compile(badWordRE);
    Pattern turnOnREC = Pattern.compile(turnOnRE);
    Pattern turnOffREC = Pattern.compile(turnOffRE);
    Pattern rainREC = Pattern.compile(rainRE);
    Pattern bigRainREC = Pattern.compile(bigRainRE);
    Pattern sunREC = Pattern.compile(sunRE);
    Pattern doULMREC = Pattern.compile(doULMRE);

    public HashMap<String, Boolean> waiting = new HashMap<>();
    public HashMap<String, String> message = new HashMap<>();
    public HashMap<String, Long> coolDown = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new BukkitRunnable(){
            @Override
            public void run(){
                String onJoin_name = e.getPlayer().getName();
                Bukkit.broadcastMessage(AInameRE + "欢迎" + onJoin_name + " ~");
            }
        }.runTaskLater(LSDtools.MainTool,40);
    }

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                name = e.getPlayer().getName();
                msg = e.getMessage(); //get信息
                Matcher ColorTextMatcher = ColorTextREC.matcher(msg); //构建Matcher类
                msg = ColorTextMatcher.replaceAll(""); //把所有的颜色符号变没
                message.put(name, msg);


                Matcher PunctuationMatcher = PunctuationREC.matcher(message.get(name));
                Matcher whoAreYouMatcher = whoAreYouREC.matcher(message.get(name));

                if (PunctuationMatcher.replaceAll(message.get(name)).equalsIgnoreCase(AIname)) {
                    waiting.put(name, true);
                }

                waiting.putIfAbsent(name, false);

                if (message.get(name).toUpperCase().contains(AIname) || waiting.get(name)) {

                    Matcher ChaXunMatcher = ChaXunREC.matcher(message.get(name));
                    Matcher hypixelMatcher = hypixelREC.matcher(message.get(name));
                    Matcher jokeMatcher = jokeREC.matcher(message.get(name));
                    Matcher ghostStoryMatcher = ghostStoryREC.matcher(message.get(name));
                    Matcher badWordMatcher = badWordREC.matcher(PunctuationMatcher.replaceAll(message.get(name)));
                    Matcher turnOnMatcher = turnOnREC.matcher(message.get(name));
                    Matcher turnOffMatcher = turnOffREC.matcher(message.get(name));
                    Matcher rainMatcher = rainREC.matcher(message.get(name));
                    Matcher bigRainMatcher = bigRainREC.matcher(message.get(name));
                    Matcher sunMatcher = sunREC.matcher(message.get(name));
                    Matcher doULMMatcher = doULMREC.matcher(message.get(name));

                    if (PunctuationMatcher.replaceAll("").equalsIgnoreCase(AIname)) {
                        sendMessage = "在呢, 有什么事?";
                        waiting.put(name, true);

                    } else if (whoAreYouMatcher.find()) {
                        sendMessage = "0(^ ^)0 啊,你好,我是你的人工智障...... 随便问我一些问题吧?";
                        waiting.put(name, true);

                    } else if (ChaXunMatcher.find()) {
                        Bukkit.getServer().broadcastMessage("c1");
                        if (hypixelMatcher.find()) {
                            Bukkit.getServer().broadcastMessage("c2");
                            sendMessage = "!hypxelAPI!";
                            waiting.put(name, false);
                        }

                    } else if (jokeMatcher.find()) {
                        sendMessage = "!joke!";
                        waiting.put(name, false);

                    } else if (ghostStoryMatcher.find()) {
                        sendMessage = "!ghostStory!";
                        waiting.put(name, false);

                    } else if (turnOnMatcher.find()) {
                        Bukkit.getPlayer(name).getWorld().setTime(6000L);
                        sendMessage = "叮~";
                        waiting.put(name, false);

                    } else if (turnOffMatcher.find()) {
                        Bukkit.getPlayer(name).getWorld().setTime(18000L);
                        sendMessage = "叮~";
                        waiting.put(name, false);

                    } else if (rainMatcher.find()) {
                        Bukkit.getPlayer(name).getWorld().setStorm(true);
                        sendMessage = "哗啦哗啦...";
                        waiting.put(name, false);

                    } else if (bigRainMatcher.find()) {
                        Bukkit.getPlayer(name).getWorld().setStorm(true);
                        Bukkit.getPlayer(name).getWorld().setThundering(true);
                        sendMessage = "轰!";
                        waiting.put(name, false);

                    } else if (sunMatcher.find()) {
                        Bukkit.getPlayer(name).getWorld().setStorm(false);
                        Bukkit.getPlayer(name).getWorld().setThundering(false);
                        sendMessage = "原来希望晴天的人有那么多...";
                        waiting.put(name, false);

                    } else if (doULMMatcher.find()) {
                        sendMessage = "滚边儿去,你爷爷我是男的";
                        waiting.put(name, false);

                    } else if (badWordMatcher.find()) {
                        sendMessage = message.get(name).replaceAll("sabi", name).replaceAll("SABI", name);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (Bukkit.getPlayer(name) != null) {
                                    Bukkit.getPlayer(name).kickPlayer("你骂你马呢");
                                }
                            }
                        }.runTaskLater(LSDtools.MainTool, 60);
                        waiting.put(name, false);

                    } else {
                        AIMsg.set(msg, name);
                        try {
                            AIMsg.save(AIMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sendMessage = "奇怪的问题增加了！\n         §7(体谅一下,孩子还小,不懂这么多 -- 作者)";
                        waiting.put(name, false);
                    }
                    Bukkit.getServer().broadcastMessage(AInameRE + sendMessage);
                }
            }
        }.runTaskLater(LSDtools.MainTool,40);
    }
}
