package subway.dto;

import subway.domain.Station;

public class StationDTO implements DefaultDTO<Station> {
    private String name;

    public StationDTO() {
    }

    public StationDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public Station toEntity() {
        return new Station(this.name);
    }
}
