package subway.infrastructure;

import subway.controller.MainViewController;
import subway.controller.RestViewController;

public class Subway {
    private static Subway instance;

    private final MainViewController mainViewController;

    private Subway() {
        mainViewController = new MainViewController();
    }

    protected static Subway getInstance() {
        if (instance == null) {
            instance = new Subway();
        }
        return instance;
    }

    public void run() {
        RestViewController.execute(mainViewController);
    }

    public void finish() {
        instance = null;
    }
}
