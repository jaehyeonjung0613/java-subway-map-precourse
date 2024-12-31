package subway.infrastructure;

import subway.controller.MainViewController;
import subway.controller.RestViewController;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

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

    private void init() {
        StationRepository.init();
        LineRepository.init();
    }

    public void run() {
        this.init();
        RestViewController.execute(mainViewController);
    }

    public void finish() {
        instance = null;
    }
}
