package subway.controller;

import subway.menu.LineMenu;
import subway.view.View;

public class LineViewController implements ViewController {
    @Override
    public View make() {
        LineMenu menu = new LineMenu(this);
        return new View("노선 관리 화면", menu);
    }
}
