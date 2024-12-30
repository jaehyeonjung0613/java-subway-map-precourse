package subway.service;

import static subway.service.StationServiceConstants.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.dto.StationDTO;
import subway.repository.StationRepository;

public class StationService implements Service {
    public List<StationDTO> selectStationList() {
        return StationRepository.stations().stream().map(Station::toDTO).collect(Collectors.toList());
    }

    public void insertStation(StationDTO stationDTO) throws IllegalArgumentException {
        Station station = stationDTO.toEntity();
        Optional<Station> optionalStation = StationRepository.selectByName(station.getName());
        optionalStation.ifPresent((existedStation) -> {
            throw new IllegalArgumentException(ALREADY_EXISTS_STATION_NAME_MESSAGE);
        });
        StationRepository.addStation(station);
    }

    public void deleteStation(StationDTO stationDTO) throws IllegalArgumentException {
        Station station = stationDTO.toEntity();
        Optional<Station> existedStation = StationRepository.selectByName(station.getName());
        existedStation.orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        StationRepository.deleteStation(station.getName());
    }
}
