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

    public void registerStation() {
        Console.printHeader("등록할 역 이름을 입력하세요.");
        String name = Console.readline();
        Console.printNextLine();
        stationController.insertStation(name);
        Console.printInfo("지하철 역이 등록되었습니다.");
    }

    public void removeStation() {
        Console.printHeader("삭제할 역 이름을 입력하세요");
        String name = Console.readline();
        Console.printNextLine();
        stationController.deleteStation(name);
        Console.printInfo("지하철 역이 삭제되었습니다.");
    }
}
