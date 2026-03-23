# Bridge Pattern
큰 클래스 또는 밀접하게 관련된 클래스들의 집합을 두 개의 개별 계층구조(추상화 및 구현) 
로 나눈 후 각각 독립적으로 개발할 수 있도록 하는 구조 디자인 패턴입니다.

- 하나의 타입이 두 개 이상의 독립적인 변화 축을 동시에 가질 때, 이를 별도의 인터페이스로 분리하고 조합으로 연결하는 구조 패턴
- 구현부에서 추상층을 분리하여 각자 독립적으로 변형할 수 있게 하는 패턴
<br>

## 구조
<img width="1120" height="760" alt="bridge" src="https://github.com/user-attachments/assets/36fe9c38-85fa-4969-abb8-c8dc6786ce21" />

<br>
<br>

### Abstraction
클라이언트가 사용하는 상위 수준의 인터페이스(또는 추상 클래스) <br>
내부에 Implementation 참조를 가지고 있고 자신의 기능 안에서 Implementation 메서드를 호출하여 위임
```kotlin
abstract class Shape(
    protected val color: Color
) {
    abstract fun draw()
    
    fun drawWithBorder() {
        println("테두리 그리기")
        draw()
    }
}
```

<br>
<br>

### Implementation
Abstraction과 독립적으로 변화하는 축을 정의하는 인터페이스
```kotlin
interface Color {
    fun setColor()
}
```

<br>
<br>

### Concrete Implementation
Implementation 인터페이스를 구현한 클래스
```kotlin
class Red : Color {
    override fun setColor() = println("Set Red")
}

class Blue : Color {
    override fun setColor() = println("Set Blue")
}

class Green : Color {
    override fun setColor() = println("Set Green")
}
```

<br>
<br>

### Refined Abstraction
Abstraction을 확장한 구체적인 변형. <br>
각 방시으로 기능을 구체화 하거나 새로운 기능을 추가할 수 있다. 
```kotlin
class Circle(color: Color) : Shape(color) {
    override fun draw() {
        color.setColor()
        println("draw Circle")
    }
}
```

<br>
<br>

### Bridge 
Abstraction이 Implementation을 필드로 포함하는 연결 관계
<img width="920" height="400" alt="brdige2" src="https://github.com/user-attachments/assets/ed948350-ef34-45d0-817a-142b4d19fcd0" />

<br>
<br>

## 언제 사용하는가?
- 클래스 계층 구조를 얕게 만듦으로써 시스템에서 구체 클래스의 수를 줄이고 싶을 때
- 부모 클래스를 수정했을 때 자식 클래스에서 발견하기 어려운 버그가 발생하는 현상을 예방하고 싶을 때
- 런타임​(실행시간)​에 구현을 전환할 수 있어야 할 때(추상화 내부의 구현 객체를 바꿀 수 있음)

<br>
<br>

Shape 이라는 interface가 있고 이를 구현하는 Circle, Rectangle이 있다고 가정해보자. 도형의 색상은 Red, Blue가 존재한다.
```kotlin
interface Shape {
    fun draw()
}

// 빨강 원, 사각형
// 파랑 원, 사각형이 있다고 해보자

class RedCircle : Shape {
    override fun draw() = println("RedCircle")
}

class BlueCircle : Shape {
    override fun draw() = println("BlueCircle")
}

class RedRectangle : Shape {
    override fun draw() = println("RedRectangle")
}

class BlueRectangle : Shape {
    override fun draw() = println("BlueRectangle")
}
```
이 때 만약 도형 하나가 더 추가되고(삼각형) 색상도 추가가되면(초록색) 어덯게 해야할가? <br>
조합하여 클래스를 따로 만들어 주면 된다. (초록x원), (초록x네모), (초록x삼각형), (파랑x삼각형)... <br>
그럼 총 9개의 클래스가 생겨나게 된다. 

도형과 색상의 종류가 점점 더 늘어나면 어떻게 될가? `클래스 폭발` 문제가 발생한다.

<br>
<br>

## 왜 이렇게 복잡한 클래스 계층이 생기는가?
Shape과 Color라는 독립적으로 확장되는 두 가지 축이 하나의 타입 안에 결합되어 있기 때문이다. <br>
모양이 늘어나도 색에는 영향이 없고, 색이 늘어나도 모양에는 영향이 없지만, 이 두 축을 하나의 클래스로 조합하면 NxM 개의 클래스가 필요해진다. 

<br>

이렇게 서브 클래스가 많아지는 문제는 Decorator 패턴에서 상속의 문제에서도 찾아볼 수 있었다. <br>
다만 Decorator는 하나의 축에서 기능 조합이 폭발하는 문제를 Wrapping으로 해결한 반면, Bridge는 독립적인 두 축이 곱해지는 문제를 분리와 조합(Composition)으로 해결한다.

<br>

그래서 밀접하게 관련된 클래스들의 집합을 개별 계층구조로 나눈 후 독립적으로 개발하고 조합을 통해 클래스 계층 구조를 얕게 만들어 구체 클래스의 수를 줄일 수 있다.

<br>
<br>

## 장점
- 클래스 계층이 납작해진 덕분에 확장에 용이하며 코드를 이해하기 더 쉽다
- 구현부의 추상층 분리 + 의존성 주입과(주로 많이 사용) 사용 -> DIP를 준수한다(테스트 용이)
- 기존 코드를 수정하지 않고 새로운 추상화와 구현을 도입할 수 있다 -> OCP 준수
- 분리된 추상층으로 자기 책임에만 집중하게 된다 - SRP 준수


<br>
<br>

## 단점
- 결합도가 높은 클래스에 패턴을 적용하여 코드를 더 복잡하게 만들 수 있다

<br>
<br>

## 예제
- [게임 캐릭터 예제 - 문제점](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex1_ProblemWithCharacter.kt)
  - 게임 캐릭터를 예시로 Bridge 패턴 적용 이전에 어떤 문제가 있는지 알아봅니다
- [게임 캐릭터 예제 - 브리지 적용](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex1_SolveBridgeWithCharacter.kt)
  - 예제 1에서 Bridge 패턴 적용 후 어떤 변화가 생기는지에 대해 알아봅니다
- [도형 예제 - 문제점](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex2_ProblemWithShape.kt)
  - Shape과 Color를 예시로 Bridge 패턴 적용 이전에 어떤 문제가 있는지 알아봅니다
- [도형 예제 - 브리지 적용](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex2_SolveBridgeWithShape.kt)
  - 예제 2에서 Bridge 패턴 적용 후 어떤 변화가 생기는지에 대해 알아봅니다   
