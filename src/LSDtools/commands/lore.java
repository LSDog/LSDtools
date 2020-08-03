package LSDtools.commands;

import LSDtools.LSDtools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//此类部分源码取自 Lores 插件 https://dev.bukkit.org/projects/lores
public class lore implements CommandExecutor {
    private static HashMap<String, LinkedList<ItemStack>> undo = new HashMap();
    char[] colorCodes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'l', 'n', 'o', 'k', 'm', 'r'};
    public void help(CommandSender sender) {
        sender.sendMessage(LSDtools.pname + "物品数据修改用法:");
        sender.sendMessage(LSDtools.pname + "/lore name <name> - 修改名字");
        sender.sendMessage(LSDtools.pname + "/lore owner <name> - 修改头颅主人");
        sender.sendMessage(LSDtools.pname + "/lore add <text> - 增加一条lore");
        sender.sendMessage(LSDtools.pname + "/lore set <line> <text> - 设置某一行lore");
        sender.sendMessage(LSDtools.pname + "/lore insert <line> <text> - 插入lore");
        sender.sendMessage(LSDtools.pname + "/lore delete [line] - 删除一行");
        sender.sendMessage(LSDtools.pname + "/lore clear - 清除数据");
        sender.sendMessage(LSDtools.pname + "/lore undo - 撤回");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) { //确保执行者是玩家
            sender.sendMessage(LSDtools.pname + "只有玩家能做这个!");
        } else {
            Player player = (Player) sender;
            ItemStack item = player.getItemInHand();
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                meta = Bukkit.getItemFactory().getItemMeta(item.getType());
                if (meta == null) {
                    player.sendMessage(LSDtools.pname + "§4不支持修改数据");
                    return true;
                }
            }
            if (args.length < 1) {
                help(sender);
            } else {
                List<String> lore = meta.getLore();
                if (lore == null) {
                    lore = new LinkedList();
                }

                Action action;
                try {
                    action = Action.valueOf(args[0].toUpperCase());
                } catch (IllegalArgumentException var20) {
                    help(sender);
                    return true;
                }

                String id = player.getName() + "'" + item.getTypeId();
                if (action != Action.UNDO) {
                    if (!undo.containsKey(id)) {
                        undo.put(id, new LinkedList());
                    }

                    LinkedList<ItemStack> list = (LinkedList)undo.get(id);
                    list.addFirst(item.clone());

                    while(list.size() > 5) {
                        list.removeLast();
                    }
                }

                int index;
                int stackSize;
                label194:
                switch(action) {
                    case NAME:
                        if (!sender.hasPermission("lores.name") || args.length < 2) {
                            help(sender);
                            return true;
                        }

                        String name = concatArgs(sender, args, 1);
                        if (name.contains("|")) {
                            index = name.replaceAll("§[0-9a-klmnor]", "").length();

                            for(Iterator itr = lore.iterator(); itr.hasNext(); index = Math.max(index, ((String)itr.next()).replaceAll("§[0-9a-klmnor]", "").length())) {
                            }

                            int spaces = index - name.replaceAll("§[0-9a-klmnor]", "").length() - 1;
                            String space = " ";

                            for(stackSize = 1; (double)stackSize < (double)spaces * 1.5D; ++stackSize) {
                                space = space + ' ';
                            }

                            name = name.replace("|", space);
                        }

                        meta.setDisplayName(name);
                        break;
                    case OWNER:
                        if (!sender.hasPermission("lores.owner") || args.length < 2) {
                            help(sender);
                            return true;
                        }

                        if (!(meta instanceof SkullMeta)) {
                            player.sendMessage("§4You may only set the Owner of a §6Skull");
                            return true;
                        }

                        ((SkullMeta)meta).setOwner(args[1]);
                        break;
                    case ADD:
                        if (!sender.hasPermission("lores.lore") || args.length < 2) {
                            help(sender);
                            return true;
                        }

                        lore.add(concatArgs(sender, args, 1));
                        break;
                    case DELETE:
                        if (!sender.hasPermission("lores.lore")) {
                            help(sender);
                            return true;
                        }

                        switch(args.length) {
                            case 1:
                                if (lore.size() < 1) {
                                    player.sendMessage("§4没有可删除的东西!");
                                    return true;
                                }

                                lore.remove(lore.size() - 1);
                                break label194;
                            case 2:
                                try {
                                    index = Integer.parseInt(args[1]) - 1;
                                } catch (Exception var19) {
                                    return false;
                                }

                                if (lore.size() <= index || index < 0) {
                                    player.sendMessage("§4未知的行数!");
                                    return true;
                                }

                                lore.remove(index);
                                break label194;
                            default:
                                return false;
                        }
                    case SET:
                        if (!sender.hasPermission("lores.lore") || args.length < 3) {
                            help(sender);
                            return true;
                        }

                        try {
                            index = Integer.parseInt(args[1]) - 1;
                        } catch (Exception var18) {
                            return false;
                        }

                        if (lore.size() <= index || index < 0) {
                            player.sendMessage("§4未知的行数!");
                            return true;
                        }

                        lore.set(index, concatArgs(sender, args, 2));
                        break;
                    case INSERT:
                        if (!sender.hasPermission("lores.lore") || args.length < 3) {
                            help(sender);
                            return true;
                        }

                        int i;
                        try {
                            i = Integer.parseInt(args[1]) - 1;
                        } catch (Exception var17) {
                            return false;
                        }

                        if (lore.size() <= i || i < 0) {
                            player.sendMessage("§4未知的行数!");
                            return true;
                        }

                        lore.add(i, concatArgs(sender, args, 2));
                        break;
                    case CLEAR:
                        if (sender.hasPermission("lores.lore") && args.length == 1) {
                            (lore).clear();
                            break;
                        }

                        help(sender);
                        return true;
                    case UNDO:
                        if (args.length != 1) {
                            return false;
                        } else {
                            LinkedList<ItemStack> list = (LinkedList)undo.get(id);
                            if (list == null) {
                                player.sendMessage("§4你还没有修改过此物品的数据呢!");
                                return true;
                            } else if (list.size() < 1) {
                                player.sendMessage("§4无法继续撤回!");
                                return true;
                            } else {
                                ItemStack undoneItem = (ItemStack)list.removeFirst();
                                if (!item.isSimilar(undoneItem) && item.getType() != Material.SKULL_ITEM) {
                                    player.sendMessage("§4你还没有修改过此物品的数据呢!");
                                } else {
                                    stackSize = item.getAmount();
                                    if (undoneItem.getAmount() != stackSize) {
                                        undoneItem.setAmount(stackSize);
                                    }

                                    player.setItemInHand(undoneItem);
                                    player.sendMessage("§5已撤回!");
                                }
                                return true;
                            }
                        }
                }

                meta.setLore(lore);
                item.setItemMeta(meta);
                player.sendMessage("§5成功设置Lore!");
            }
            return true;
        }
        return true;
    }

    private static String concatArgs(CommandSender sender, String[] args, int first) {
        StringBuilder sb = new StringBuilder();
        if (first > args.length) {
            return "";
        } else {
            for(int i = first; i <= args.length - 1; ++i) {
                sb.append(" ");
                sb.append(ChatColor.translateAlternateColorCodes('&', args[i]));
            }

            String string = sb.substring(1);
            char[] charArray = string.toCharArray();
            boolean modified = false;

            for(int i = 0; i < charArray.length; ++i) {
                if (charArray[i] == 167 && !sender.hasPermission("lores.color." + charArray[i + 1])) {
                    charArray[i] = '?';
                    modified = true;
                }
            }

            return modified ? String.copyValueOf(charArray) : string;
        }
    }

    private enum Action {
        NAME,
        OWNER,
        ADD,
        DELETE,
        SET,
        INSERT,
        CLEAR,
        UNDO;

        Action() {
        }
    }
}
