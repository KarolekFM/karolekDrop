package net.karolek.drop.commands.executors;

import net.karolek.drop.commands.AbstractExecutor;
import net.karolek.drop.commands.exceptions.CommandException;
import net.karolek.drop.gui.DropGuiMenu;
import org.bukkit.entity.Player;

public class StoneExecutor extends AbstractExecutor {

    public StoneExecutor() {
        super("stone", "informacje o dropie ze stone", "/stone", "karolekdrop.executors.stone", new String[]{"drop", "kamien"});
    }

    @Override
    public boolean runCommand(Player player, String label, String[] args) throws CommandException {
        new DropGuiMenu().open(player);
        return true;
    }
}
