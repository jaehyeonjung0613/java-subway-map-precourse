# 🧐 지하철 노선도 미션

[우아한테크코스](https://github.com/woowacourse) precourse 문제
중 [지하철 노선도 미션](https://github.com/woowacourse/java-subway-map-precourse) 풀이 기록하기.

계층형 아키텍처 구조와 MVC 패턴을 이용하여 개발하고, 입출력 및 프로그래밍 요구사항을 부합하도록 풀어 볼 예정.

## 0. 설계

### infrastructure

|  클래스   | 기능          |
|:------:|:------------|
| Subway | - 지하철 노선 관리 |

### menu

|     클래스     | 기능                                |
|:-----------:|:----------------------------------|
|  MenuItem   | - 메뉴 필드(label, handler) 저장        |
| CommandLine | - 메뉴 필드별 명령어 저장                   |
|    Menu     | - 메뉴 추가<br/> - 메뉴 출력<br/> - 메뉴 선택 |

### view

| 클래스  | 기능                           |
|:----:|:-----------------------------|
| View | - 화면 출력<br/> - 메뉴 선택 요청 및 처리 |

### controller

|        클래스         | 기능                                  |
|:------------------:|:------------------------------------|
|     Controller     | - parameter 유효성 체크 및 가공             |
|   ViewController   | - View 생성<br/> - 화면 동작 처리           |
| RestViewController | - ViewController 실행 처리<br/> - 에러 처리 |

### service

|   클래스   | 기능        |
|:-------:|:----------|
| Service | - 비즈니스 처리 |

### repository

|    클래스     | 기능            |
|:----------:|:--------------|
| Repository | - CRUD 데이터 처리 |

### domain

|   클래스   | 기능   |
|:-------:|:-----|
| Station | - 역  |
|  Line   | - 노선 |
| Section | - 구간 |

### ui

|   클래스   | 기능          |
|:-------:|:------------|
| Console | - 콘솔 입출력 처리 |

## 1. 애플리케이션 Skeleton

```java
// Subway.java

package subway.infrastructure;

public class Subway {
    private static Subway instance;

    private Subway() {
    }

    protected static Subway getInstance() {
        if (instance == null) {
            instance = new Subway();
        }
        return instance;
    }

    public void run() {
    }

    public void finish() {
        instance = null;
    }
}
```

Subway 애플리케이션 런처 동작 및 종료 구성.

```java
// SubwayBuilder.java

package subway.infrastructure;

public class SubwayBuilder {

    public Subway build() {
        return Subway.getInstance();
    }
}
```

런처 builder 생성.

```java
// Application.java

package subway;

import subway.infrastructure.Subway;
import subway.infrastructure.SubwayBuilder;

public class Application {
    public static void main(String[] args) {
        Subway subway = new SubwayBuilder().build();
        try {
            subway.run();
        } finally {
            subway.finish();
        }
    }
}
```

애플리케이션 실행 및 종료.

## 2. 콘솔 입출력

```java
// ConsoleConstants.java

package subway.ui;

public final class ConsoleConstants {
    private ConsoleConstants() {
    }

    public static final String HEADER_LOG_FORMAT = "## %s";
    public static final String INFO_LOG_FORMAT = "[INFO] %s";
    public static final String ERROR_LOG_FORMAT = "[ERROR] %s";
}
```

출력 관련 상수 정의.

```java
// Console.java

package subway.ui;

import static subway.ui.ConsoleConstants.*;

import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readline() {
        return scanner.nextLine();
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void printNextLine() {
        System.out.println();
    }

    public static void printFormat(String format, Object... args) {
        println(String.format(format, args));
    }

    public static void printHeader(String message) {
        printFormat(HEADER_LOG_FORMAT, message);
    }

    public static void printInfo(String message) {
        printFormat(INFO_LOG_FORMAT, message);
    }

    public static void printError(String message) {
        printFormat(ERROR_LOG_FORMAT, message);
    }
}
```

콘솔 입출력 기능 생성.

## 3. View 생성 및 처리 Skeleton

```java
// View.java

package subway.view;

public class View {
    private final String name;

    public View(String name) {
        this.name = name;
    }

    public void show() {
    }

    public String requestCommand() {
    }

    public void onSelect(String command) {
    }

    public boolean isClose() {
        return true;
    }

    public String getName() {
        return name;
    }
}
```

화면 동작에 필요한 기능 구성.

```java
// ViewController.java

package subway.controller;

import subway.view.View;

public interface ViewController {
    View make();
}
```

화면 생성 인터페이스 Controller 정의.

```java
// RestViewController.java

package subway.controller;

import subway.ui.Console;
import subway.view.View;

public class RestViewController {

    public static void execute(ViewController viewController) {
        View view = viewController.make();

        String command;
        do {
            try {
                view.show();
                command = view.requestCommand();
                view.onSelect(command);
            } catch (IllegalArgumentException e) {
                Console.printError(e.getMessage());
            } finally {
                Console.printNextLine();
            }
        } while (!view.isClose());
    }
}
```

화면 생성 및 동작 절차를 수행하도록 처리.

## 4. 메뉴 관리 Skeleton

```java
// MenuItem.java

package subway.menu;

public class MenuItem {
    private final String label;
    private final Runnable handler;

    public MenuItem(String label, Runnable handler) {
        this.label = label;
        this.handler = handler;
    }

    public String getLabel() {
        return label;
    }

    public Runnable getHandler() {
        return handler;
    }
}
```

메뉴 항목별 필요 정보 구성.

```java
// CommandLine.java

package subway.menu;

public class CommandLine {
    private final String command;
    private final MenuItem menuItem;

    public CommandLine(String command, MenuItem menuItem) {
        this.command = command;
        this.menuItem = menuItem;
    }

    public String getCommand() {
        return command;
    }

    public String getLabel() {
        return this.menuItem.getLabel();
    }

    public Runnable getHandler() {
        return this.menuItem.getHandler();
    }
}
```

명령어와 메뉴 항목 매핑.

```java
// Menu.java

package subway.menu;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    private final List<CommandLine> commandLineList;
    protected final T viewController;

    public Menu(T viewController) {
        this.commandLineList = new ArrayList<>();
        this.viewController = viewController;
        this.setup();
    }

    abstract protected void setup();
}
```

메뉴 항목 관리 및 CommandLine 과 ViewController 기능 매핑을 위한 구성.

## 5. 메뉴 항목 출력

```java
// MenuConstants.java

package subway.menu;

public final class MenuConstants {
    private MenuConstants() {
    }

    public static final String MENU_ITEM_OUTPUT_FORMAT = "%s. %s";
}
```

출력 관련 상수 정의.

```java
// Menu.java

package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    private CommandLine getCommandLine(String command) {
        return this.commandLineList.stream()
            .filter(commandLine -> commandLine.getCommand().equals(command))
            .findFirst()
            .get();
    }

    public final String output(String command) {
        CommandLine commandLine = this.getCommandLine(command);
        return String.format(MENU_ITEM_OUTPUT_FORMAT, commandLine.getCommand(), commandLine.getLabel());
    }

    public final String outputAll() {
        StringBuilder builder = new StringBuilder();
        for (CommandLine commandLine : this.commandLineList) {
            builder.append(this.output(commandLine.getCommand()));
            builder.append("\n");
        }
        return builder.toString();
    }
}
```

메뉴 항목 출력 기능 생성.

## 6. 메뉴 항목 추가

```java
// Menu.java

package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    public final void insertMenuItem(int position, String command, String label, Runnable handler) {
        MenuItem menuItem = new MenuItem(label, handler);
        CommandLine commandLine = new CommandLine(command, menuItem);
        this.commandLineList.add(position, commandLine);
    }

    public final void addMenuItem(String command, String label, Runnable handler) {
        this.insertMenuItem(this.commandLineList.size(), command, label, handler);
    }
}
```

메뉴 항목 추가 및 삽입 기능 생성.

## 7. 메뉴 항목 선택

```java
// Menu.java

package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    public final void select(String command) {
        CommandLine commandLine = this.getCommandLine(command);
        commandLine.getHandler().run();
    }
}
```

메뉴 항목 선택 기능 생성.

## 8. 메뉴 종료

```java
// MenuState.java

package subway.menu;

public enum MenuState {
    IDLE, OPEN, CLOSE
}
```

메뉴 컨트롤을 위한 상태값 정의.

```java
// Menu.java

package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    private MenuState state = MenuState.IDLE;

    public Menu(T viewController) {
        this.commandLineList = new ArrayList<>();
        this.viewController = viewController;
        this.setup();
        this.state = MenuState.OPEN;
    }

    public final void close() {
        this.state = MenuState.CLOSE;
    }

    public final boolean isClose() {
        return MenuState.CLOSE.equals(this.state);
    }
}
```

메뉴 종료 기능 생성.

## 9. View 동작 구현

```java
// ViewConstants.java

package subway.view;

public final class ViewConstants {
    private ViewConstants() {
    }

    public static final String REQUEST_COMMAND_QUERY = "원하는 기능을 선택하세요.";
}
```

메뉴 선택 질의문 정의.

```java
// View.java

package subway.view;

import static subway.view.ViewConstants.*;

import subway.controller.ViewController;
import subway.menu.Menu;
import subway.ui.Console;

public class View {
    private final String name;
    private final Menu menu;

    public View(String name, Menu menu) {
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void show() {
        Console.printHeader(this.getName());
        Console.println(this.menu.outputAll());
    }

    public String requestCommand() {
        Console.printHeader(REQUEST_COMMAND_QUERY);
        return Console.readline();
    }

    public void onSelect(String command) {
        Console.printNextLine();
        this.menu.select(command);
    }

    public boolean isClose() {
        return this.menu.isClose();
    }
}
```

Menu 기능을 활용하여 View 동작 구현.

## 10. 메인 화면 출력

```java
// MainViewController.java

package subway.controller;

import subway.menu.MainMenu;
import subway.ui.Console;
import subway.view.View;

public class MainViewController implements ViewController {

    @Override
    public View make() {
        MainMenu menu = new MainMenu(this);
        return new View("메인 화면", menu);
    }
}
```

```java
// MainMenu.java

package subway.menu;

import subway.controller.MainViewController;

public class MainMenu extends Menu<MainViewController> {
    public MainMenu(MainViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 관리", () -> {
        });
        this.addMenuItem("2", "노선 관리", () -> {
        });
        this.addMenuItem("3", "구간 관리", () -> {
        });
        this.addMenuItem("4", "지하철 노선도 출력", () -> {
        });
        this.addMenuItem("Q", "종료", this::close);
    }
}
```

Main View 및 Menu 생성.

```java
// Subway.java

package subway.infrastructure;

import subway.controller.MainViewController;
import subway.controller.RestViewController;

public class Subway {
    private static Subway instance;

    private final MainViewController mainViewController;

    private Subway() {
        mainViewController = new MainViewController();
    }

    public void run() {
        RestViewController.execute(mainViewController);
    }
}
```

애플리케이션 launcher 실행 구현.

## 11. 역 관리 화면 출력

```java
// StationViewController.java

package subway.controller;

import subway.menu.StationMenu;
import subway.view.View;

public class StationViewController implements ViewController {
    @Override
    public View make() {
        StationMenu menu = new StationMenu(this);
        return new View("역 관리 화면", menu);
    }
}
```

```java
// StationMenu.java

package subway.menu;

import subway.controller.StationViewController;

public class StationMenu extends Menu<StationViewController> {
    public StationMenu(StationViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 등록", () -> {
        });
        this.addMenuItem("2", "역 삭제", () -> {
        });
        this.addMenuItem("3", "역 조회", () -> {
        });
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

Station View 및 Menu 생성.

```java
// MainViewController.java

package subway.controller;

import subway.menu.MainMenu;
import subway.view.View;

public class MainViewController implements ViewController {
    private final StationViewController stationViewController = new StationViewController();

    public void openStationView() {
        RestViewController.execute(stationViewController);
    }
}
```

Station View 실행 기능 생성.

```java
// MainMenu.java

package subway.menu;

import subway.controller.MainViewController;

public class MainMenu extends Menu<MainViewController> {
    @Override
    protected void setup() {
        this.addMenuItem("1", "역 관리", this.viewController::openStationView);
        this.addMenuItem("2", "노선 관리", () -> {
        });
        this.addMenuItem("3", "구간 관리", () -> {
        });
        this.addMenuItem("4", "지하철 노선도 출력", () -> {
        });
        this.addMenuItem("Q", "종료", this::close);
    }
}
```

Station View 실행 메뉴 항목과 매핑.

## 12. 역 조회

```java
// Service.java

package subway.service;

public interface Service {
}
```

비즈니스 인터페이스 Service 정의.

```java
// StationService.java

package subway.service;

import java.util.List;

import subway.domain.Station;
import subway.repository.StationRepository;

public class StationService implements Service {
    public List<Station> selectStationList() {
        return StationRepository.stations();
    }
}
```

Station 목록 반환 기능 생성.

```java
// Controller.java

package subway.controller;

public interface Controller {
}
```

비즈니스 인터페이스 Controller 정의.

```java
// StationController.java

package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.service.StationService;

public class StationController implements Controller {
    private final StationService stationService = new StationService();

    public List<String> selectStationNameList() {
        List<Station> stationList = stationService.selectStationList();
        return stationList.stream().map(Station::getName).collect(Collectors.toList());
    }
}
```

Station 명 목록 반환 기능 생성.

```java
// StationViewController.java

package subway.controller;

import java.util.List;

import subway.menu.StationMenu;
import subway.ui.Console;
import subway.view.View;

public class StationViewController implements ViewController {
    private final StationController stationController = new StationController();

    public void printStationNameAll() {
        Console.printHeader("역 목록");
        List<String> stationNameList = stationController.selectStationNameList();
        for (String stationName : stationNameList) {
            Console.printInfo(stationName);
        }
    }
}
```

```java
// StationMenu.java

package subway.menu;

import subway.controller.StationViewController;

public class StationMenu extends Menu<StationViewController> {
    public StationMenu(StationViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 등록", () -> {
        });
        this.addMenuItem("2", "역 삭제", () -> {
        });
        this.addMenuItem("3", "역 조회", this.viewController::printStationNameAll);
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

역 목록 조회 구현.

## 13. 역 데이터 초기화

```java
// StationRepository.java

package subway.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import subway.domain.Station;

public class StationRepository {
    public static void init() {
        addStation(new Station("교대역"));
        addStation(new Station("강남역"));
        addStation(new Station("역삼역"));
        addStation(new Station("남부터미널역"));
        addStation(new Station("양재역"));
        addStation(new Station("양재시민의숲역"));
        addStation(new Station("매봉역"));
    }
}
```

기초 데이터 초기화.

```java
// Subway.java

package subway.infrastructure;

import subway.controller.MainViewController;
import subway.controller.RestViewController;
import subway.repository.StationRepository;

public class Subway {
    private void init() {
        StationRepository.init();
    }

    public void run() {
        this.init();
        RestViewController.execute(mainViewController);
    }
}
```

애플리케이션 구동시 역 기초 데이터 초기화.

## 14. 메뉴 선택 후 종료

```java
// Menu.java
package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    public final void handleSelectAfterClose(Runnable handler) {
        handler.run();
        this.close();
    }
}
```

메뉴 선택 이후 닫기 기능 생성.

```java
// StationMenu.java

package subway.menu;

import subway.controller.StationViewController;

public class StationMenu extends Menu<StationViewController> {
    public StationMenu(StationViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 등록", () -> {
        });
        this.addMenuItem("2", "역 삭제", () -> {
        });
        this.addMenuItem("3", "역 조회", () -> this.handleSelectAfterClose(this.viewController::printStationNameAll));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

역 조회 후 메인 화면으로 넘어가도록 수정.

## 15. 올바른 명령어 받을때까지 입력

```java
// Menu.java

package subway.menu;

import static subway.menu.MenuConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.controller.ViewController;

public abstract class Menu<T extends ViewController> {
    public final Runnable select(String command) {
        CommandLine commandLine = this.getCommandLine(command);
        return commandLine.getHandler();
    }
}
```

메뉴 항목 선택시 handler 반환되도록 수정.

```java
// View.java

package subway.view;

import static subway.view.ViewConstants.*;

import subway.menu.Menu;
import subway.ui.Console;

public class View {
    public Runnable requestCommand() {
        String command;
        do {
            try {
                Console.printHeader(REQUEST_COMMAND_QUERY);
                command = Console.readline();
                return this.menu.select(command);
            } catch (IllegalArgumentException e) {
                Console.printNextLine();
                Console.printError(e.getMessage());
            } finally {
                Console.printNextLine();
            }
        } while (true);
    }
}
```

명령어 입력시 해당 메뉴 항목의 handler 반환되도록 수정(기존 onSelect 제거).

handler 처리는 외부에 위임.

```java
// RestViewController.java

package subway.controller;

import subway.ui.Console;
import subway.view.View;

public class RestViewController {

    public static void execute(ViewController viewController) {
        View view = viewController.make();

        Runnable handler;
        do {
            try {
                view.show();
                handler = view.requestCommand();
                handler.run();
            } catch (IllegalArgumentException e) {
                Console.printError(e.getMessage());
                Console.printNextLine();
            }
        } while (!view.isClose());
        Console.printNextLine();
    }
}
```

직접 handler 호출하도록 변경.

## 16. 역 등록

```java
// StationService.java

package subway.service;

import static subway.repository.StationRepository.*;

import java.util.List;

import subway.domain.Station;

public class StationService implements Service {
    public List<Station> selectStationList() {
        return stations();
    }

    public void insertStation(Station station) {
        addStation(station);
    }
}
```

```java
// StationController.java

package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.service.StationService;

public class StationController implements Controller {
    private final StationService stationService = new StationService();

    public List<String> selectStationNameList() {
        List<Station> stationList = stationService.selectStationList();
        return stationList.stream().map(Station::getName).collect(Collectors.toList());
    }

    public void insertStation(String name) {
        Station station = new Station(name);
        stationService.insertStation(station);
    }
}
```

역 등록 Controller, Service 기능 생성.

```java
// StationViewController.java

package subway.controller;

import java.util.List;

import subway.menu.StationMenu;
import subway.ui.Console;
import subway.view.View;

public class StationViewController implements ViewController {
    private final StationController stationController = new StationController();

    public void registerStation() {
        Console.printHeader("등록할 역 이름을 입력하세요.");
        String name = Console.readline();
        Console.printNextLine();
        stationController.insertStation(name);
        Console.printInfo("지하철 역이 등록되었습니다.");
    }
}
```

```java
// StationMenu.java

package subway.menu;

import subway.controller.StationViewController;

public class StationMenu extends Menu<StationViewController> {
    public StationMenu(StationViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 등록", () -> this.handleSelectAfterClose(this.viewController::registerStation));
        this.addMenuItem("2", "역 삭제", () -> {
        });
        this.addMenuItem("3", "역 조회", () -> this.handleSelectAfterClose(this.viewController::printStationNameAll));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

역 등록 기능 구현.

## 17. 역 삭제

```java
// StationService.java

package subway.service;

import java.util.List;

import subway.domain.Station;
import subway.repository.StationRepository;

public class StationService implements Service {
    public void deleteStation(Station station) {
        StationRepository.deleteStation(station.getName());
    }
}
```

```java
// StationController.java

package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.service.StationService;

public class StationController implements Controller {
    private final StationService stationService = new StationService();

    public void deleteStation(String name) {
        Station station = new Station(name);
        stationService.deleteStation(station);
    }
}
```

역 삭제 Controller, Service 기능 생성.

```java
// StationViewController.java

package subway.controller;

import java.util.List;

import subway.menu.StationMenu;
import subway.ui.Console;
import subway.view.View;

public class StationViewController implements ViewController {
    private final StationController stationController = new StationController();

    public void removeStation() {
        Console.printHeader("삭제할 역 이름을 입력하세요");
        String name = Console.readline();
        Console.printNextLine();
        stationController.deleteStation(name);
        Console.printInfo("지하철 역이 삭제되었습니다.");
    }
}
```

```java
// StationMenu.java

package subway.menu;

import subway.controller.StationViewController;

public class StationMenu extends Menu<StationViewController> {
    public StationMenu(StationViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 등록", () -> this.handleSelectAfterClose(this.viewController::registerStation));
        this.addMenuItem("2", "역 삭제", () -> this.handleSelectAfterClose(this.viewController::removeStation));
        this.addMenuItem("3", "역 조회", () -> this.handleSelectAfterClose(this.viewController::printStationNameAll));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

역 삭제 기능 구현.

## 18. 역 Entity, DTO 분리

```java
package subway.domain;

import subway.dto.DefaultDTO;

public interface Entity<T extends DefaultDTO> {
    T toDTO();
}
```

```java
// DefaultDTO.java

package subway.dto;

import subway.domain.Entity;

public interface DefaultDTO<T extends Entity> {
    T toEntity();
}
```

Entity, DTO 인터페이스 생성.

```java
// Station.java

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
```

```java
// StationDTO.java

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
```

역 Entity, DTO 구현.

```java
// StationController.java

package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.dto.StationDTO;
import subway.service.StationService;

public class StationController implements Controller {
    private final StationService stationService = new StationService();

    public List<String> selectStationNameList() {
        List<StationDTO> stationList = stationService.selectStationList();
        return stationList.stream().map(StationDTO::getName).collect(Collectors.toList());
    }

    public void insertStation(String name) {
        StationDTO stationDTO = new StationDTO(name);
        stationService.insertStation(stationDTO);
    }

    public void deleteStation(String name) {
        StationDTO stationDTO = new StationDTO(name);
        stationService.deleteStation(stationDTO);
    }
}
```

```java
// StationService.java

package subway.service;

import java.util.List;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.dto.StationDTO;
import subway.repository.StationRepository;

public class StationService implements Service {
    public List<StationDTO> selectStationList() {
        return StationRepository.stations().stream().map(Station::toDTO).collect(Collectors.toList());
    }

    public void insertStation(StationDTO stationDTO) {
        Station station = stationDTO.toEntity();
        StationRepository.addStation(station);
    }

    public void deleteStation(StationDTO stationDTO) {
        Station station = stationDTO.toEntity();
        StationRepository.deleteStation(station.getName());
    }
}
```

Entity 아닌 DTO 로 전달.

## 19. 역 관리 유효성 체크

```java
// StationConstants.java

package subway.domain;

public final class StationConstants {
    private StationConstants() {
    }

    public static final int MIN_NAME_LENGTH = 2;

    public static final String EMPTY_NAME_MESSAGE = "지하철 역 이름은 필수로 입력되어야합니다.";
    public static final String MIN_NAME_LENGTH_FORMAT = "지하철 역 이름은 %d글자 이상이어야 합니다.";
}
```

```java
// Station.java

package subway.domain;

import static subway.domain.StationConstants.*;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    private final String name;

    public Station(String name) throws IllegalArgumentException {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (name.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(MIN_NAME_LENGTH_FORMAT, MIN_NAME_LENGTH));
        }
    }
}
```

역 이름 관련 유효성 체크.

```java
// StationRepository.java

package subway.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import subway.domain.Station;

public class StationRepository {
    private static final List<Station> stations = new ArrayList<>();

    public static Optional<Station> selectByName(String name) {
        return stations.stream().filter(station -> Objects.equals(station.getName(), name)).findFirst();
    }
}
```

```java
// StationServiceConstants.java

package subway.service;

public final class StationServiceConstants {
    private StationServiceConstants() {
    }

    public static final String ALREADY_EXISTS_STATION_NAME_MESSAGE = "이미 등록된 역 이름입니다.";
    public static final String NOT_EXISTS_STATION_NAME_MESSAGE = "존재하지 않은 역 이름입니다.";
}
```

```java
// StationService.java

package subway.service;

import static subway.service.StationServiceConstants.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.dto.StationDTO;
import subway.repository.StationRepository;

public class StationService implements Service {
    public void insertStation(StationDTO stationDTO) throws IllegalArgumentException {
        Station station = stationDTO.toEntity();
        Optional<Station> optionalStation = StationRepository.selectByName(station.getName());
        optionalStation.ifPresent((existedStation) -> {
            throw new IllegalArgumentException(ALREADY_EXISTS_STATION_NAME_MESSAGE);
        });
        StationRepository.addStation(station);
    }

    public void deleteStation(StationDTO stationDTO) throws IllegalArgumentException {
        Station station = stationDTO.toEntity();
        Optional<Station> existedStation = StationRepository.selectByName(station.getName());
        existedStation.orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        StationRepository.deleteStation(station.getName());
    }
}
```

역 등록 및 삭제시 미존재, 존재 유효성 체크.

## 20. 노선 관리 화면 출력

```java
// LineViewController.java

package subway.controller;

import subway.menu.LineMenu;
import subway.view.View;

public class LineViewController implements ViewController {
    @Override
    public View make() {
        LineMenu menu = new LineMenu(this);
        return new View("노선 관리 화면", menu);
    }
}
```

```java
// LineMenu.java

package subway.menu;

import subway.controller.LineViewController;

public class LineMenu extends Menu<LineViewController> {
    public LineMenu(LineViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "노선 등록", () -> {
        });
        this.addMenuItem("2", "노선 삭제", () -> {
        });
        this.addMenuItem("3", "노선 조회", () -> {
        });
        this.addMenuItem("B", "돌아가기", this::close);

    }
}
```

Line View 및 Menu 생성.

```java
// MainViewController.java

package subway.controller;

import subway.menu.MainMenu;
import subway.view.View;

public class MainViewController implements ViewController {
    private final LineViewController lineViewController = new LineViewController();

    public void openLineView() {
        RestViewController.execute(lineViewController);
    }
}
```

Line View 실행 기능 생성.

```java
// MainMenu.java

package subway.menu;

import subway.controller.MainViewController;

public class MainMenu extends Menu<MainViewController> {
    public MainMenu(MainViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 관리", this.viewController::openStationView);
        this.addMenuItem("2", "노선 관리", this.viewController::openLineView);
        this.addMenuItem("3", "구간 관리", () -> {
        });
        this.addMenuItem("4", "지하철 노선도 출력", () -> {
        });
        this.addMenuItem("Q", "종료", this::close);
    }
}
```

Line View 실행 메뉴 항목과 매핑.

## 21. 노선 조회

```java
// LineService.java

package subway.service;

import java.util.List;

import subway.domain.Line;
import subway.repository.LineRepository;

public class LineService implements Service {
    public List<Line> selectLineList() {
        return LineRepository.lines();
    }
}
```

Line 목록 반환 기능 생성.

```java
// LineController.java

package subway.controller;

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
```

Line 명 목록 반환 기능 생성.

```java
// LineViewController.java

package subway.controller;

import java.util.List;

import subway.menu.LineMenu;
import subway.ui.Console;
import subway.view.View;

public class LineViewController implements ViewController {
    private final LineController lineController = new LineController();

    @Override
    public View make() {
        LineMenu menu = new LineMenu(this);
        return new View("노선 관리 화면", menu);
    }

    public void printLineNameAll() {
        Console.printHeader("역 목록");
        List<String> lineNameList = lineController.selectLineNameList();
        for (String lineName : lineNameList) {
            Console.printInfo(lineName);
        }
    }
}
```

```java
// LineMenu.java

package subway.menu;

import subway.controller.LineViewController;

public class LineMenu extends Menu<LineViewController> {
    public LineMenu(LineViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "노선 등록", () -> {
        });
        this.addMenuItem("2", "노선 삭제", () -> {
        });
        this.addMenuItem("3", "노선 조회", () -> this.handleSelectAfterClose(this.viewController::printLineNameAll));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

노선 목록 조회 구현.

## 22. 노선 데이터 초기화

```java
// LineRepository.java

package subway.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import subway.domain.Line;
import subway.domain.Line;

public class LineRepository {
    public static void init() {
        addLine(new Line("2호선"));
        addLine(new Line("3호선"));
        addLine(new Line("신분당선"));
    }
}
```

기초 데이터 초기화.

```java
// Subway.java

package subway.infrastructure;

import subway.controller.MainViewController;
import subway.controller.RestViewController;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class Subway {
    private void init() {
        StationRepository.init();
        LineRepository.init();
    }

    public void run() {
        this.init();
        RestViewController.execute(mainViewController);
    }
}
```

애플리케이션 구동시 노선 기초 데이터 초기화.

## 23. 노선 등록

```java
// LineService.java

package subway.service;

import java.util.List;

import subway.domain.Line;
import subway.repository.LineRepository;

public class LineService implements Service {
    public void insertLine(Line line) {
        LineRepository.addLine(line);
    }
}
```

```java
// LineController.java

package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.controller.Controller;
import subway.domain.Line;
import subway.service.LineService;

public class LineController implements Controller {
    private final LineService lineService = new LineService();

    public void insertLine(String name) {
        Line line = new Line(name);
        lineService.insertLine(line);
    }
}
```

노선 등록 Controller, Service 기능 생성.

```java
// LineViewController.java

package subway.controller;

import java.util.List;

import subway.menu.StationMenu;
import subway.ui.Console;
import subway.view.View;

public class StationViewController implements ViewController {
    private final StationController stationController = new StationController();

    public void registerStation() {
        Console.printHeader("등록할 역 이름을 입력하세요.");
        String name = Console.readline();
        Console.printNextLine();
        stationController.insertStation(name);
        Console.printInfo("지하철 역이 등록되었습니다.");
    }
}
```

```java
// LineMenu.java

package subway.menu;

import subway.controller.LineViewController;

public class LineMenu extends Menu<LineViewController> {
    public LineMenu(LineViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "노선 등록", () -> this.handleSelectAfterClose(this.viewController::registerLine));
        this.addMenuItem("2", "노선 삭제", () -> {
        });
        this.addMenuItem("3", "노선 조회", () -> this.handleSelectAfterClose(this.viewController::printLineNameAll));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

노선 등록 기능 구현.

## 24. 노선 삭제

```java
// LineService.java

package subway.service;

import java.util.List;

import subway.domain.Line;
import subway.repository.LineRepository;

public class LineService implements Service {
    public void deleteLine(Line line) {
        LineRepository.deleteLineByName(line.getName());
    }
}
```

```java
// LineController.java

package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.controller.Controller;
import subway.domain.Line;
import subway.service.LineService;

public class LineController implements Controller {
    private final LineService lineService = new LineService();

    public void deleteLine(String name) {
        Line line = new Line(name);
        lineService.deleteLine(line);
    }
}
```

노선 삭제 Controller, Service 기능 생성.

```java
// LineViewController.java

package subway.controller;

import java.util.List;

import subway.menu.LineMenu;
import subway.ui.Console;
import subway.view.View;

public class LineViewController implements ViewController {
    private final LineController lineController = new LineController();

    public void removeLine() {
        Console.printHeader("삭제할 노선 이름을 입력하세요");
        String name = Console.readline();
        Console.printNextLine();
        lineController.deleteLine(name);
        Console.printInfo("지하철 노선이 삭제되었습니다.");
    }
}
```

```java
// LineMenu.java

package subway.menu;

import subway.controller.LineViewController;

public class LineMenu extends Menu<LineViewController> {
    public LineMenu(LineViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "노선 등록", () -> this.handleSelectAfterClose(this.viewController::registerLine));
        this.addMenuItem("2", "노선 삭제", () -> this.handleSelectAfterClose(this.viewController::removeLine));
        this.addMenuItem("3", "노선 조회", () -> this.handleSelectAfterClose(this.viewController::printLineNameAll));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

노선 삭제 기능 구현.

## 25. 노선 Entity, DTO 분리

```java
// Line.java

package subway.domain;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    private final String name;

    public Line(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public LineDTO toDTO() {
        return new LineDTO(this.name);
    }
}
```

```java
// LineDTO.java

package subway.dto;

import subway.domain.Line;

public class LineDTO implements DefaultDTO<Line> {
    private String name;

    public LineDTO() {
    }

    public LineDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public Line toEntity() {
        return new Line(this.name);
    }
}
```

노선 Entity, DTO 구현.

```java
// LineController.java

package subway.controller;

import java.util.List;
import java.util.stream.Collectors;

import subway.controller.Controller;
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
```

```java
// LineService.java

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
```

Entity 아닌 DTO 로 전달.

## 26. 노선 관리 유효성 체크

```java
// LineConstants.java

package subway.domain;

public final class LineConstants {
    private LineConstants() {
    }

    public static final int MIN_NAME_LENGTH = 2;

    public static final String EMPTY_NAME_MESSAGE = "지하철 노선 이름은 필수로 입력되어야합니다.";
    public static final String LESS_NAME_LENGTH_FORMAT = "지하철 노선 이름은 %d글자 이상이어야 합니다.";
}
```

```java
// Line.java

package subway.domain;

import static subway.domain.LineConstants.*;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    private final String name;

    public Line(String name) {
        this.validateName(name);
        this.name = name;
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (name.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(LESS_NAME_LENGTH_FORMAT, MIN_NAME_LENGTH));
        }
    }
}
```

노선 이름 관련 유효성 체크.

```java
// LineRepository.java

package subway.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import subway.domain.Line;

public class LineRepository {
    private static final List<Line> lines = new ArrayList<>();

    public static Optional<Line> selectByName(String name) {
        return lines.stream().filter(line -> Objects.equals(line.getName(), name)).findFirst();
    }
}
```

```java
// LineServiceConstants.java

package subway.service;

public final class LineServiceConstants {
    private LineServiceConstants() {
    }

    public static final String ALREADY_EXISTS_LINE_NAME_MESSAGE = "이미 등록된 노선 이름입니다.";
    public static final String NOT_EXISTS_LINE_NAME_MESSAGE = "존재하지 않은 노선 이름입니다.";
}
```

```java
// LineService.java

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
```

노선 등록 및 삭제시 미존재, 존재 유효성 체크.

## 27. 구간 관리 화면 출력

```java
// SectionViewController.java

package subway.controller;

import subway.menu.SectionMenu;
import subway.view.View;

public class SectionViewController implements ViewController {
    @Override
    public View make() {
        SectionMenu menu = new SectionMenu(this);
        return new View("구간 관리 화면", menu);
    }
}
```

```java
// SectionMenu.java

package subway.menu;

import subway.controller.SectionViewController;

public class SectionMenu extends Menu<SectionViewController> {
    public SectionMenu(SectionViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "구간 등록", () -> {
        });
        this.addMenuItem("2", "구간 삭제", () -> {
        });
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

Section View 및 Menu 생성.

```java
// MainViewController.java

package subway.controller;

import subway.menu.MainMenu;
import subway.view.View;

public class MainViewController implements ViewController {
    private final SectionViewController sectionViewController = new SectionViewController();

    public void openSectionView() {
        RestViewController.execute(sectionViewController);
    }
}
```

Section View 실행 기능 생성.

```java
// MainMenu.java

package subway.menu;

import subway.controller.MainViewController;

public class MainMenu extends Menu<MainViewController> {
    public MainMenu(MainViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "역 관리", this.viewController::openStationView);
        this.addMenuItem("2", "노선 관리", this.viewController::openLineView);
        this.addMenuItem("3", "구간 관리", this.viewController::openSectionView);
        this.addMenuItem("4", "지하철 노선도 출력", () -> {
        });
        this.addMenuItem("Q", "종료", this::close);
    }
}
```

Section View 실행 메뉴 항목과 매핑.

## 28. 구간 등록

```java
// Station.java

package subway.domain;

import static subway.domain.StationConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    private final String name;
    private final List<Line> lineList;

    public Station(String name) throws IllegalArgumentException {
        validateName(name);
        this.name = name;
        this.lineList = new ArrayList<>();
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (name.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(LESS_NAME_LENGTH_FORMAT, MIN_NAME_LENGTH));
        }
    }

    public void addLine(Line line) {
        this.lineList.add(line);
    }
}
```

```java
// Line.java

