package subway.menu;

import subway.controller.LineViewController;

public class LineMenu extends Menu<LineViewController> {
    public LineMenu(LineViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "노선 등록", () -> {
        });
        this.addMenuItem("2", "노선 삭제", () -> {
        });
        this.addMenuItem("3", "노선 조회", () -> this.handleSelectAfterClose(this.viewController::printLineNameAll));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
