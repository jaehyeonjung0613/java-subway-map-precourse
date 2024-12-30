package subway.controller;

import subway.ui.Console;
import subway.view.View;

public class RestViewController {

    public static void execute(ViewController viewController) {
        View view = viewController.make();

        Runnable handler;
        do {
            try {
                view.show();
                handler = view.requestCommand();
                handler.run();
            } catch (IllegalArgumentException e) {
                Console.printError(e.getMessage());
                Console.printNextLine();
            }
        } while (!view.isClose());
        Console.printNextLine();
    }
}
