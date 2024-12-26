package subway.infrastructure;

public class Subway {
    private static Subway instance;

    private Subway() {
    }

    protected static Subway getInstance() {
        if (instance == null) {
            instance = new Subway();
        }
        return instance;
    }

    public void run() {
    }

    public void finish() {
        instance = null;
    }
}
