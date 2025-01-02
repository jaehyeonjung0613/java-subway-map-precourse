package subway.controller;

import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.service.SectionService;

public class SectionController implements Controller {
    private final SectionService sectionService = new SectionService();

    public void insertSection(String lineName, String stationName, String _position) {
        LineDTO lineDTO = new LineDTO(lineName);
        StationDTO stationDTO = new StationDTO(stationName);
        int position = Integer.parseInt(_position);
        sectionService.insertSection(lineDTO, stationDTO, position);
    }

    public void deleteSection(String lineName, String stationName) {
        LineDTO lineDTO = new LineDTO(lineName);
        StationDTO stationDTO = new StationDTO(stationName);
        sectionService.deleteSection(lineDTO, stationDTO);
    }
}
