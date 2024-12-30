package subway.service;

import static subway.repository.StationRepository.*;

import java.util.List;

import subway.domain.Station;

public class StationService implements Service {
    public List<Station> selectStationList() {
        return stations();
    }

    public void insertStation(Station station) {
        addStation(station);
    }
}
