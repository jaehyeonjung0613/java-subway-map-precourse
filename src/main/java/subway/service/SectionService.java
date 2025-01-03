package subway.service;

import static subway.service.SectionServiceConstants.*;

import java.util.List;
import java.util.stream.Collectors;

import subway.dao.LineDAO;
import subway.domain.Line;
import subway.domain.Station;
import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class SectionService implements Service {
    public void insertSection(LineDTO lineDTO, StationDTO stationDTO, int position) throws IllegalArgumentException {
        Line line = LineRepository.selectByName(lineDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        Station station = StationRepository.selectByName(stationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        line.insertStation(position, station);
    }

    public void deleteSection(LineDTO lineDTO, StationDTO stationDTO) throws IllegalArgumentException {
        Line line = LineRepository.selectByName(lineDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        Station station = StationRepository.selectByName(stationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        line.removeStation(station);
    }

    public void initSection(LineDTO lineDTO, StationDTO fstStationDTO, StationDTO lstStationDTO) throws
        IllegalArgumentException {
        Line line = LineRepository.selectByName(lineDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        Station fstStation = StationRepository.selectByName(fstStationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_FIRST_STATION_NAME_MESSAGE));
        Station lstStation = StationRepository.selectByName(lstStationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LAST_STATION_NAME_MESSAGE));
        if (fstStation == lstStation) {
            throw new IllegalArgumentException(DUPLICATION_FINAL_STATION_NAME_MESSAGE);
        }
        line.insertStation(0, fstStation);
        line.addStation(lstStation);
    }

    public List<LineDAO> selectAllLineNameWithStationName() {
        List<Line> lineList = LineRepository.lines();
        return lineList.stream().map((line) -> {
                List<String> stationNameList = line.getStationList()
                    .stream()
                    .map(Station::getName)
                    .collect(Collectors.toList());
                return new LineDAO(line.getName(), stationNameList);
            })
            .collect(Collectors.toList());
    }
}
