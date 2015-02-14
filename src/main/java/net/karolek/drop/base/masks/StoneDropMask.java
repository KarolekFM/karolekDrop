package net.karolek.drop.base.masks;

import net.karolek.drop.Config;
import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.Drop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.utils.DropUtil;
import net.karolek.drop.utils.RandomUtil;
import net.karolek.drop.utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StoneDropMask extends DropMask {

    public StoneDropMask(KarolekDrop plugin) {
        super(plugin);
    }

    @Override
    public void breakBlock(Player player, ItemStack tool, Block block) {
        int exp = Config.STONE_EXP;
        List<ItemStack> drops = new ArrayList<>();
        for (Drop drop : getDropManager().getRandomDrops()) {
            if (drop.isDisabled(player.getName())) continue;
            if (!drop.enoughPickaxe(tool)) continue;
            if (!drop.enoughHeight(block.getY())) continue;
            if (!RandomUtil.getChance(drop.getChance())) continue;
            int amount = drop.getRandomAmount();
            int points = drop.getRandomPoints();

            if (tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) && drop.isFortune()) {
                int fortune = DropUtil.addFortuneEnchant(tool);
                amount += fortune;
            }

            ItemStack item = drop.getItem().clone();
            item.setAmount(amount);
            exp += drop.getExp();
            drops.add(item);

            if (drop.getMessage() != null && drop.getMessage().length() > 0) {
                String message = drop.getMessage();
                message = message.replace("{AMOUNT}", Integer.toString(amount));
                message = message.replace("{POINTS}", Integer.toString(points));
                Util.sendMsg(player, message);
            }
        }
        if (drops.size() < 1)
            drops.add(new ItemStack(tool.containsEnchantment(Enchantment.SILK_TOUCH) ? Material.STONE : Material.COBBLESTONE));

        DropUtil.recalculateDurability(player, tool);
        DropUtil.addItemsToPlayer(player, drops, block);
        player.giveExp(exp);
    }
}
