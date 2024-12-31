package subway.menu;

import subway.controller.MainViewController;

public class MainMenu extends Menu<MainViewController> {
    public MainMenu(MainViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 관리", this.viewController::openStationView);
        this.addMenuItem("2", "노선 관리", this.viewController::openLineView);
        this.addMenuItem("3", "구간 관리", () -> {
        });
        this.addMenuItem("4", "지하철 노선도 출력", () -> {
        });
        this.addMenuItem("Q", "종료", this::close);
    }
}
