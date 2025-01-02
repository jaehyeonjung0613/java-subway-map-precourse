package subway.service;

import subway.domain.Line;
import subway.domain.Station;
import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class SectionService implements Service {
    public void insertSection(LineDTO lineDTO, StationDTO stationDTO, int position) {
        Line line = LineRepository.selectByName(lineDTO.getName()).get();
        Station station = StationRepository.selectByName(stationDTO.getName()).get();
        line.insertStation(position, station);
    }

    public void deleteSection(LineDTO lineDTO, StationDTO stationDTO) {
        Line line = LineRepository.selectByName(lineDTO.getName()).get();
        Station station = StationRepository.selectByName(stationDTO.getName()).get();
        line.removeStation(station);
    }
}
