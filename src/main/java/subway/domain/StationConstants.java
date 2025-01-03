package subway.domain;

public final class StationConstants {
    private StationConstants() {
    }

    public static final int MIN_NAME_LENGTH = 2;

    public static final String EMPTY_NAME_MESSAGE = "지하철 역 이름은 필수로 입력되어야합니다.";
    public static final String LESS_NAME_LENGTH_FORMAT = "지하철 역 이름은 %d글자 이상이어야 합니다.";
    public static final String ALREADY_CONTAIN_LINE_MESSAGE = "이미 구간에 등록된 노선 이름입니다.";
    public static final String NOT_CONTAIN_LINE_MESSAGE = "구간에 등록되지 않은 노선 이름입니다.";
}
