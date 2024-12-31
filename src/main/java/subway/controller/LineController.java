package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.dto.LineDTO;
import subway.service.LineService;

public class LineController implements Controller {
    private final LineService lineService = new LineService();

    public List<String> selectLineNameList() {
        List<LineDTO> stationList = lineService.selectLineList();
        return stationList.stream().map(LineDTO::getName).collect(Collectors.toList());
    }

    public void insertLine(String name) {
        LineDTO lineDTO = new LineDTO(name);
        lineService.insertLine(lineDTO);
    }

    public void deleteLine(String name) {
        LineDTO lineDTO = new LineDTO(name);
        lineService.deleteLine(lineDTO);
    }
}
