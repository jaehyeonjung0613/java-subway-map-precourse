package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.dto.StationDTO;
import subway.service.StationService;

public class StationController implements Controller {
    private final StationService stationService = new StationService();

    public List<String> selectStationNameList() {
        List<StationDTO> stationList = stationService.selectStationList();
        return stationList.stream().map(StationDTO::getName).collect(Collectors.toList());
    }

    public void insertStation(String name) {
        StationDTO stationDTO = new StationDTO(name);
        stationService.insertStation(stationDTO);
    }

    public void deleteStation(String name) {
        StationDTO stationDTO = new StationDTO(name);
        stationService.deleteStation(stationDTO);
    }
}
