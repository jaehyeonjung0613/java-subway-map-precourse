package subway.service;

import static subway.service.LineServiceConstants.*;

import java.util.List;
import java.util.Optional;
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
        Optional<Line> optionalLine = LineRepository.selectByName(line.getName());
        optionalLine.ifPresent((existedLine) -> {
            throw new IllegalArgumentException(ALREADY_EXISTS_LINE_NAME_MESSAGE);
        });
        LineRepository.addLine(line);
    }

    public void deleteLine(LineDTO lineDTO) {
        Line line = lineDTO.toEntity();
        Optional<Line> optionalLine = LineRepository.selectByName(line.getName());
        optionalLine.orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        LineRepository.deleteLineByName(line.getName());
    }
}