package subway.domain;

import static subway.domain.LineConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    private final String name;
    private final List<Station> stationList;

    public Line(String name) {
        this.validateName(name);
        this.name = name;
        this.stationList = new ArrayList<>();
    }

    private void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (name.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(LESS_NAME_LENGTH_FORMAT, MIN_NAME_LENGTH));
        }
    }

    public void insertStation(int position, Station station) {
        station.addLine(this);
        this.stationList.add(position, station);
    }

    public void addStation(Station station) {
        this.insertStation(this.stationList.size(), station);
    }
}
```

Station, Line Entity 에 N:N 매핑.

```java
// SectionService.java

package subway.service;

import subway.domain.Line;
import subway.domain.Station;
import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class SectionService implements Service {
    public void insertSection(LineDTO lineDTO, StationDTO stationDTO, int position) {
        Line line = LineRepository.selectByName(lineDTO.getName()).get();
        Station station = StationRepository.selectByName(stationDTO.getName()).get();
        line.insertStation(position, station);
    }
}
```

```java
// SectionController.java

package subway.controller;

import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.service.SectionService;

public class SectionController implements Controller {
    private final SectionService sectionService = new SectionService();

    public void insertSection(String lineName, String stationName, String _position) {
        LineDTO lineDTO = new LineDTO(lineName);
        StationDTO stationDTO = new StationDTO(stationName);
        int position = Integer.parseInt(_position);
        sectionService.insertSection(lineDTO, stationDTO, position);
    }
}
```

구간 등록 Controller, Service 기능 생성.

```java
// SectionViewController.java

