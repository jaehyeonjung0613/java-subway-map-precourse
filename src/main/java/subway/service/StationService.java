package subway.service;

import java.util.List;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.dto.StationDTO;
import subway.repository.StationRepository;

public class StationService implements Service {
    public List<StationDTO> selectStationList() {
        return StationRepository.stations().stream().map(Station::toDTO).collect(Collectors.toList());
    }

    public void insertStation(StationDTO stationDTO) {
        Station station = stationDTO.toEntity();
        StationRepository.addStation(station);
    }

    public void deleteStation(StationDTO stationDTO) {
        Station station = stationDTO.toEntity();
        StationRepository.deleteStation(station.getName());
    }
}
