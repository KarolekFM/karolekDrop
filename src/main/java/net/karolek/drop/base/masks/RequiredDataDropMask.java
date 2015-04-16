package net.karolek.drop.base.masks;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.utils.DropUtil;
import net.karolek.drop.utils.RandomUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class RequiredDataDropMask extends DropMask {

    private Material material;
    private int minAmount, maxAmount;
    private byte neededData;

    public RequiredDataDropMask(KarolekDrop plugin, Material material, int minAmount, int maxAmount, byte neededData) {
        super(plugin);
        this.material = material;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.neededData = neededData;
    }

    @Override public void breakBlock(Player player, ItemStack tool, Block block) {
        int amount = 1;
        if(block.getData() == neededData) {
            amount = RandomUtil.getRandInt(minAmount, maxAmount);
        }
        ItemStack drop = new ItemStack(material, amount);

        DropUtil.recalculateDurability(player, tool);
        DropUtil.addItemsToPlayer(player, Arrays.asList(drop), block);
    }
}
