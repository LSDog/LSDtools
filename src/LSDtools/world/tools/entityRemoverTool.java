package LSDtools.world.tools;

import LSDtools.LSDtools;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class entityRemoverTool implements Listener {
    public ItemStack getTool(){
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta(); //得到方块的元数据
        meta.setDisplayName("§e§l实体删除棒 - 左键切换模式"); //设置它显示的名字
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e§l点击选中并删除实体"); //添加lore
        meta.setLore(lore);
        item.setItemMeta(meta); //保存更改
        return item;
    }

    HashMap<String, Boolean> waitingForConfirm = new HashMap<>(); // 是否在等待二次点击确认
    HashMap<String, Entity> confirmMap = new HashMap<>(); // 二次点击确认删除的hashmap
    HashMap<String, Boolean> confirmMode = new HashMap<>(); // 切换模式

    @EventHandler
    public void changeMode(PlayerInteractEvent e) { // 模式切换
        Player p = e.getPlayer();
        Action action = e.getAction();
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (p.getItemInHand().getItemMeta().hasLore()
                    && p.getItemInHand().getItemMeta().getLore().contains("§e§l点击选中并删除实体")) {
                if (confirmMode.getOrDefault(p.getName(), true)) {
                    confirmMode.put(p.getName(), false);
                    p.sendMessage(LSDtools.pname + "§7 现在是直接删除模式");
                } else {
                    confirmMode.put(p.getName(), true);
                    p.sendMessage(LSDtools.pname + "§7 现在是确认模式");
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void remove(PlayerInteractEntityEvent e) { // 第一次、第二次点击实体的操作
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();
        if (e.getRightClicked() != null) {
            if (p.getItemInHand().getItemMeta().hasLore()
                    && p.getItemInHand().getItemMeta().getLore().contains("§e§l点击选中并删除实体")
                    && !(e.getRightClicked() instanceof Player)) {
                if (confirmMode.getOrDefault(p.getName(), true)) {
                    if (!waitingForConfirm.getOrDefault(p.getName(), false)) {
                        confirmMap.put(e.getPlayer().getName(), entity);
                        waitingForConfirm.put(p.getName(), true);
                        p.sendMessage(LSDtools.pname + "§7 再次右键以删除! 丢掉魔法棒以取消.");
                    } else if (waitingForConfirm.getOrDefault(p.getName(), false)) {
                        e.getRightClicked().remove();
                        waitingForConfirm.remove(p.getName());
                        confirmMap.remove(p.getName());
                        p.sendMessage(LSDtools.pname + "§7 已删除!");
                    }
                } else {
                    e.getRightClicked().remove();
                    p.sendMessage(LSDtools.pname + "§7 已删除!");
                }
            }
        }
    }

    @EventHandler
    public void cancelRemove(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack itemStack = e.getItemDrop().getItemStack();
        if (itemStack.getItemMeta().getLore().contains("§e§l点击选中并删除实体")
                && waitingForConfirm.getOrDefault(e.getPlayer().getName(), false)) {
            waitingForConfirm.remove(p.getName());
            confirmMap.remove(p.getName());
            p.sendMessage(LSDtools.pname + "§7 已取消!");
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void quitCancel(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        waitingForConfirm.remove(p.getName());
        confirmMap.remove(p.getName());
    }
}
