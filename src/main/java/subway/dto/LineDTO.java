package subway.dto;

import subway.domain.Line;

public class LineDTO implements DefaultDTO<Line> {
    private String name;

    public LineDTO() {
    }

    public LineDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public Line toEntity() {
        return new Line(this.name);
    }
}
