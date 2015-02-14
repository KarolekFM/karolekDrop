package net.karolek.drop.commands.exceptions;

import net.karolek.drop.utils.Util;

public class CommandException extends Exception {

    public CommandException(String message) {
        super(Util.fixColor(message));
    }
}
