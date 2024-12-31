package subway.domain;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    private final String name;

    public Line(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public LineDTO toDTO() {
        return new LineDTO(this.name);
    }
}
