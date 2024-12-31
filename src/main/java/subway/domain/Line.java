package subway.domain;

import static subway.domain.LineConstants.*;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    private final String name;

    public Line(String name) {
        this.validateName(name);
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
    public LineDTO toDTO() {
        return new LineDTO(this.name);
    }
}
