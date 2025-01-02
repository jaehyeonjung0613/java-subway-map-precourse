package subway.menu;

import subway.controller.SectionViewController;

public class SectionMenu extends Menu<SectionViewController> {
    public SectionMenu(SectionViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "구간 등록", () -> this.handleSelectAfterClose(this.viewController::registerSection));
        this.addMenuItem("2", "구간 삭제", () -> this.handleSelectAfterClose(this.viewController::removeSection));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
