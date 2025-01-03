package subway.domain;

import static subway.domain.StationConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    private final String name;
    private final List<Line> lineList;

    public Station(String name) throws IllegalArgumentException {
        validateName(name);
        this.name = name;
        this.lineList = new ArrayList<>();
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

    public void addLine(Line line) throws IllegalArgumentException {
        if (this.lineList.contains(line)) {
            throw new IllegalArgumentException(ALREADY_CONTAIN_LINE_MESSAGE);
        }
        this.lineList.add(line);
    }

    public void removeLine(Line line) throws IllegalArgumentException {
        if (!this.lineList.contains(line)) {
            throw new IllegalArgumentException(NOT_CONTAIN_LINE_MESSAGE);
        }
        this.lineList.remove(line);
    }

    public boolean isContainLine() {
        return !this.lineList.isEmpty();
    }

    @Override
    public StationDTO toDTO() {
        return new StationDTO(this.name);
    }
}
