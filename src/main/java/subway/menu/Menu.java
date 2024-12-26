package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    private final List<CommandLine> commandLineList;
    protected final T viewController;

    public Menu(T viewController) {
        this.commandLineList = new ArrayList<>();
        this.viewController = viewController;
        this.setup();
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
}