package subway.controller;

import subway.menu.SectionMenu;
import subway.ui.Console;
import subway.view.View;

public class SectionViewController implements ViewController {
    private final SectionController sectionController = new SectionController();

    public void registerSection() {
        Console.printHeader("노선을 입력하세요.");
        String lienName = Console.readline();
        Console.printNextLine();
        Console.printHeader("역이름을 입력하세요.");
        String stationName = Console.readline();
        Console.printNextLine();
        Console.printHeader("순서를 입력하세요.");
        String position = Console.readline();
        Console.printNextLine();
        sectionController.insertSection(lienName, stationName, position);
        Console.printInfo("구간이 등록되었습니다.");
    }
}
```

```java
// SectionMenu.java

package subway.menu;

import subway.controller.SectionViewController;

public class SectionMenu extends Menu<SectionViewController> {
    public SectionMenu(SectionViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "구간 등록", () -> this.handleSelectAfterClose(this.viewController::registerSection));
        this.addMenuItem("2", "구간 삭제", () -> {
        });
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

구간 등록 기능 구현.

## 29. 구간 삭제

```java
// Station.java

package subway.domain;

import static subway.domain.StationConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    public void removeLine(Line line) {
        this.lineList.remove(line);
    }
}
```

```java
// Line.java

package subway.domain;

import static subway.domain.LineConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    public void removeStation(Station station) {
        station.removeLine(this);
        this.stationList.remove(station);
    }
}
```

Station, Line 목록 제거 기능 생성.

```java
// SectionService.java

package subway.service;

import subway.domain.Line;
import subway.domain.Station;
import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class SectionService implements Service {
    public void deleteSection(LineDTO lineDTO, StationDTO stationDTO) {
        Line line = LineRepository.selectByName(lineDTO.getName()).get();
        Station station = StationRepository.selectByName(stationDTO.getName()).get();
        line.removeStation(station);
    }
}
```

```java
// SectionController.java

package subway.controller;

import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.service.SectionService;

public class SectionController implements Controller {
    private final SectionService sectionService = new SectionService();

    public void deleteSection(String lineName, String stationName) {
        LineDTO lineDTO = new LineDTO(lineName);
        StationDTO stationDTO = new StationDTO(stationName);
        sectionService.deleteSection(lineDTO, stationDTO);
    }
}
```

구간 삭제 Controller, Service 기능 생성.

```java
// SectionViewController.java

package subway.controller;

import subway.menu.SectionMenu;
import subway.ui.Console;
import subway.view.View;

public class SectionViewController implements ViewController {
    private final SectionController sectionController = new SectionController();

    public void removeSection() {
        Console.printHeader("삭제할 구간의 노선을 입력하세요.");
        String lienName = Console.readline();
        Console.printNextLine();
        Console.printHeader("삭제할 구간의 역을 입력하세요.");
        String stationName = Console.readline();
        Console.printNextLine();
        sectionController.deleteSection(lienName, stationName);
        Console.printInfo("구간이 삭제되었습니다.");
    }
}
```

```java
// LineMenu.java

package subway.menu;

import subway.controller.SectionViewController;

public class SectionMenu extends Menu<SectionViewController> {
    public SectionMenu(SectionViewController viewController) {
        super(viewController);
    }

    @Override
    protected void setup() {
        this.addMenuItem("1", "구간 등록", () -> this.handleSelectAfterClose(this.viewController::registerSection));
        this.addMenuItem("2", "구간 삭제", () -> this.handleSelectAfterClose(this.viewController::removeSection));
        this.addMenuItem("B", "돌아가기", this::close);
    }
}
```

구간 삭제 기능 구현.

## 30. 구간 관리 유효성 체크

```java
// Validation.java

package subway.util;

import java.util.regex.Pattern;

public final class Validation {
    private Validation() {
    }

    public static boolean isNumeric(String str) {
        return Pattern.matches("^[0-9]*$", str);
    }
}
```

문자 숫자 형태 체크 기능 생성.

```java
// SectionControllerConstants.java

package subway.controller;

public final class SectionControllerConstants {
    private SectionControllerConstants() {
    }

    public static final String NOT_NUMERIC_ORDER_MESSAGE = "순서는 숫자만 입력 가능합니다.";
}
```

````java
// SectionController.java

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
}
````

