package subway.controller;

import java.util.List;

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

    public void registerLine() {
        Console.printHeader("등록할 노선 이름을 입력하세요.");
        String lineName = Console.readline();
        Console.printNextLine();
        Console.printHeader("등록할 노선의 상행 종점역 이름을 입력하세요.");
        String fstStationName = Console.readline();
        Console.printNextLine();
        Console.printHeader("등록할 노선의 하행 종점역 이름을 입력하세요.");
        String lstStationName = Console.readline();
        Console.printNextLine();
        lineController.insertLine(lineName, fstStationName, lstStationName);
        Console.printInfo("지하철 노선이 등록되었습니다.");
    }

    public void removeLine() {
        Console.printHeader("삭제할 노선 이름을 입력하세요");
        String name = Console.readline();
        Console.printNextLine();
        lineController.deleteLine(name);
        Console.printInfo("지하철 노선이 삭제되었습니다.");
    }
}
