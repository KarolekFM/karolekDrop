package net.karolek.drop.utils;

import net.karolek.drop.base.Drop;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public final class MessagesUtil {

    private MessagesUtil() {
    }

    public static String replace(String source, Drop drop) {
        source = source.replace("{NAME}", drop.getName());
        source = source.replace("{ITEM}", ItemUtil.itemStackToString(drop.getItem()));
        source = source.replace("{MESSAGE}", drop.getMessage() != null && drop.getMessage().length() > 0 ? drop.getMessage() : "");
        source = source.replace("{CHANCE}", Double.toString(drop.getChance()));
        source = source.replace("{EXP}", Integer.toString(drop.getExp()));
        source = source.replace("{FORTUNE}", drop.isFortune() ? "tak" : "nie");
        source = source.replace("{CAN_DISABLE}", drop.isCanDisable() ? "tak" : "nie");
        source = source.replace("{HEIGHT}", drop.getHeight() != null ? drop.getHeight().getParse() : "wszedzie");
        source = source.replace("{AMOUNT}", drop.getAmount() != null ? drop.getAmount().getParse() : "1");
        source = source.replace("{POINTS}", drop.getPoints() != null ? drop.getPoints().getParse() : "0");
        source = source.replace("{TOOLS}", drop.getTools().size() == 0 ? "wszystkim" : StringUtils.join(drop.getTools(), ", "));
        return Util.fixColor(source);
    }

    public static String replace(String source, Drop drop, Player player) {
        source = source.replace("{ACTIVE}", drop.isDisabled(player.getName()) ? "nie" : "tak");
        source = source.replace("{CHANCE}", String.format("%1$.3f", drop.getChance(player)));
        source = source.replace("{CAN_DROP}", drop.canDrop(player) ? "tak" : "nie");
        source = replace(source, drop);
        return source;
    }

}