구간 등록시 입력받은 순서 값 유효성 체크.

```java
// StationConstants.java

package subway.domain;

public final class StationConstants {
    private StationConstants() {
    }

    public static final String ALREADY_CONTAIN_LINE_MESSAGE = "이미 구간에 등록된 노선 이름입니다.";
    public static final String NOT_CONTAIN_LINE_MESSAGE = "구간에 등록되지 않은 노선 이름입니다.";
}
```

```java
// Station.java

package subway.domain;

import static subway.domain.StationConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    public void addLine(Line line) throws IllegalArgumentException {
        if (this.lineList.contains(line)) {
            throw new IllegalArgumentException(ALREADY_CONTAIN_LINE_MESSAGE);
        }
        this.lineList.add(line);
    }

    public void removeLine(Line line) throws IllegalArgumentException {
        if (!this.lineList.contains(line)) {
            throw new IllegalArgumentException(NOT_CONTAIN_LINE_MESSAGE);
        }
        this.lineList.remove(line);
    }
}
```

Station 에서 구간 등록 및 삭제시 Line 존재, 미존재 여부 체크.

```java
// LineConstants.java

package subway.domain;

public final class LineConstants {
    private LineConstants() {
    }

    public static final String ALREADY_CONTAIN_STATION_MESSAGE = "이미 구간에 등록된 역 이름입니다.";
    public static final String RANGE_OVER_ORDER_FORMAT = "%d ~ %d 범위 내 순서만 입력 가능합니다.";
    public static final String NOT_CONTAIN_STATION_MESSAGE = "구간에 등록되지 않은 역 이름입니다.";
}
```

