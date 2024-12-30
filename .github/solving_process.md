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

public class StationViewController implements ViewController{
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
        this.addMenuItem("1", "역 등록", ()->{});
        this.addMenuItem("2", "역 삭제", ()->{});
        this.addMenuItem("3", "역 조회", ()->{});
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
        this.addMenuItem("2", "노선 관리", ()->{});
        this.addMenuItem("3", "구간 관리", ()->{});
        this.addMenuItem("4", "지하철 노선도 출력", ()->{});
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
        this.addMenuItem("1", "역 등록", ()->{});
        this.addMenuItem("2", "역 삭제", ()->{});
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
            } catch(IllegalArgumentException e) {
                Console.printNextLine();
                Console.printError(e.getMessage());
            } finally {
                Console.printNextLine();
            }
        } while(true);
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






