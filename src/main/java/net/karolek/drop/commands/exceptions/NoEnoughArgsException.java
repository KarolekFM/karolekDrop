package net.karolek.drop.commands.exceptions;


import net.karolek.drop.commands.AbstractExecutor;

public class NoEnoughArgsException extends CommandException {

    public NoEnoughArgsException(String message) {
        super("&cPrawidlowe uzycie: &7" + message + "&c!");
    }

    public NoEnoughArgsException(AbstractExecutor cmd) {
        this(cmd.getUsage());
    }

}
