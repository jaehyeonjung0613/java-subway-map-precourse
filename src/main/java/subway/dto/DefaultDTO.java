package subway.dto;

import subway.domain.Entity;

public interface DefaultDTO<T extends Entity> {
    T toEntity();
}
