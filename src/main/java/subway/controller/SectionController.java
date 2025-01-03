package subway.controller;

import static subway.controller.SectionControllerConstants.*;

import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.service.SectionService;
import subway.util.Validation;

public class SectionController implements Controller {
    private final SectionService sectionService = new SectionService();

    public void insertSection(String lineName, String stationName, String _position) throws IllegalArgumentException {
        LineDTO lineDTO = new LineDTO(lineName);
        StationDTO stationDTO = new StationDTO(stationName);
        if (!Validation.isNumeric(_position)) {
            throw new IllegalArgumentException(NOT_NUMERIC_ORDER_MESSAGE);
        }
        int position = Integer.parseInt(_position);
        sectionService.insertSection(lineDTO, stationDTO, position);
    }

    public void deleteSection(String lineName, String stationName) {
        LineDTO lineDTO = new LineDTO(lineName);
        StationDTO stationDTO = new StationDTO(stationName);
        sectionService.deleteSection(lineDTO, stationDTO);
    }
}
