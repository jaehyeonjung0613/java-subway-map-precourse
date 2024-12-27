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

    public Runnable requestCommand() {
        String command;
        do {
            try {
                Console.printHeader(REQUEST_COMMAND_QUERY);
                command = Console.readline();
                return this.menu.select(command);
            } catch (IllegalArgumentException e) {
                Console.printNextLine();
                Console.printError(e.getMessage());
            } finally {
                Console.printNextLine();
            }
        } while (true);
    }

    public boolean isClose() {
        return this.menu.isClose();
    }
}
