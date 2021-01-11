package LSDtools.world.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Set;

public class tpTool implements Listener {
    public ItemStack getTool(){
            ItemStack item = new ItemStack(Material.FEATHER, 1);
            ItemMeta meta = item.getItemMeta(); //得到方块的元数据
            meta.setDisplayName("§e§l传送羽毛"); //设置它显示的名字
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§e§l点击传送"); //添加lore
            meta.setLore(lore);
            item.setItemMeta(meta); //保存更改
        return item;
    }
    @EventHandler
    public void tp(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() != null) {
            if (p.getItemInHand().getItemMeta().hasLore()
                    && p.getItemInHand().getItemMeta().getLore().contains("§e§l点击传送")) {
                if (p.getTargetBlock((Set<Material>) null, 2048).isLiquid()) { return; }
                if (p.getTargetBlock((Set<Material>) null, 2048).isEmpty()) { return; }
                int x = p.getTargetBlock((Set<Material>) null, 2048).getX(); //得到目标方块的XYZ
                int z = p.getTargetBlock((Set<Material>) null, 2048).getZ();
                int y = p.getTargetBlock((Set<Material>) null, 2048).getY();
                int free = 0;
                Location loc;
                    //开始检测 - 从目标方块起向上找两格空间的位置并传送
                while (y <= p.getWorld().getMaxHeight()) { // 当检测目标位于世界最高高度以下时
                    if (p.getWorld().getBlockAt(x, y, z).getState().getType().equals((Material.AIR))) { //如果是空气就将free值加一
                        ++free;
                    } else { free = 0; } //不然就设置成0

                    if (y >= 256) { //太高就传送到 y = 257
                        loc = new Location(p.getWorld(), x + 0.5, 257, z + 0.5, p.getLocation().getYaw(), p.getPlayer().getLocation().getPitch());
                        p.teleport(loc);
                        return;
                    } else if (free == 2) { //如果free值为2（有两格空间）
                        loc = new Location(p.getWorld(), x + 0.5, y - 1, z + 0.5, p.getLocation().getYaw(), p.getPlayer().getLocation().getPitch());
                        p.teleport(loc);
                        //确定位置并传送
                        e.setCancelled(true); //取消造成的一切额外事件
                        return;
                    }
                    y = y + 1; //如果全部不成立则再往上一格找
                }
            }
        }
    }
}