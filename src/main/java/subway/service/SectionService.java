package subway.service;

import static subway.service.SectionServiceConstants.*;

import subway.domain.Line;
import subway.domain.Station;
import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class SectionService implements Service {
    public void insertSection(LineDTO lineDTO, StationDTO stationDTO, int position) throws IllegalArgumentException {
        Line line = LineRepository.selectByName(lineDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        Station station = StationRepository.selectByName(stationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        line.insertStation(position, station);
    }

    public void deleteSection(LineDTO lineDTO, StationDTO stationDTO) throws IllegalArgumentException {
        Line line = LineRepository.selectByName(lineDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        Station station = StationRepository.selectByName(stationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        line.removeStation(station);
    }
}
