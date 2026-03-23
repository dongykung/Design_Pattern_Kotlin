# Adapter Pattern
호환되지 않는 인터페이스를 가진 객체들이 함께 동작할 수 있도록 중간에 변환 계층을 두는 구조 패턴 <br>
![adapterimg](https://github.com/user-attachments/assets/06283066-b8b9-4f1b-862f-1c76ed8f022f) <br>
흔한 예시로 여행용 변압기 / 어댑터를 생각할 수 있습니다. (중간에서 전압을 변환해주어야 함) 
<br>

어댑터 패턴은 클래스를 상속하느냐, 합성을 사용하느냐에 따라 객체 어댑터와 / 클래스 어댑터로 나뉘게 됩니다.

## Object Adapter 구조
<img width="1160" height="640" alt="ObjectAdapter" src="https://github.com/user-attachments/assets/5bc92248-b074-4ce9-ac3c-c4b52db0a6e5" />

### Client Interface
다른 클래스들이 클라이언트 코드와 공동 작업할 수 있도록 따라야 하는 프로토콜, Adapter가 구현하는 Interface
```kotlin
// Client Interface
fun interface Volt220 {
    fun powerWith220V(volt: Int): String
}
```

<br>

### Service
일반적으로 타사 또는 레거시의 유용한 클래스를 뜻 <br>
Client 와 Adaptee(Service) 중간에서 호환성이 없는 둘을 연결시켜주는 역할을 담당
```kotlin
// Service (호환 안 되는 기존 클래스)
class AmericanDevice {
    fun powerWith110V(volt: Int): String {
        require(volt == 110) { "이 기기는 110V만 지원합니다 현재V: $volt"}
        return "110V로 동작 중"
    }
}
```

<br>

### Adapter
클라이언트와 서비스 양쪽에서 작동할 수 있는 클래스로, 서비스 객체를 래핑하는 동안 클라이언트 인터페이스를 구현
```kotlin
// Adapter
class VoltAdapter(
    private val adaptee: AmericanDevice // 합성 (adaptee를 필드로 가짐)
) : Volt220 {
    override fun powerWith220V(volt: Int): String {
        require(volt == 220) { "220V 전원만 연결 가능합니다 현재V: $volt"}
        val convertedVolt = convertTo110V(volt)
        println("어댑터: ${volt}V -> ${convertedVolt}V 변환 완료")
        return adaptee.powerWith110V(convertedVolt) // 위임함
    }

    private fun convertTo110V(volt: Int): Int {
        return volt / 2
    }
}
```

<br>

### Client
기존 시스템을 어댑터를 통해 이용하려는 쪽. Client Interface를 통하여 Service를 이용할 수 있게 된다.
```kotlin
fun main() {
    val americanDevice = AmericanDevice()
    val adapter: Volt220 = VoltAdapter(americanDevice)
    println(adapter.powerWith220V(220))
}
```

<br>
<br>

## Class Adapter 구조
<img width="1100" height="640" alt="ClassAdapter" src="https://github.com/user-attachments/assets/c05330c1-9f46-434e-8d27-7ad0bf170a89" />
상속을 사용하기 때문에 객체를 래핑할 필요가 없다.

<br>

### Client Interface
```kotlin
// Client Interface
interface ClassVolt220 {
    fun powerWith220V(volt: Int): String
}
```

<br>

### Service
```kotlin
// Service
open class ClassAmericanDevice {
    fun powerWith110V(volt: Int): String {
        require(volt == 110) { "이 기기는 110V만 지원합니다 현재V: $volt" }
        return "110V 로 동작 중"
    }
}
```

<br>

### Adapter
```kotlin
// 클래스 어댑터: 상속 + 구현
class ClassVoltAdapter : ClassAmericanDevice(), ClassVolt220 {
    override fun powerWith220V(volt: Int): String {
        require(volt == 220) { "220V 전원만 연결 가능합니다 현재V: $volt"}
        val convertedVolt = convertTo110V(volt)
        println("어댑터: ${volt}V -> ${convertedVolt}V 변환 완료")
        return powerWith110V(convertedVolt) // 자기 자신의 메서드 (상속)
    }

    private fun convertTo110V(volt: Int): Int = volt / 2
}
```

<br>

### Client
```kotlin
fun main() {
    val adapter: ClassVolt220 = ClassVoltAdapter()
    println(adapter.powerWith220V(220))
}
```

<br> <br>

## Object Adapter vs Class Adapter
어떤 Adapter 패턴을 사용해야 할까? <br>
Kotlin에서는 다중 상속을 지원하지 않기 때문에 Class Adapter는 제한적 입니다. 때문에 Object Adapter 패턴을 많이 사용합니다. 

<br>

어댑터 패턴은 이미 동작하는 기존 클래스를 인터페이스만 바꿔서 연결하는 것입니다. 때문에 다중 상속이 안되는 Kotlin에서는 Objetc Adapter 패턴을 권장합니다.

<br>
<br>

## Adapter Class를 매번 생성해야 하나?
Adapter가 자신의 상태를 가지고 있지 않고 변환 로직이 복잡하지 않다면 Kotlin의 확장함수를 통해 Adapter Class를 생략할 수 있습니다.
```kotlin
fun interface KVolt220 {
    fun powerWith220V(volt: Int): String
}

class JapanDevice {
    fun powerWith110V(volt: Int): String {
        require(volt == 110) { "110V만 지원합니다 현재:${volt}V" }
        return "110V 정상 동작"
    }
}

// Adapter Class 생략 후 확장함수로 대체
fun JapanDevice.toVolt220V(): KVolt220 {
    val device = this
    return KVolt220 { volt ->
        require(volt == 220) { "220V 전원만 연결 가능합니다 현재V: $volt"}
        val convertedVolt = volt / 2
        println("어댑터: ${volt}V -> ${convertedVolt}V 변환 완료")
        device.powerWith110V(convertedVolt)
    }
}

fun main() {
    val adapter: KVolt220 = JapanDevice().toVolt220V()
    println(adapter.powerWith220V(220))
}
```

<br>
<br>

## 언제 사용하는가?
- 기존 클래스(레거시)를 사용하고 싶지만 그 인터페이스가 나머지 코드와 호환되지 않을 때
- 이미 만들어진 클래스를 새로운 인터페이스(API)에 맞게 개조할때
- 서로 다른 모듈 / 시스템 간 연결 (모듈 A는 Dto를 반환, 모듈 B는 Entity를 기대하는 식으로 데이터 형식이 다를 때)

<br>
<br>

## 장점
- 프로그램의 기본 비즈니스 로직에서 인터페이스 또는 데이터 변환 코드를 분리할 수 있습니다. -> SRP 준수
- 기존의 클래스를 건드리지 않고 클라이언트 인터페이스를 통해 어댑터와 작동 -> OCP 준수

<br>
<br>

## 단점
- 다수의 새로운 인터페이스와 클래스들을 도입해야 하므로 코드의 전반적인 복잡성이 증가합니다
- Service 코드를 변경하는게 더 쉬울 수도 있는 경우가 있습니다

<br>
<br>

## 예제
- [Object-Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX1_ObjectAdapter.kt)
  - Object-Adapter 패턴에 대해 알아봅니다
- [Class-Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX2_ClassAdapter.kt)
  - Class-Adpater 패턴에 대해 알아봅니다
- [Extension-Fuction](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX3_ExtensionFunction.kt)
  - 확장 함수를 사용하여 Adapter Class를 생략하는 방법에 대해 알아봅니다 
