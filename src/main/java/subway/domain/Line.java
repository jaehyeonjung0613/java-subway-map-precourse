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

    public void insertStation(int position, Station station) throws IllegalArgumentException {
        if (this.stationList.contains(station)) {
            throw new IllegalArgumentException(ALREADY_CONTAIN_STATION_MESSAGE);
        }
        if (position < 0 || position > this.stationList.size()) {
            throw new IllegalArgumentException(String.format(RANGE_OVER_ORDER_FORMAT, 0, this.stationList.size()));
        }
        station.addLine(this);
        this.stationList.add(position, station);
    }

    public void addStation(Station station) throws IllegalArgumentException {
        this.insertStation(this.stationList.size(), station);
    }

    public void removeStation(Station station) throws IllegalArgumentException {
        if (!this.stationList.contains(station)) {
            throw new IllegalArgumentException(NOT_CONTAIN_STATION_MESSAGE);
        }
        station.removeLine(this);
        this.stationList.remove(station);
    }

    @Override
    public LineDTO toDTO() {
        return new LineDTO(this.name);
    }
}
