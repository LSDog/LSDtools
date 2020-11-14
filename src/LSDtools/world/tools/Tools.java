package LSDtools.world.tools;

import org.bukkit.inventory.ItemStack;

public class Tools { //对应编号和物品
    private final tpTool tpTool = new tpTool();
    public ItemStack getTool (int num) {
        ItemStack tool = null;
        switch (num) {
            case 1:
                tool = tpTool.getTool();
                break;
            default:
                tool = null;
        }
        return tool;
    }
}
