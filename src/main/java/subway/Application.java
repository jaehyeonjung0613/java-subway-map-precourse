package subway;

import subway.infrastructure.Subway;
import subway.infrastructure.SubwayBuilder;

public class Application {
    public static void main(String[] args) {
        Subway subway = new SubwayBuilder().build();
        try {
            subway.run();
        } finally {
            subway.finish();
        }
    }
}
