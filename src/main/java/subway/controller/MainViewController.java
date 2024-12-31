package subway.controller;

import subway.menu.MainMenu;
import subway.view.View;

public class MainViewController implements ViewController {
    private final StationViewController stationViewController = new StationViewController();
    private final LineViewController lineViewController = new LineViewController();

    @Override
    public View make() {
        MainMenu menu = new MainMenu(this);
        return new View("메인 화면", menu);
    }

    public void openStationView() {
        RestViewController.execute(stationViewController);
    }

    public void openLineView() {
        RestViewController.execute(lineViewController);
    }
}
