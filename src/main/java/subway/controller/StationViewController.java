package subway.controller;

import subway.menu.StationMenu;
import subway.view.View;

public class StationViewController implements ViewController{
    @Override
    public View make() {
        StationMenu menu = new StationMenu(this);
        return new View("역 관리 화면", menu);
    }
}
