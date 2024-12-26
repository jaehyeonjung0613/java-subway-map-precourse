package subway.view;

public class View {
    private final String name;

    public View(String name) {
        this.name = name;
    }

    public void show() {
    }

    public String requestCommand() {
        return null;
    }

    public void onSelect(String command) {
    }

    public boolean isClose() {
        return true;
    }

    public String getName() {
        return name;
    }
}
