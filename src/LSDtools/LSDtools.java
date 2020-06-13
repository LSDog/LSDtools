package LSDtools;
import org.bukkit.plugin.java.JavaPlugin;

public class LSDtools extends JavaPlugin{
    @Override
    public void onLoad() {
        getLogger().info("§b§l[LSDtools]§f插件已加载！");
    }
    public void onEnable() {
        getLogger().info("§b§l[LSDtools]§f插件已启用！");
    }
    public void onDisable() {
        getLogger().info("§b§l[LSDtools]§f插件已关闭！");
    }
}