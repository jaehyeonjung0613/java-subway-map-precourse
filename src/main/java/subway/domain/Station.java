package subway.domain;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    private final String name;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public StationDTO toDTO() {
        return new StationDTO(this.name);
    }
}
