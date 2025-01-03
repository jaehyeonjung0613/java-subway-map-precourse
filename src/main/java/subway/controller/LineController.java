package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.service.LineService;
import subway.service.SectionService;

public class LineController implements Controller {
    private final LineService lineService = new LineService();
    private final SectionService sectionService = new SectionService();

    public List<String> selectLineNameList() {
        List<LineDTO> stationList = lineService.selectLineList();
        return stationList.stream().map(LineDTO::getName).collect(Collectors.toList());
    }

    public void insertLine(String lineName, String fstStationName, String lstStationName) throws
        IllegalArgumentException {
        LineDTO lineDTO = new LineDTO(lineName);
        StationDTO fstStationDTO = new StationDTO(fstStationName);
        StationDTO lstStationDTO = new StationDTO(lstStationName);
        lineService.insertLine(lineDTO);
        try {
            sectionService.initSection(lineDTO, fstStationDTO, lstStationDTO);
        } catch (IllegalArgumentException e) {
            lineService.deleteLine(lineDTO);
            throw e;
        }
    }

    public void deleteLine(String name) {
        LineDTO lineDTO = new LineDTO(name);
        lineService.deleteLine(lineDTO);
    }
}
