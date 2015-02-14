package net.karolek.drop.commands;

import lombok.Getter;
import lombok.Setter;
import net.karolek.drop.commands.exceptions.CommandException;
import net.karolek.drop.commands.exceptions.PermissionException;
import net.karolek.drop.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public abstract class AbstractExecutor implements CommandExecutor {

    private final String name;
    private final String description;
    private final String usage;
    private final String permission;
    private final List<String> aliases;
    private final boolean playerExec;

    protected static CommandMap cmap;

    public AbstractExecutor(String name, String description, String usage, String... aliases) {
        this(name, description, usage, null, true, aliases);
    }

    public AbstractExecutor(String name, String description, String usage, String permission, String... aliases) {
        this(name, description, usage, permission, true, aliases);
    }

    public AbstractExecutor(String name, String description, String usage, String permission, boolean playerExec, String... aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.playerExec = playerExec;
        this.aliases = Arrays.asList(aliases);
    }

    public boolean runCommand(CommandSender sender, String label, String[] args) throws CommandException {
        throw new CommandException("&cNie znaleziono metody, ktora obsluguje komende konsoli!");
    }

    public boolean runCommand(Player player, String label, String[] args) throws CommandException {
        throw new CommandException("&cNie znaleziono metody, ktora obsluguje komende gracza!");
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (this.permission != null && this.permission.length() > 0) {
                if (!sender.isOp() || !sender.hasPermission(this.permission))
                    throw new PermissionException(this.permission);
            }
            if (playerExec) {
                if (!(sender instanceof Player))
                    throw new CommandException("&cMusisz byc graczem, aby moc wykonac ta komende!");
                return runCommand((Player) sender, label, args);
            }
            return runCommand(sender, label, args);
        } catch (CommandException e) {
            return Util.sendMsg(sender, e.getMessage());
        }
    }

    public final AbstractExecutor register() {
        ReflectCommand cmd = new ReflectCommand(this.name);
        if (this.aliases != null) cmd.setAliases(this.aliases);
        if (this.description != null) cmd.setDescription(Util.fixColor(this.description));
        if (this.usage != null) cmd.setUsage(Util.fixColor(this.usage));
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);
        return this;
    }

    final CommandMap getCommandMap() {
        if (cmap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (cmap != null) {
            return cmap;
        }
        return getCommandMap();
    }

    private static final class ReflectCommand extends Command {
        private AbstractExecutor exe = null;

        protected ReflectCommand(String command) {
            super(command);
        }

        public void setExecutor(AbstractExecutor exe) {
            this.exe = exe;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            if (exe != null)
                return exe.onCommand(sender, this, commandLabel, args);
            return false;
        }

    }


}
