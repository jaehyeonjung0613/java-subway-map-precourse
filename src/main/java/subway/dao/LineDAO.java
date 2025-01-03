package subway.dao;

import java.util.Collections;
import java.util.List;

public class LineDAO {
    private final String name;
    private final List<String> stationNameList;

    public LineDAO(String name, List<String> stationNameList) {
        this.name = name;
        this.stationNameList = stationNameList;
    }

    public String getName() {
        return name;
    }

    public List<String> getStationNameList() {
        return Collections.unmodifiableList(this.stationNameList);
    }
}
