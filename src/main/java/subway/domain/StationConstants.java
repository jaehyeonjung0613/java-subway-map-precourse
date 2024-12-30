package subway.domain;

public final class StationConstants {
    private StationConstants() {
    }

    public static final int MIN_NAME_LENGTH = 2;

    public static final String EMPTY_NAME_MESSAGE = "지하철 역 이름은 필수로 입력되어야합니다.";
    public static final String LESS_NAME_LENGTH_FORMAT = "지하철 역 이름은 %d글자 이상이어야 합니다.";
}
