package subway.repository;

import subway.domain.Line;
import subway.domain.Station;

public class SectionRepository {
    public static void init() {
        generate("2호선", "교대역", "강남역", "역삼역");
        generate("3호선", "교대역", "남부터미널역", "양재역", "매봉역");
        generate("신분당선", "강남역", "양재역", "양재시민의숲역");
    }

    private static void generate(String lineName, String... stationNames) {
        Line line = LineRepository.selectByName(lineName).get();
        Station station;
        for (String stationName : stationNames) {
            station = StationRepository.selectByName(stationName).get();
            line.addStation(station);
        }
    }
}
