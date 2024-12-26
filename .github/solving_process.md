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





