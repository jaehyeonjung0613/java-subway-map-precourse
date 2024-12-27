package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.service.StationService;

public class StationController implements Controller {
    private final StationService stationService = new StationService();

    public List<String> selectStationNameList() {
        List<Station> stationList = stationService.selectStationList();
        return stationList.stream().map(Station::getName).collect(Collectors.toList());
    }
}
