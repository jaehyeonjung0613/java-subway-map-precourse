package subway.controller;

import subway.menu.SectionMenu;
import subway.view.View;

public class SectionViewController implements ViewController {
    @Override
    public View make() {
        SectionMenu menu = new SectionMenu(this);
        return new View("구간 관리 화면", menu);
    }
}
