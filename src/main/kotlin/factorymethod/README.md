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
abstract class PizzaStore {
    // createProduct
    protected abstract fun createPizza(): Pizza

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
class SeoulPizzaStore : PizzaStore() {
    override fun createPizza(): Pizza {
        return MeatPizza()
    }
}

// ConcreteCreatorB
class NewYorkPizzaStore : PizzaStore() {
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
- 
