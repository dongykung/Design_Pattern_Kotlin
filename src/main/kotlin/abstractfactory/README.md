# Abstract Factory 
추상 팩토리는 관련 객체들의 구상 클래스들을 지정하지 않고도 관련 객체들의 모음을 생성할 수 있도록 하는 생성패턴입니다.

연관성이 있는 객체 군이 여러개 있을 경우 이들을 그룹화 하여 추상화하고 팩토리 객체에서 집합으로 묶은 객체 군을 구현화 하는 생성 패턴 입니다. <br>
예를들어 Component 들도 Android 컴포넌트 / IOS 컴포넌트로 나뉘게 될 때, 제품군들의 관리와 확장하기 용이하게 패턴화한 것이라고 할 수 있습니다.

## 구조
<img width="1440" height="900" alt="structure-2x (1)" src="https://github.com/user-attachments/assets/c78b1e86-7689-4bae-8742-f9f68fb098dd" />

<br>
<br>

### Abstract Product
각 타입의 제품들을 추상화한 인터페이스
```kotlin
interface Component {
    fun render()
}

// Abstract ProductA
interface Button : Component

// Abstract ProductB
interface CheckBox : Component
```

<br>

### Concrete Product
추상화한 제품의 구현체, 이 객체는 팩토리 객체로부터 생성된다
```kotlin
// Concrete ProductA1
class AndroidButton : Button {
    override fun render() {
        println("AndroidButton Render")
    }
}

// Concrete ProductA2
class IOSButton : Button {
    override fun render() {
        println("IOSButton Render")
    }
}

// Concrete ProductB1
class AndroidCheckBox : CheckBox {
    override fun render() {
        println("AndroidCheckBox Render")
    }
}

// Concrete ProductB2
class IOSCheckBox : CheckBox {
    override fun render() {
        println("IOSCheckBox Render")
    }
}
```

<br>

### AbstractFactory
최상위 공장 클래스로 여러개의 제품들을 생성하는 여러 메소드들을 추상화한다.
```kotlin
// Abstract Factory
interface MobileComponentFactory {
    fun createButton(): Button
    fun createCheckBox(): CheckBox
}
```

<br>

### ConcreteFactoy
서브 공장 클래스로 타입에 맞는 제품 객체를 반환하는 메소드들을 구현한다
```kotlin
// ConcreteFactory1
class AndroidComponentFactory : MobileComponentFactory {
    override fun createButton(): Button {
        return AndroidButton()
    }

    override fun createCheckBox(): CheckBox {
        return AndroidCheckBox()
    }
}

// ConcreteFactory2
class IOSComponentFactory : MobileComponentFactory {
    override fun createButton(): Button {
        return IOSButton()
    }

    override fun createCheckBox(): CheckBox {
        return IOSCheckBox()
    }
}
```

<br>

### 동작
```kotlin
enum class OSType {
    ANDROID, IOS
}

fun main() {
    val os: OSType = OSType.ANDROID

    val componentFactory: MobileComponentFactory = when (os) {
        OSType.ANDROID -> AndroidComponentFactory()
        OSType.IOS -> IOSComponentFactory()
    }

    val button: Button = componentFactory.createButton()
    val checkBox: CheckBox = componentFactory.createCheckBox()

    button.render()
    checkBox.render()
}
// AndroidButton Render
// AndroidCheckBox Render
```

<br>
<br>

## Factory Method vs Abstract Factory
모두 팩토리 객체를 통해서 객체를 생성하고 구체적인 타입을 감추는 생성 패턴이다. 둘 모두 생성자와 구현 객체의 강한 결합을 피할 수 있다.
<br>

그러나 주의할 점으로 Abstract Factory가 Factory Method의 상위 호환이 아니라는 것이다. <br>
FactoryMethod는 객체 생성 이후 공통화된 비즈니스 로직을 정의하고 한 Factory당 한 종류의 객체 생성을 지원한다.
<br>

Abstract Factory는 생성해야 할 객체 집한 군의 공통점에 초점을 맞추고 한 Factory에서 서로 연관된 여러 종료의 객체 생성을 지원한다.
| 구분 | 팩토리 메서드 패턴 | 추상 팩토리 패턴 |
|------|-------------------|-----------------|
| **목적** | 구체적인 객체 생성 과정을 하위 또는 구체적인 클래스로 옮기는 것 | 관련 있는 여러 객체를 구체적인 클래스에 의존하지 않고 만들 수 있게 해주는 것 |
| **생성 범위** | 한 Factory당 한 종류의 객체 생성 지원 | 한 Factory에서 서로 연관된 여러 종류의 객체 생성 지원 (제품군 생성 지원) |
| **포커스**  | 메서드 레벨에서 포커스를 맞춤으로써, 클라이언트의 ConcreteProduct 인스턴스의 생성 및 구성에 대한 의존을 감소 | 클래스(Factory) 레벨에서 포커스를 맞춤으로써, 클라이언트의 ConcreteProduct 인스턴스 군의 생성 및 구성에 대한 의존을 감소 |

<br>
<br>

## 언제 사용하는가
- 제품군의 제품을 사용해야 할 때 해당 제품들의 구체적인 클래스들에 의존하고 싶지 않을 때
- 클래스의 Factory Method들의 집합이 여러 책임을 가질 때(클래스가 여러 제품 유형을 만들 때 - 버튼도 만들고 체크박스도 만들고)
- 여러 제품군 중 하나를 선택해서 시스템을 설정해야하고 한 번 구성한 제품을 다른 것으로 대체할 수도 있을 때
- 제품에 대한 클래스 라이브러리를 제공하고, 그들의 구현이 아닌 인터페이스를 노출시키고 싶을 때 

<br>
<br>

## 장점
- 팩토리에서 생성되는 제품들의 상호 호환이 가능하다(Android, IOS 모두 사용 가능)
- 제품들과 클라이언트 코드 사이의 결합도를 낮출 수 있다
- SRP 준수 = 제품군에 관한 제품 생성 코드를 한 곳으로 추출
- OCP 준수 = 기존 코드 변경없이 새로운 유형의 제품군을 추가할 수 있다

<br>
<br>

## 단점
- 패턴과 함께 새로운 인터페이스들과 클래스들이 많이 도입되기 때문에 코드가 필요 이상으로 복잡해질 수 있다
- 기존 추상 팩토리의 세부사항이 변경되면 모든 팩토리에 대한 수정이 필요하다
- 새로운 종류의 제품을 지원하게 되면 기존 팩토리를 수정해야 한다(새로운 컴포넌트가 추가될 때)

### 참고자료 

[추상 팩토리 - INPA](https://inpa.tistory.com/entry/GOF-%F0%9F%92%A0-%EC%B6%94%EC%83%81-%ED%8C%A9%ED%86%A0%EB%A6%ACAbstract-Factory-%ED%8C%A8%ED%84%B4-%EC%A0%9C%EB%8C%80%EB%A1%9C-%EB%B0%B0%EC%9B%8C%EB%B3%B4%EC%9E%90)

[refactoring.guru](https://refactoring.guru/ko/design-patterns/abstract-factory)
