package net.karolek.drop.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ItemUtil {

    private ItemUtil() {
    }

    public static boolean isPickaxe(ItemStack tool) {
        return Enchantment.DIG_SPEED.canEnchantItem(tool);
    }

    public static ItemStack itemStackFromString(String string) {
        String[] splits = string.split("@");
        if (splits.length < 2) {
            return new ItemStack(Material.matchMaterial(splits[0]));
        } else if (splits.length == 2) {
            return new ItemStack(Material.matchMaterial(splits[0]), 1, NumberUtil.parseInt(splits[1]).shortValue());
        }
        return new ItemStack(Material.AIR);
    }

    public static String itemStackToString(ItemStack itemStack) {
        if (itemStack.getData().getData() == 0) {
            return itemStack.getType().name();
        } else {
            return itemStack.getType().name() + "@" + itemStack.getData().getData();
        }
    }

    public static List<Material> materialsFromStrings(List<String> strings) {
        List<Material> materials = new ArrayList<>();
        for (String string : strings) {
            Material material = Material.matchMaterial(string);
            if (material != null) materials.add(material);
        }
        return materials;
    }

    public static List<String> materialsToStrings(List<Material> materials) {
        List<String> strings = new ArrayList<>();
        for (Material material : materials)
            strings.add(material.name());
        return strings;
    }

}
