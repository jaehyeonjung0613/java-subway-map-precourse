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



