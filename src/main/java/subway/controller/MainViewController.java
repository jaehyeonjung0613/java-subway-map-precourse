package subway.controller;

import java.util.List;

import subway.dao.LineDAO;
import subway.menu.MainMenu;
import subway.ui.Console;
import subway.view.View;

public class MainViewController implements ViewController {
    private final StationViewController stationViewController = new StationViewController();
    private final LineViewController lineViewController = new LineViewController();
    private final SectionViewController sectionViewController = new SectionViewController();
    private final SectionController sectionController = new SectionController();

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

    public void openSectionView() {
        RestViewController.execute(sectionViewController);
    }

    public void printSubwayMap() {
        Console.printHeader("지하철 노선도");
        List<LineDAO> lineDAOList = sectionController.selectAllLineNameWithStationName();
        List<String> stationNameList;
        for (LineDAO lineDAO : lineDAOList) {
            Console.printInfo(lineDAO.getName());
            Console.printInfo("---");
            stationNameList = lineDAO.getStationNameList();
            for (String stationName : stationNameList) {
                Console.printInfo(stationName);
            }
            Console.printNextLine();
        }
    }
}
