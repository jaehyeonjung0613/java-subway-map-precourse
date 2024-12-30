package subway.domain;

import subway.dto.DefaultDTO;

public interface Entity<T extends DefaultDTO> {
    T toDTO();
}
