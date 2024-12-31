package subway.service;

import java.util.List;
import java.util.stream.Collectors;

import subway.domain.Line;
import subway.dto.LineDTO;
import subway.repository.LineRepository;

public class LineService implements Service {
    public List<LineDTO> selectLineList() {
        return LineRepository.lines().stream().map(Line::toDTO).collect(Collectors.toList());
    }

    public void insertLine(LineDTO lineDTO) {
        Line line = lineDTO.toEntity();
        LineRepository.addLine(line);
    }

    public void deleteLine(LineDTO lineDTO) {
        Line line = lineDTO.toEntity();
        LineRepository.deleteLineByName(line.getName());
    }
}
