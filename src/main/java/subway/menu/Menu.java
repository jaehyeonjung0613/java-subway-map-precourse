package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    private final List<CommandLine> commandLineList;
    protected final T viewController;

    private MenuState state = MenuState.IDLE;

    public Menu(T viewController) {
        this.commandLineList = new ArrayList<>();
        this.viewController = viewController;
        this.setup();
        this.state = MenuState.OPEN;
    }

    abstract protected void setup();

    private CommandLine getCommandLine(String command) {
        return this.commandLineList.stream()
            .filter(commandLine -> commandLine.getCommand().equals(command))
            .findFirst()
            .get();
    }

    public final String output(String command) {
        CommandLine commandLine = this.getCommandLine(command);
        return String.format(MENU_ITEM_OUTPUT_FORMAT, commandLine.getCommand(), commandLine.getLabel());
    }

    public final String outputAll() {
        StringBuilder builder = new StringBuilder();
        for (CommandLine commandLine : this.commandLineList) {
            builder.append(this.output(commandLine.getCommand()));
            builder.append("\n");
        }
        return builder.toString();
    }

    public final void insertMenuItem(int position, String command, String label, Runnable handler) {
        MenuItem menuItem = new MenuItem(label, handler);
        CommandLine commandLine = new CommandLine(command, menuItem);
        this.commandLineList.add(position, commandLine);
    }

    public final void addMenuItem(String command, String label, Runnable handler) {
        this.insertMenuItem(this.commandLineList.size(), command, label, handler);
    }

    public final void select(String command) {
        CommandLine commandLine = this.getCommandLine(command);
        commandLine.getHandler().run();
    }

    public final void close() {
        this.state = MenuState.CLOSE;
    }

    public final boolean isClose() {
        return MenuState.CLOSE.equals(this.state);
    }
}
