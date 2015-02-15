package net.karolek.drop.utils;

import net.karolek.drop.Config;
import net.karolek.drop.compare.Compare;
import net.karolek.drop.compare.Compares;
import net.karolek.drop.compare.IntegerCompare;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DropUtil {

    public static final Compare<Integer> FORTUNE_1_COMPARE = Compares.parseString(Config.FORTUNE_1_AMOUNT);
    public static final Compare<Integer> FORTUNE_2_COMPARE = Compares.parseString(Config.FORTUNE_2_AMOUNT);
    public static final Compare<Integer> FORTUNE_3_COMPARE = Compares.parseString(Config.FORTUNE_3_AMOUNT);
    public static final Compare<Integer> FORTUNE_HIGH_LEVELS_COMPARE = Compares.parseString(Config.FORTUNE_HIGH$LEVELS_AMOUNT);

    private DropUtil() {
    }

    public static void recalculateDurability(Player player, ItemStack item) {
        if (item.getType().getMaxDurability() == 0) {
            return;
        }
        int enchantLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
        short d = item.getDurability();
        if (enchantLevel > 0) {
            if (100 / (enchantLevel + 1) > RandomUtil.getRandInt(0, 100)) {
                if (d == item.getType().getMaxDurability()) {
                    player.getInventory().clear(player.getInventory().getHeldItemSlot());
                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                } else {
                    item.setDurability((short) (d + 1));
                }
            }
        } else if (d == item.getType().getMaxDurability()) {
            player.getInventory().clear(player.getInventory().getHeldItemSlot());
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
        } else {
            item.setDurability((short) (d + 1));
        }
    }

    public static int addFortuneEnchant(ItemStack tool) {
        switch (tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)) {
            case 1:
                if (RandomUtil.getChance(Config.FORTUNE_1_PERCENT)) return Compares.getRandomValue(FORTUNE_1_COMPARE);
            case 2:
                if (RandomUtil.getChance(Config.FORTUNE_2_PERCENT)) return Compares.getRandomValue(FORTUNE_2_COMPARE);
            case 3:
                if (RandomUtil.getChance(Config.FORTUNE_3_PERCENT)) return Compares.getRandomValue(FORTUNE_3_COMPARE);
            default:
                if (RandomUtil.getChance(Config.FORTUNE_HIGH$LEVELS_PERCENT))
                    return Compares.getRandomValue(FORTUNE_HIGH_LEVELS_COMPARE);
                else return 0;
        }
    }

    public static void addItemsToPlayer(Player player, List<ItemStack> items, Block b) {
        PlayerInventory inv = player.getInventory();
        HashMap<Integer, ItemStack> notStored = inv.addItem((ItemStack[]) items.toArray(new ItemStack[items.size()]));
        for (Map.Entry<Integer, ItemStack> en : notStored.entrySet()) {
            b.getWorld().dropItemNaturally(b.getLocation(), (ItemStack) en.getValue());
        }
    }

}
