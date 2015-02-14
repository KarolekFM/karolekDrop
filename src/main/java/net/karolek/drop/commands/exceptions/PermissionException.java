package net.karolek.drop.commands.exceptions;

public class PermissionException extends CommandException {

    public PermissionException(String message) {
        super("&cNie masz praw do wykonania tej komendy! &7(" + message + ")");
    }
}
