package net.karolek.drop.commands.executors;

import net.karolek.drop.Config;
import net.karolek.drop.KarolekDrop;
import net.karolek.drop.commands.AbstractExecutor;
import net.karolek.drop.commands.exceptions.CommandException;
import net.karolek.drop.commands.exceptions.NoEnoughArgsException;
import net.karolek.drop.commands.exceptions.PermissionException;
import net.karolek.drop.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class KarolekDropExecutor extends AbstractExecutor {

    public KarolekDropExecutor() {
        super("karolekdrop", "glowna komenda pluginu", "/karolekdrop", "karolekdrop.executors.stone", false, new String[]{"kd", "dropadmin"});
    }

    @Override
    public boolean runCommand(CommandSender sender, String label, String[] args) throws CommandException {

        PluginDescriptionFile pdf = KarolekDrop.getPlugin().getDescription();

        if (args.length > 0) {
            switch (args[0]) {
                case "reload":
                    if (!sender.hasPermission("karolekdrop.executors.reload"))
                        throw new PermissionException("karolekdrop.executors.reload");
                    KarolekDrop.getPlugin().getDropManager().reloadManager();
                    Config.reloadConfig();
                    return Util.sendMsg(sender, "&aConfig has been reloaded!");
                case "author":
                case "autor":
                case "info":
                    Util.sendMsg(sender, "&7 ~ If you find any bugs or exploits, please report them to me ~");
                    Util.sendMsg(sender, "&7 ~ using GitHub: &ahttps://github.com/KarolekFM/karolekDrop &7 ~");
                    Util.sendMsg(sender, "&7 ~ Thank you for using my plugin, have a nice day! ;-) ~");
                    return true;
                default:
                    throw new NoEnoughArgsException("/karolekdrop [reload|author]");
            }
        }


        Util.sendMsg(sender, "&7 ~ KarolekDrop &a" + pdf.getVersion() + "&7 by &aKarolek &7 ~");
        Util.sendMsg(sender, "&7 ~ Please, donate me using PayPal: &aeastwest977@gmail.com &7~");
        return true;
    }

}
