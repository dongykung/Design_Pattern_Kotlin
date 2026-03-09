# Factory Method
부모 클래스에서 객체들을 생성할 수 있는 인터페이스를 제공하지만, 자식 클래스들이 생성될 객체들의 유형을 변경할 수 있도록 하는 생성 패턴 

객체 생성에 필요한 과정을 템플릿 처럼 미리 구성해놓고, 객체 생성에 관한 전처리, 후처리를 통해 생성 과정을 다양하게 처리하여 객체를 유연하게 정할 수 있다.

## 구조
<img width="1320" height="760" alt="structure-2x" src="https://github.com/user-attachments/assets/b412113b-070f-4f17-85b2-3f89ebfb2ddd" />

### Creator
최상위 공장 클래스로서, 팩토리 메서드를 추상화하여 서브 클래스로 하여금 구현하도록 함
- someOperation: 공통 비즈니스  로직, 객체 생성에 관한 전처리, 후처리를 템플릿화한 method
- createProduct: 서브 공장 클래스에서 재정의할 객체 생성 추상 method
```kotlin
// Creator
interface PizzaStore {
    // createProduct
    fun createPizza(): Pizza

    // some operation
    fun order(): Pizza {
        val pizza = createPizza()
        pizza.prepare()
        pizza.box()
        return pizza
    }
}
```
<br>

### ConcreteCreator
각 서브 공장 클래스들은 이에 맞는 제품 객체를 반환하도록 추상 메서드를 재정의
- createProduct()를 각자 다르게 구현한다.
```kotlin
// ConcreteCreatorA
class SeoulPizzaStore : PizzaStore {
    override fun createPizza(): Pizza {
        return MeatPizza()
    }
}

// ConcreteCreatorB
class NewYorkPizzaStore : PizzaStore {
    override fun createPizza(): Pizza {
        return SeafoodPizza()
    }
}
```

<br>

### Product
제품 추상화
```kotlin
// Product
interface Pizza {
    val name: String
    fun prepare()
    fun box()
}
```

<br>

### ConcreteProduct
제품 구현체
```kotlin
// Product Impl (ConcreteProductA)
class MeatPizza : Pizza {
    override val name: String = "MeatPizza"
    override fun prepare() = println("$name 준비중")
    override fun box() = println("$name 포장 완료")
}

// Product Impl (ConcreteProductB)
class SeafoodPizza : Pizza {
    override val name: String = "SeafoodPizza"
    override fun prepare() = println("$name 준비중")
    override fun box() = println("$name 포장 완료")
}
```

<br>

### 흐름
```kotlin
fun main() {
    val store: PizzaStore = SeoulPizzaStore()
    store.order()

    val store2: PizzaStore = NewYorkPizzaStore()
    store2.order()
}
```

<br>
<br>

## 언제사용하는가
- 구체 타입에 대한 결합도를 낮추고 싶을 때
- 객체의 정확한 타입을 모를 때
- 라이브러리 또는 프레임워크의 사용자들에게 내부 컴포넌트들을 확장하는 방법을 제공하고 싶을 때
- 기존 객체들을 매번 재구축하는 대신 이들을 재사용하여 시스템 리소스를 절약하고 싶을 때
    -     
<br>

## 장점
- 생성자(Creator)와 구현 객체(ConcreteProduct)의 강한 결합을 피할 수 있다
- 공통 비즈니스 로직을 처리할 수 있다
- 캡슐화, 추상화를 통해 생성되는 객체의 구체적인 타입을 감출 수 있다
- Creator는 흐름을 관리하고 ConcreteCreator가 생성에 대한 책임을 가지므로 단일 책임 원칙(SRP)를 준수한다
- ConcreteCreator가 추가되어도 기존 코드 수정 업이 새로운 ConcreteCreator를 만들면 된다 = 개방-폐쇄 원칙(OCP)을 준수한다
- 추상화에 의존하므로 의존성 역전 원칙(DIP)를 준수한다

<br>

## 단점
- 패턴을 구현하기 위해 새로운 자식 공장 클래스들을 정의하고 구현해야하기 때문에 서브 클래스의 수가 많아진다
- 코드가 더 복잡해진다
