package net.karolek.drop.base;

import lombok.Getter;
import lombok.Setter;
import net.karolek.drop.Config;
import net.karolek.drop.KarolekDrop;
import net.karolek.drop.compare.Compare;
import net.karolek.drop.compare.Compares;
import net.karolek.drop.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
@Setter
@SerializableAs("Drop")
public class Drop implements ConfigurationSerializable {

    private final String name;
    private final String permission;
    private final ItemStack item;
    private final String message;
    private final double chance;
    private final int exp;
    private final boolean fortune;
    private final boolean canDisable;
    private final Compare<Integer> height;
    private final Compare<Integer> amount;
    private final Compare<Integer> points;
    private final List<Material> tools = new ArrayList<>();
    private final Set<String> disabled = new HashSet<>();

    public Drop(String name, String permission, ItemStack item, String message, double chance, int exp, boolean fortune, boolean canDisable, Compare<Integer> height, Compare<Integer> amount, Compare<Integer> points, List<Material> tools) {
        this.name = name;
        this.permission = permission;
        this.item = item;
        this.message = message;
        this.chance = chance;
        this.exp = exp;
        this.fortune = fortune;
        this.canDisable = canDisable;
        this.height = height;
        this.amount = amount;
        this.points = points;
        this.tools.addAll(tools);
    }

    public static Drop deserialize(Map<String, Object> map) {
        String name = null;
        String permission = null;
        ItemStack item = null;
        String message = null;
        double chance = 0D;
        int exp = 0;
        boolean fortune = false;
        boolean canDisable = true;
        Compare<Integer> height = null;
        Compare<Integer> amount = null;
        Compare<Integer> points = null;
        List<Material> tools = new ArrayList<>();

        if (map.containsKey("name")) {
            name = (String) map.get("name");
            if (name == null)
                throw new IllegalArgumentException("Name can not be null!");
        }

        if (map.containsKey("permission")) {
            permission = (String) map.get("permission");
        }

        if (map.containsKey("item")) {
            item = ItemUtil.itemStackFromString((String) map.get("item"));
            if (item == null)
                throw new IllegalArgumentException("Item can not be null!");
        }

        if (map.containsKey("message")) {
            message = (String) map.get("message");
        }

        if (map.containsKey("chance")) {
            chance = (double) map.get("chance");
        }

        if (map.containsKey("exp")) {
            exp = (int) map.get("exp");
        }

        if (map.containsKey("fortune")) {
            fortune = (boolean) map.get("fortune");
        }

        if (map.containsKey("can-disable")) {
            canDisable = (boolean) map.get("can-disable");
        }

        if (map.containsKey("height")) {
            height = Compares.parseString((String) map.get("height"));
        }

        if (map.containsKey("amount")) {
            amount = Compares.parseString((String) map.get("amount"));
        }

        if (map.containsKey("points")) {
            points = Compares.parseString((String) map.get("points"));
        }

        if (map.containsKey("tools")) {
            tools.addAll(ItemUtil.materialsFromStrings((List<String>) map.get("tools")));
        }


        return new Drop(name, permission, item, message, chance, exp, fortune, canDisable, height, amount, points, tools);

    }

    public double getChance(Player viewer) {
        double chance = getChance();
        if (Config.VIP$DROP_ENABLED & viewer.hasPermission(Config.VIP$DROP_PERMISSION))
            chance += chance * Config.VIP$DROP_MULTIPLIER;
        return chance;
    }

    public boolean canDrop(Player viewer) {
        return this.permission == null || viewer.hasPermission(this.permission);
    }

    public double getChanceBonus(String string) {
        if (!Config.BONUS$DROP_ENABLED) return 0D;
        if (!canDisable) return 0D;
        if (isDisabled(string)) return 0D;

        List<Drop> drops = KarolekDrop.getPlugin().getDropManager().getRandomDrops();
        int disabledDrops = 0;
        int dropsSize = drops.size();
        double dropsChance = 0D;
        double disabledDropsChance = 0D;

        for (Drop drop : drops) {
            //if(drop.equals(this)) continue;

            if (drop.isDisabled(string)) {
                disabledDrops++;
                disabledDropsChance += drop.getChance();
            }
            dropsChance += drop.getChance();

        }

        if (disabledDrops == 0) return 0D;

        int deltaDrops = dropsSize - disabledDrops;
        double deltaChance = dropsChance - disabledDropsChance;

        return (getChance() / (double) deltaDrops) * Config.BONUS$DROP_MULTIPLIER;
    }

    public boolean isDisabled(String string) {
        if (!canDisable) return false;
        return disabled.contains(string);
    }

    public void changeStatus(String string) {
        if (!canDisable) return;
        if (isDisabled(string))
            disabled.remove(string);
        else
            disabled.add(string);
    }

    public boolean enoughHeight(int height) {
        if (this.height == null) return true;
        return this.height.isInRange(height);
    }

    public boolean enoughPickaxe(ItemStack item) {
        if (!ItemUtil.isPickaxe(item)) return false;
        if (tools.size() < 1) return true;
        return tools.contains(item.getType());
    }

    public int getRandomAmount() {
        if (this.amount == null) return 1;
        return Compares.getRandomValue(this.amount);
    }

    public int getRandomPoints() {
        if (this.points == null) return 0;
        return Compares.getRandomValue(this.points);
    }

    @Override
    public Map<String, Object> serialize() {
        System.out.println(permission + " :D");
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", this.name);
        if (this.permission != null) map.put("permission", this.permission);
        map.put("item", ItemUtil.itemStackToString(this.item));
        if (this.message != null && this.message.length() > 0) map.put("message", this.message);
        map.put("chance", this.chance);
        if (this.exp > 0) map.put("exp", this.exp);
        map.put("fortune", this.fortune);
        map.put("can-disable", this.canDisable);
        if (this.height != null) map.put("height", this.height.getParse());
        if (this.amount != null) map.put("amount", this.amount.getParse());
        if (this.points != null) map.put("points", this.points.getParse());
        map.put("tools", ItemUtil.materialsToStrings(this.tools));
        return map;
    }
}
