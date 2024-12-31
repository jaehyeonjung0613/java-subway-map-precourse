package subway.menu;

import java.util.List;
import java.util.stream.Collectors;

import subway.controller.Controller;
import subway.domain.Line;
import subway.service.LineService;

public class LineController implements Controller {
    private final LineService lineService = new LineService();

    public List<String> selectLineNameList() {
        List<Line> stationList = lineService.selectLineList();
        return stationList.stream().map(Line::getName).collect(Collectors.toList());
    }
}
