package subway.controller;

import java.util.List;

import subway.menu.LineController;
import subway.menu.LineMenu;
import subway.ui.Console;
import subway.view.View;

public class LineViewController implements ViewController {
    private final LineController lineController = new LineController();

    @Override
    public View make() {
        LineMenu menu = new LineMenu(this);
        return new View("노선 관리 화면", menu);
    }

    public void printLineNameAll() {
        Console.printHeader("역 목록");
        List<String> lineNameList = lineController.selectLineNameList();
        for (String lineName : lineNameList) {
            Console.printInfo(lineName);
        }
    }
}
