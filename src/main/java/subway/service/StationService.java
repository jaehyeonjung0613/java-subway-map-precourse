package subway.service;

import java.util.List;

import subway.domain.Station;
import subway.repository.StationRepository;

public class StationService implements Service {
    public List<Station> selectStationList() {
        return StationRepository.stations();
    }
}
