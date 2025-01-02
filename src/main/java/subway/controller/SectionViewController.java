package subway.controller;

import subway.menu.SectionMenu;
import subway.ui.Console;
import subway.view.View;

public class SectionViewController implements ViewController {
    private final SectionController sectionController = new SectionController();

    @Override
    public View make() {
        SectionMenu menu = new SectionMenu(this);
        return new View("구간 관리 화면", menu);
    }

    public void registerSection() {
        Console.printHeader("노선을 입력하세요.");
        String lienName = Console.readline();
        Console.printNextLine();
        Console.printHeader("역이름을 입력하세요.");
        String stationName = Console.readline();
        Console.printNextLine();
        Console.printHeader("순서를 입력하세요.");
        String position = Console.readline();
        Console.printNextLine();
        sectionController.insertSection(lienName, stationName, position);
        Console.printInfo("구간이 등록되었습니다.");
    }
}
