package subway.service;

import java.util.List;

import subway.domain.Station;
import subway.repository.StationRepository;

public class StationService implements Service {
    public List<Station> selectStationList() {
        return StationRepository.stations();
    }

    public void insertStation(Station station) {
        StationRepository.addStation(station);
    }

    public void deleteStation(Station station) {
        StationRepository.deleteStation(station.getName());
    }
}
