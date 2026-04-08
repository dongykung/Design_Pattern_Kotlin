# Strategy
실행 중(런타임)에 알고리즘(행위)을 선택하여 객체의 동작을 런타임에 변경할 수 있게하는 행동 패턴

즉, 수행하는 알고리즘이 여러 개일 때 각 알고리즘들을 미리 전략으로 캡슐화(클래스로 분리)해 두고, 
상황이나 필요에 따라 동적으로 교체하여 사용할 수 있게 해주는 패턴

<br>
<br>

## 구조
<img width="440" height="370" alt="wjsfir" src="https://github.com/user-attachments/assets/8caf20f7-9461-4f62-a3f8-4fbc0b16862f" />

<br><br>

### Strategy
전략 구현체들의 인터페이스 (무엇을 하는지에 집중) <br>
내부 구현체에서 어떻게 하는지에 집중
```kotlin
interface PaymentStrategy {
    fun pay(amount: Int)
}
```

<br>
<br>

### ConcreteStrategy
전략 구현체들 <br>
인터페이스에서 정의한 행위에 대한 알고리즘을 수행
```kotlin
class KakaoPayStrategy : PaymentStrategy {
    override fun pay(amount: Int) {
        println("카카오페이 $amount 결제")
    }
}

class ApplePayStrategy : PaymentStrategy {
    override fun pay(amount: Int) {
        println("애플페이 $amount 결제")
    }
}

class CreditCardStrategy : PaymentStrategy {
    override fun pay(amount: Int) {
        println("신용카드 $amount 결제")
    }
}
```

<br>
<br>

### Context
전략을 사용하는 객체 (합성을 이용함)
```kotlin
class MusinsaShoppingCart(
    private var payment: PaymentStrategy
) {
    fun setPaymentStrategy(paymentStrategy: PaymentStrategy) {
        // 검증 및 로깅 로직..
        this.payment = paymentStrategy
    }

    fun checkout(amount: Int) {
        payment.pay(amount)
    }
}
```

<br>
<br>

### Client
```kotlin
fun main() {
    val kakaoPayment = KakaoPayStrategy()
    val sp = MusinsaShoppingCart(kakaoPayment)
    sp.checkout(100000)

    sp.setPaymentStrategy(ApplePayStrategy())
    sp.checkout(500000)

    sp.setPaymentStrategy(CreditCardStrategy())
    sp.checkout(2000)
}
```

<br>
<br>

## 다른 패턴과의 비교
사실 객체지향에 대한 공부를 했다면 아주 익숙한 구조라고 생각한다. <br>

Payment에 대한 추상화, 합성, 캡슐화, 인터페이스에 의존하여 결합도 감소, 런타임 중 구현체 변경 등 — 객체지향에서 자주 등장하는 개념들이 전략 패턴에서 볼 수 있다.

### Bridge vs Strategy
두 모든 패턴은 다른 객체에 작업을 위임하는 합성을 기반으로 합니다. <br>
하지만 이 패턴들은 서로 다른 문제들을 해결한다..

Strategy는 런타임 중 행동 교체가 목적이고, Bridge는 추상-구현 분리로 클래스 폭발 방지가 목적이다. <br>
즉, Strategy 패턴을 사용하다 요구사항이 많아져 변형의 축이 늘어날 때 Bridge를 활용하여 클래스 폭박을 막을 수 있다.

<br>
<br>

## 언제 사용하는가?
- 런타임(실행 중)에 객체의 동작 방식을 실시간으로 바꿔야 할 때
- 비슷한 동작을 하지만 내부 구현만 조금씩 다른 클래스들이 파편화되어 있을 때
- 비즈니스 로직과 알고리즘 구현을 분리할때 (MusinsaShoppingCart는 어덯게 결제했는지 몰라도 됨)

<br>
<br>

## 장점
- 런타임 중 구현 객체를 변경할 수 있다
- 기존 코드를 변경하지 않고 새로운 기능을 도입할 수 있다(OCP 준수)


<br>
<br>

## 단점
- 알고리즘이 많지 않고 거의 변하지 않는다면, 패턴과 함께 사용되는 새로운 클래스들과 인터페이스들로 더 복잡해질 수 있다.
  - 트레이드 오프를 잘 고려해야 한다.
- 상황에 맞는 전략을 적절히 선택할 수 있어야 한다.

<br>
<br>

## 예제
- [EX1-기본 전략 패턴](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/strategy/EX1_BasicStrategy.kt)
- [EX2-전략+브릿지](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/strategy/EX2_Strategy%2BBridge.kt)