```java
// Line.java

package subway.domain;

import static subway.domain.LineConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.LineDTO;

public class Line implements Entity<LineDTO> {
    public void insertStation(int position, Station station) throws IllegalArgumentException {
        if (this.stationList.contains(station)) {
            throw new IllegalArgumentException(ALREADY_CONTAIN_STATION_MESSAGE);
        }
        if (position < 0 || position > this.stationList.size()) {
            throw new IllegalArgumentException(String.format(RANGE_OVER_ORDER_FORMAT, 0, this.stationList.size()));
        }
        station.addLine(this);
        this.stationList.add(position, station);
    }

    public void addStation(Station station) throws IllegalArgumentException {
        this.insertStation(this.stationList.size(), station);
    }

    public void removeStation(Station station) throws IllegalArgumentException {
        if (!this.stationList.contains(station)) {
            throw new IllegalArgumentException(NOT_CONTAIN_STATION_MESSAGE);
        }
        station.removeLine(this);
        this.stationList.remove(station);
    }
}
```

Line 에서 구간 등록시 Station 존재와 순서 범위 체크하고 삭제시 Station 미존재 여부 체크.

```java
// SectionServiceConstants.java

package subway.service;

public final class SectionServiceConstants {
    private SectionServiceConstants() {
    }

    public static final String NOT_EXISTS_STATION_NAME_MESSAGE = "존재하지 않은 역 이름입니다.";
    public static final String NOT_EXISTS_LINE_NAME_MESSAGE = "존재하지 않은 노선 이름입니다.";
}
```

