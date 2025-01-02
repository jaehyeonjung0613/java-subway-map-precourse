package subway.domain;

import static subway.domain.LineConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    private final String name;
    private final List<Station> stationList;

    public Line(String name) {
        this.validateName(name);
        this.name = name;
        this.stationList = new ArrayList<>();
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

    public void insertStation(int position, Station station) {
        station.addLine(this);
        this.stationList.add(position, station);
    }

    public void addStation(Station station) {
        this.insertStation(this.stationList.size(), station);
    }

    @Override
    public LineDTO toDTO() {
        return new LineDTO(this.name);
    }
}
