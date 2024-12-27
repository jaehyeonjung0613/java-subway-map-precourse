package subway.menu;

import subway.controller.StationViewController;

public class StationMenu extends Menu<StationViewController> {
    public StationMenu(StationViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 등록", ()->{});
        this.addMenuItem("2", "역 삭제", ()->{});
        this.addMenuItem("3", "역 조회", ()->{});
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
