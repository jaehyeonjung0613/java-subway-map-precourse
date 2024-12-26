package subway.menu;

public class MenuItem {
    private final String label;
    private final Runnable handler;

    public MenuItem(String label, Runnable handler) {
        this.label = label;
        this.handler = handler;
    }

    public String getLabel() {
        return label;
    }

    public Runnable getHandler() {
        return handler;
    }
}
