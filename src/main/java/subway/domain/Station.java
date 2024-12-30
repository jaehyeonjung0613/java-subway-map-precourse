package subway.domain;

import static subway.domain.StationConstants.*;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    private final String name;

    public Station(String name) throws IllegalArgumentException {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (name.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(LESS_NAME_LENGTH_FORMAT, MIN_NAME_LENGTH));
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public StationDTO toDTO() {
        return new StationDTO(this.name);
    }
}
