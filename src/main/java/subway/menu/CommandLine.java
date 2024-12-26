package subway.menu;

public class CommandLine {
    private final String command;
    private final MenuItem menuItem;

    public CommandLine(String command, MenuItem menuItem) {
        this.command = command;
        this.menuItem = menuItem;
    }

    public String getCommand() {
        return command;
    }

    public String getLabel() {
        return this.menuItem.getLabel();
    }

    public Runnable getHandler() {
        return this.menuItem.getHandler();
    }
}
