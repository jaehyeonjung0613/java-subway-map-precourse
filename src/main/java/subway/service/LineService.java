package subway.service;

import java.util.List;

import subway.domain.Line;
import subway.repository.LineRepository;

public class LineService implements Service {
    public List<Line> selectLineList() {
        return LineRepository.lines();
    }

    public void insertLine(Line line) {
        LineRepository.addLine(line);
    }

    public void deleteLine(Line line) {
        LineRepository.deleteLineByName(line.getName());
    }
}
