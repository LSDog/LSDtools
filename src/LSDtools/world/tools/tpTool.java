package LSDtools.world.tools;

import LSDtools.LSDtools;
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
            if (p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().getLore().contains("§e§l点击传送")) {
                int x = p.getTargetBlock((Set<Material>) null, 1024).getX();
                int z = p.getTargetBlock((Set<Material>) null, 1024).getZ();
                int y = p.getTargetBlock((Set<Material>) null, 1024).getY();
                int free = 0;
                while (y <= p.getWorld().getMaxHeight()) {
                    if (p.getWorld().getBlockAt(x, y, z).getState().getType().equals((Material.AIR))) {
                        ++free;
                    } else {
                        free = 0;
                    }
                    if (free == 2) {
                        if (y < 0) {
                            y = 1;
                        }
                        Location loc = new Location(p.getWorld(), x + 0.5, y - 2 + 1, z + 0.5, p.getLocation().getYaw(), p.getPlayer().getLocation().getPitch());
                        p.teleport(loc); //传送
                        e.setCancelled(true);
                        return;
                    }
                    ++y;
                }
            }
        }
    }
}