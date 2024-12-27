package subway.controller;

import java.util.List;

import subway.menu.StationMenu;
import subway.ui.Console;
import subway.view.View;

public class StationViewController implements ViewController {
    private final StationController stationController = new StationController();

    @Override
    public View make() {
        StationMenu menu = new StationMenu(this);
        return new View("역 관리 화면", menu);
    }

    public void printStationNameAll() {
        Console.printHeader("역 목록");
        List<String> stationNameList = stationController.selectStationNameList();
        for (String stationName : stationNameList) {
            Console.printInfo(stationName);
        }
    }
}
