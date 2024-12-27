package subway.view;

import static subway.view.ViewConstants.*;

import subway.menu.Menu;
import subway.ui.Console;

public class View {
    private final String name;
    private final Menu menu;

    public View(String name, Menu menu) {
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void show() {
        Console.printHeader(this.getName());
        Console.println(this.menu.outputAll());
    }

    public String requestCommand() {
        Console.printHeader(REQUEST_COMMAND_QUERY);
        return Console.readline();
    }

    public void onSelect(String command) {
        Console.printNextLine();
        this.menu.select(command);
    }

    public boolean isClose() {
        return this.menu.isClose();
    }
}