```java

// SectionService.java

package subway.service;

import static subway.service.SectionServiceConstants.*;

import subway.domain.Line;
import subway.domain.Station;
import subway.dto.LineDTO;
import subway.dto.StationDTO;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class SectionService implements Service {
    public void insertSection(LineDTO lineDTO, StationDTO stationDTO, int position) throws IllegalArgumentException {
        Line line = LineRepository.selectByName(lineDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        Station station = StationRepository.selectByName(stationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        line.insertStation(position, station);
    }

    public void deleteSection(LineDTO lineDTO, StationDTO stationDTO) throws IllegalArgumentException {
        Line line = LineRepository.selectByName(lineDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        Station station = StationRepository.selectByName(stationDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTS_LINE_NAME_MESSAGE));
        line.removeStation(station);
    }
}
```

구간 등록 및 삭제시 Station, Line 존재 여부 체크.

## 31. 역 삭제(노선 등록된 경우)

```java
// Station.java


package subway.domain;

import static subway.domain.StationConstants.*;

import java.util.ArrayList;
import java.util.List;

import subway.dto.StationDTO;

public class Station implements Entity<StationDTO> {
    public boolean isContainLine() {
        return !this.lineList.isEmpty();
    }
}
```

노선 등록 여부 확인 기능 생성.

```java
// StationServiceConstants.java

package subway.service;

public final class StationServiceConstants {
    private StationServiceConstants() {
    }
    
    public static final String CONTAIN_LINE_STATION_NAME_MESSAGE = "노선에 등록된 역 이름입니다.";
}
```

```java
// StationService.java

package subway.service;

import static subway.service.StationServiceConstants.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import subway.domain.Station;
import subway.dto.StationDTO;
import subway.repository.StationRepository;

public class StationService implements Service {
    public void deleteStation(StationDTO stationDTO) throws IllegalArgumentException {
        Optional<Station> optionalStation = StationRepository.selectByName(stationDTO.getName());
        Station station = optionalStation.orElseThrow(
            () -> new IllegalArgumentException(NOT_EXISTS_STATION_NAME_MESSAGE));
        if(station.isContainLine()) {
            throw new IllegalArgumentException(CONTAIN_LINE_STATION_NAME_MESSAGE);
        }
        StationRepository.deleteStation(station.getName());
    }
}
```

역 삭제시 노선 등록 여부 체크.