package LSDtools.world.tools;

import org.bukkit.inventory.ItemStack;

public class allTools { //对应编号和物品
    private final tpTool tpTool = new tpTool();
    private final entityRemoverTool entityRemoverTool = new entityRemoverTool();

    public ItemStack getTool (int num) {
        ItemStack tool = null;
        switch (num) {
            case 1:
                tool = tpTool.getTool();
                break;
            case 2:
                tool = entityRemoverTool.getTool();
                break;
            default:
                tool = null;
        }
        return tool;
    }
}
