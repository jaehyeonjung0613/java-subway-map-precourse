package subway.domain;

public final class LineConstants {
    private LineConstants() {
    }

    public static final int MIN_NAME_LENGTH = 2;

    public static final String EMPTY_NAME_MESSAGE = "지하철 노선 이름은 필수로 입력되어야합니다.";
    public static final String LESS_NAME_LENGTH_FORMAT = "지하철 노선 이름은 %d글자 이상이어야 합니다.";
    public static final String ALREADY_CONTAIN_STATION_MESSAGE = "이미 구간에 등록된 역 이름입니다.";
    public static final String RANGE_OVER_ORDER_FORMAT = "%d ~ %d 범위 내 순서만 입력 가능합니다.";
    public static final String NOT_CONTAIN_STATION_MESSAGE = "구간에 등록되지 않은 역 이름입니다.";
}
