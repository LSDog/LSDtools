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
    String whoAreYouRE = "(你.*[是谁]+[啊|?？]*)|(谁啊你[?|？]*)"; //你是谁?
    String ChaXunRE = "查+[一?下]|[寻.*]"; //查询相关
    String hypixelRE = "hypixel|hy"; //hy的称呼
    String jokeRE = "讲个*笑话"; //讲笑话
    String ghostStoryRE = "讲个*鬼故事"; //讲鬼故事
    String badWordRE =
            "操你妈" +
            "|傻逼" +
            "|sb" +
            "|脑[瘫残]" +
            "|[智][障]" +
            "|爬" +
            "|(.*[ ]+L+\\b)|(L+[ ]+.*)|(L{2,})" +
            "|[你|n](妈|m|全家)[死sm][了|l]" +
            "|w[dc]nmd" +
            "|fuck[ ]*(u|you)" +
            "|[n][t|c]" +
            "|[你][妈]" +
            "|([母]|[妈]|[娘])*.*[死].*[全家]*.*" +
            "|村.*吃饭"; //检测骂人
    String turnOnRE = "([开][个下]*[灯])|([灯][开])|([调].*[早])"; //开灯关灯
    String turnOffRE = "([关][个下]*[灯])|([灯][关])|([调].*[晚])";
    String rainRE = "[下][个场会]*[雨]"; //下雨
    String bigRainRE = "[下][个场会]*[大][雨]";
    String sunRE = "([放][晴])|([雨].*[停])|([停].*[雨])";
    String doULMRE = "[你](((爱|喜欢)[我][吗么嘛][?]*)|(喜不喜欢我))"; //你喜欢我吗
    String mathRE = "计算|算+一下|算{2,}|等于几|[0-9][+\\-*/^%√()!]";
    String outOfMathRE = "[^0-9+\\-*/^%√.()!]";
    String cleanItemRE = "扫(个|一?下)*地";
    String nothingRE = "没.*事|test|测试[一下]*|nothing|\\.";
    String translateRE = "翻译";

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
    Pattern mathREC = Pattern.compile(mathRE);
    Pattern outOfMathREC = Pattern.compile(outOfMathRE);
    Pattern cleanItemREC = Pattern.compile(cleanItemRE);
    Pattern nothingREC = Pattern.compile(nothingRE);
    Pattern translateREC = Pattern.compile(translateRE);

    public HashMap<String, Boolean> waiting = new HashMap<>(); // 是否在等待此玩家回应
    public HashMap<String, String> message = new HashMap<>(); // 玩家发出的信息
    public HashMap<String, Long> coolDown = new HashMap<>(); // 冷却时间
    public HashMap<String, Boolean> isLongMessage = new HashMap<>(); // 反馈的信息是否是多条信息
    public HashMap<String, Integer> sentences = new HashMap<>(); // 有几条句子
    public HashMap<String, String> sendMessage = new HashMap<>(); // 发给玩家的信息

    // 加入游戏
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!LSDtools.MainTool.getConfig().getBoolean("AI", false)) {
            return;
        }
        new BukkitRunnable(){
            @Override
            public void run(){
                String onJoin_name = e.getPlayer().getName();
                if (!e.getPlayer().hasPlayedBefore()){
                    Bukkit.broadcastMessage(AInameRE + "欢迎新玩家" + onJoin_name + " ~");
                } else {
                    int i = (int)(1+Math.random()*(5-1+1));
                    String joinMessage = null;
                    if (i == 1) {joinMessage = "欢迎回来！ " + onJoin_name + " ~";}
                    if (i == 2) {joinMessage = "呦, " + onJoin_name + "!";}
                    if (i == 3) {joinMessage = "来啦 " + onJoin_name + "?";}
                    if (i == 4) {joinMessage = "hey yo~ " + onJoin_name;}
                    if (i == 5) {joinMessage = "唉,这不是 " + onJoin_name + " 嘛~";}
                    Bukkit.broadcastMessage(AInameRE + joinMessage);
                }
            }
        }.runTaskLater(LSDtools.MainTool,60);
    }

    // 退出游戏
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        waiting.put(e.getPlayer().getName(), false);
    }

    // 死亡事件
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!LSDtools.MainTool.getConfig().getBoolean("AI", false)) {
            return;
        }
        new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.broadcastMessage(AInameRE + "RIP " + e.getEntity().getPlayer().getName() + ".");
            }
        }.runTaskLater(LSDtools.MainTool,40);
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

        coolDown.putIfAbsent(name, 0L);
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

                    Matcher whoAreYouMatcher = whoAreYouREC.matcher(message.get(name));
                    Matcher ChaXunMatcher = ChaXunREC.matcher(message.get(name));
                    Matcher hypixelMatcher = hypixelREC.matcher(message.get(name));
                    Matcher jokeMatcher = jokeREC.matcher(message.get(name));
                    Matcher ghostStoryMatcher = ghostStoryREC.matcher(message.get(name));
                    Matcher badWordMatcher = badWordREC.matcher(message.get(name));
                    Matcher turnOnMatcher = turnOnREC.matcher(message.get(name));
                    Matcher turnOffMatcher = turnOffREC.matcher(message.get(name));
                    Matcher rainMatcher = rainREC.matcher(message.get(name));
                    Matcher bigRainMatcher = bigRainREC.matcher(message.get(name));
                    Matcher sunMatcher = sunREC.matcher(message.get(name));
                    Matcher doULMMatcher = doULMREC.matcher(message.get(name));
                    Matcher mathMatcher = mathREC.matcher(message.get(name));
                    Matcher outOfMathMatcher = outOfMathREC.matcher(message.get(name));
                    Matcher cleanItemMatcher = cleanItemREC.matcher(message.get(name));
                    Matcher nothingMatcher = nothingREC.matcher(message.get(name));

                    if (PunctuationMatcher.replaceAll("").equalsIgnoreCase(AIname)) {
                        if (i == 1) {sendMessage.put(name, "在呢, 有什么事?");}
                        if (i == 2) {sendMessage.put(name, "干嘛? 忙着呢...");}
                        if (i == 3) {sendMessage.put(name, "诶。");}
                        if (i == 4) {sendMessage.put(name, "怎么了?");}
                        if (i == 5) {sendMessage.put(name, "想干点啥?");}
                        waiting.put(name, true);

                    } else if (whoAreYouMatcher.find()) {
                        sendMessage.put(name, "0(^ ^)0 啊,你好,我是你的人工智障...... 随便问我一些问题吧?");
                        waiting.put(name, true);

                    } else if (ChaXunMatcher.find()) {
                        if (hypixelMatcher.find()) {
                            sendMessage.put(name, "!hypxelAPI!");
                            waiting.put(name, false);
                        }

                    } else if (nothingMatcher.find()) {
                        if (i == 1) {sendMessage.put(name, "哦...");}
                        if (i == 2) {sendMessage.put(name, "哦好");}
                        if (i == 3) {sendMessage.put(name, "...");}
                        if (i == 4) {sendMessage.put(name, "没事啊...");}
                        if (i == 5) {sendMessage.put(name, "*打哈欠*");}
                        waiting.put(name, false);

                    } else if (mathMatcher.find()) {
                        String math = outOfMathMatcher.replaceAll("");
                        ScriptEngineManager manager = new ScriptEngineManager();
                        ScriptEngine se = manager.getEngineByName("js");
                        try {
                            BigDecimal result = new BigDecimal(se.eval(math).toString());
                            if (String.valueOf(result).endsWith(".0")) {
                                sendMessage.put(name, "应该是" + String.valueOf(result).replaceAll(".0", ""));
                            } else {
                                sendMessage.put(name, "应该是" + result);
                            }
                            waiting.put(name, false);
                        } catch (ScriptException | NullPointerException e) {
                            sendMessage.put(name, "唔...这或许不是一个正常的算式...");
                            waiting.put(name, false);
                        }

                    } else if (cleanItemMatcher.find()) {
                        int i = 0;
                        for (World world : Bukkit.getServer().getWorlds()) {
                            for (Item item : Bukkit.getWorld(world.getName()).getEntitiesByClass(Item.class)) {
                                item.remove();
                                i++;
                            }
                        }
                        if (i > 0) {
                            sendMessage.put(name, "好嘞~ 清除了§e§l " + i + " §r个掉落物!");
                        } else {
                            sendMessage.put(name, "额...好像没什么我可以清理的了......");
                        }
                        waiting.put(name, false);

                    } else if (jokeMatcher.find()) {
                        isLongMessage.put(name,true);
                        sentences.put(name, 2);
                        LongSentences[1] = "玻璃上建房子?";
                        LongSentences[0] = "-- 没门!";
                        waiting.put(name, false);

                    } else if (ghostStoryMatcher.find()) {
                        sendMessage.put(name, "!ghostStory!");
                        waiting.put(name, false);

                    } else if (turnOnMatcher.find()) {
                        p.getWorld().setTime(6000L);
                        sendMessage.put(name, "叮~");
                        waiting.put(name, false);

                    } else if (turnOffMatcher.find()) {
                        p.getWorld().setTime(18000L);
                        sendMessage.put(name, "叮~");
                        waiting.put(name, false);

                    } else if (rainMatcher.find()) {
                        p.getWorld().setStorm(true);
                        sendMessage.put(name, "哗啦哗啦...");
                        waiting.put(name, false);

                    } else if (bigRainMatcher.find()) {
                        p.getWorld().setStorm(true);
                        p.getWorld().setThundering(true);
                        sendMessage.put(name, "轰!");
                        waiting.put(name, false);

                    } else if (sunMatcher.find()) {
                        p.getWorld().setStorm(false);
                        p.getWorld().setThundering(false);
                        sendMessage.put(name, "原来希望晴天的人有那么多...");
                        waiting.put(name, false);

                    } else if (doULMMatcher.find()) {
                        sendMessage.put(name, "滚边儿去,你爷爷我是男的");
                        waiting.put(name, false);

                    } else if(message.get(name).equalsIgnoreCase(name)) {
                        sendMessage.put(name, name + "? 那不就是你嘛... 有什么事吗?");
                        waiting.put(name, true);

                    } else if (badWordMatcher.find()) {
                        sendMessage.put(name, message.get(name).replaceAll("sabi", name).replaceAll("SABI", name));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (p != null) {
                                    p.kickPlayer("你骂你马呢");
                                }
                            }
                        }.runTaskLater(LSDtools.MainTool, 60);
                        waiting.put(name, false);

                    } else {
                        if (i == 1) {sendMessage.put(name, "什么嘛......");}
                        if (i == 2) {sendMessage.put(name, "没...没听太懂");}
                        if (i == 3) {sendMessage.put(name, "闲的吧...");}
                        if (i == 4) {sendMessage.put(name, "嗯 嗯 ... 你说啥来着?");}
                        if (i == 5) {sendMessage.put(name, "不懂,聊点别的行吗");}
                        waiting.put(name, false);

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
