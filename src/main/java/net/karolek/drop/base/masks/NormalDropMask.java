package net.karolek.drop.base.masks;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.utils.DropUtil;
import net.karolek.drop.utils.RandomUtil;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

import java.util.ArrayList;
import java.util.List;

public class NormalDropMask extends DropMask {

    public static final NormalDropMask INSTANCE = new NormalDropMask(KarolekDrop.getPlugin());

    private NormalDropMask(KarolekDrop plugin) {
        super(plugin);
    }

    @Override
    public void breakBlock(Player player, ItemStack tool, Block block) {
        DropUtil.recalculateDurability(player, tool);
        DropUtil.addItemsToPlayer(player, getDrops(block, tool), block);
    }

    private List<ItemStack> getDrops(final Block block, final ItemStack item) {
        final List<ItemStack> items = new ArrayList<>();
        final Material type = block.getType();
        int amount = 1;
        byte data;
        switch (type) {
            case NETHER_WARTS:
                final NetherWarts warts = (NetherWarts) block.getState().getData();
                amount = (warts.getState().equals(NetherWartsState.RIPE) ? (RandomUtil.getRandInt(0, 2) + 2) : 1);
                items.add(new ItemStack(Material.NETHER_STALK, amount));
                break;
            case COCOA:
                final CocoaPlant plant = (CocoaPlant) block.getState().getData();
                amount = (plant.getSize().equals(CocoaPlant.CocoaPlantSize.LARGE) ? 3 : 1);
                items.add(new ItemStack(Material.INK_SACK, amount, (short) 3));
                break;
            //case PUMPKIN_STEM: // temp :)
            //    items.add(new ItemStack(Material.PUMPKIN_SEEDS, 1));
            //    break;
            //case MELON_STEM:
            //    items.add(new ItemStack(Material.MELON_SEEDS, 1));
            //    break;
            //case CARROT:
            //    data = block.getState().getData().getData();
            //    if(data == 7) {
            //        amount = RandomUtil.getRandInt(1, 3);
            //    }
            //    items.add(new ItemStack(Material.CARROT_ITEM, amount));
            //    break;
            //case POTATO:
            //    data = block.getState().getData().getData();
            //    if(data == 7) {
            //        amount = RandomUtil.getRandInt(1, 3);
            //    }
            //    items.add(new ItemStack(Material.POTATO_ITEM, amount));
            //    break;
            case CROPS:
                final Crops wheat = (Crops) block.getState().getData();
                int seedamount = 1;
                if (wheat.getState() == CropState.RIPE) {
                    items.add(new ItemStack(Material.WHEAT, RandomUtil.getRandInt(1, 2)));
                    seedamount = 1 + RandomUtil.getRandInt(0, 2);
                }
                items.add(new ItemStack(Material.SEEDS, seedamount));
                break;
            //case SUGAR_CANE_BLOCK:
            //    amount = 1;
            //    items.add(new ItemStack(Material.SUGAR_CANE, amount));
            //    break;
            case DOUBLE_PLANT:
                data = block.getData();
                if (data == 11) {
                    Block under = block.getRelative(0, -1, 0);
                    if (under.getType() == Material.DOUBLE_PLANT) {
                        items.addAll(under.getDrops(item));
                        under.setType(Material.AIR);
                    }
                } else {
                    items.addAll(block.getDrops(item));
                }
                break;
            case REDSTONE_WIRE:
            case WOODEN_DOOR:
                data = block.getData();
                if (data == 8) {
                    Block under = block.getRelative(0, -1, 0);
                    if (under.getType() == Material.WOODEN_DOOR) {
                        items.addAll(under.getDrops(item));
                        under.setType(Material.AIR);
                    }
                } else {
                    items.addAll(block.getDrops(item));
                }
                break;
            case IRON_DOOR_BLOCK:
                data = block.getData();
                if (data == 8) {
                    Block under = block.getRelative(0, -1, 0);
                    if (under.getType() == Material.IRON_DOOR_BLOCK) {
                        items.addAll(under.getDrops(item));
                        under.setType(Material.AIR);
                    }
                } else {
                    items.addAll(block.getDrops(item));
                }
                break;
            case TRIPWIRE:
            case LEVER:
            case WOOD_BUTTON:
            case STONE_BUTTON:
            case DIODE_BLOCK_ON:
            case DIODE_BLOCK_OFF:
            case REDSTONE_COMPARATOR_OFF:
            case REDSTONE_COMPARATOR_ON:
            case DAYLIGHT_DETECTOR:
                items.addAll(block.getDrops(item));
                break;
            case REDSTONE_ORE:
            case GLOWING_REDSTONE_ORE:
                if (item.containsEnchantment(Enchantment.SILK_TOUCH) && block.getType().isBlock()) {
                    items.add(new ItemStack(Material.REDSTONE_ORE));
                    break;
                }
                items.addAll(block.getDrops(item));
                break;
            case SNOW:
                items.add(new ItemStack(Material.SNOW_BALL, block.getData()));
                break;
            default:
                if (item.containsEnchantment(Enchantment.SILK_TOUCH) && block.getType().isBlock()) {
                    items.add(new ItemStack(block.getType(), 1, (short) block.getData()));
                    break;
                }
                items.addAll(block.getDrops(item));
                break;
        }
        return items;
    }
}
