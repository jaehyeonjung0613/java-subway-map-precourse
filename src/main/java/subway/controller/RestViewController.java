package subway.controller;

import subway.ui.Console;
import subway.view.View;

public class RestViewController {

    public static void execute(ViewController viewController) {
        View view = viewController.make();

        String command;
        do {
            try {
                view.show();
                command = view.requestCommand();
                view.onSelect(command);
            } catch (IllegalArgumentException e) {
                Console.printError(e.getMessage());
            } finally {
                Console.printNextLine();
            }
        } while (!view.isClose());
    }
}
