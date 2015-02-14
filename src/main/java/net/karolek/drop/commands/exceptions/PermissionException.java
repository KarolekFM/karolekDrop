package net.karolek.drop.commands.exceptions;

public class PermissionException extends CommandException {

    public PermissionException(String message) {
        super("&cYou don't have permissions to run that command! &7(" + message + ")");
    }
}
