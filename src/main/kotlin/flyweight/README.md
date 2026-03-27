# Flyweight
각 객체에 모든 데이터를 유지하는 대신 여러 객체들 간에 상태의 공통 부분들을 공유하여 사용할 수 있는 RAM에 더 많은 객체들을 포함할 수 있도록 하는 구조 디자인 패턴

<br>

열심히 FPS 게임을 만들어서 친구들에게 게임을 배포했다고 가정해보겠습니다. <br>
그런데 어느 친구는 게임을 문제없이 잘 플레이하는데 어느 친구는 게임하다 자꾸 게임 앱이 꺼진다고 연락이 왔습니다.

무슨 차이점이 있었을가요? <br>
A친구의 RAM은 16GB였지만 B친구의 RAM은 4GB 였습니다. 

<br>

문제 상황을 디버깅 해보니 총알이 점점 많아질수록 메모리를 점유하는 것이 문제였습니다. <br>
즉 총알 1개가 생성될 때마다 총알 객체가 메모리를 점유하기 때문에 총알이 수 만개 이상이 생기면 B친구의 RAM이 감당하지 못합니다.
<img width="653" height="314" alt="image" src="https://github.com/user-attachments/assets/001a8a0c-b147-47f2-9727-fc6e7218621f" /> <br>

그럼 어떻게 이 문제를 해결할 수 있을가요? <br>
바로 총알 객체들 간에 공통 부분들을 공유하여 사용하는 것입니다. 
```kotlin
class Particle(
    var x: Double, // 좌표
    var y: Double, // 좌표
    var speed: Double, // 속도
    val image: Bitmap, // 이미지
    val color: Color, // 색상
    val type: ParticleType // 타입
)
```
해당 클래스에서 공통화 할 수 있는 부분이 어떤 것이 있을가요? <br>
<img width="160" height="90" alt="ㄹlyweight" src="https://github.com/user-attachments/assets/8e7fa1f1-6979-4df2-9cf8-8c2f0598e3f4" /><br>
총알 모양이 위 그림으로 공통된다고 가정해보겠습니다. <br>
그럼 iamge와 color는 하나의 객체를 사용하여 나머지 객체에서 이를 공유하여 이러한 문제를 해결할 수 있습니다.

<br>
<br>

## intrinsic 와 extrinsic

### Intrinsic (고유한 상태)
고유한, 본질적인 이라는 뜻을 가지며 인스턴스가 어떠한 상황에서도 변하지 않음을 의미합니다. 즉 값이 불변이므로 언제 어디서든 공유해도 문제가 되지 않습니다. <br>
위 상황에서는 총알의 image와 color에 해당됩니다.

<br>

### Extrinsic (공유한 상태)
외적인, 외부로부터의 뜻을 가지며 인스턴스를 두는 장소나 상황에 따라서 변화하는 정보를 말합니다. 그래서 값이 언제 어디서 변화할지 모르기 때문에 이를 캐시해서 공유할수 는 없습니다. <br>
위 상황에서는 총알의 x y 좌표, speed에 해당됩니다.

<br>
<br>

## 구조
<img width="1280" height="780" alt="flyweight" src="https://github.com/user-attachments/assets/2ca37db9-ea44-4f75-a7b1-65c2e57cd36a" />

<br>

### Flyweight
우선 경량패턴을 적용하기 위해서는 클래스들의 필드를 고유한 상태 / 공유한 상태를 기준으로 나눕니다.
```kotlin
// 고유한 상태
class ParticleIntrinsic(
    val color: Color,
    val bitmap: Bitmap
)
```

<br>
<br>

### Context
각 상태를 가지는 unique한 상태를 들고있으며 공유 객체인 ParticleIntrinsic을 참조합니다.
```kotlin
class GameParticle(
    var x: Double,
    var y: Double,
    var speed: Double,
    val type: ParticleType,
    val intrinsic: ParticleIntrinsic
)
```

<br>
<br>

### FlyweightFactory 
cache 맵을 가지며 요청받은 타입의 Flyweight 객체가 있으면 반환하고, 없으면 생성하여 캐싱합니다.
```kotlin
object ParticleTypeFactory {
    private val cache = mutableMapOf<ParticleType, ParticleIntrinsic>()

    fun getInstance(type: ParticleType): ParticleIntrinsic = cache.getOrPut(type) {
        when (type) {
            BULLET -> ParticleIntrinsic(
                color = Color("#0xFFFFFF"),
                bitmap = Bitmap.create(32, 32)
            )

            MISSILE -> ParticleIntrinsic(
                color = Color("#0xFFF9F9"),
                bitmap = Bitmap.create(64, 64)
            )
        }
    }
}
```

<br>
이렇게 FlyweightFactory에 고유한 상태들을 캐싱함으로써 모든 총알 객체에서는 캐싱을 참조하여 더 이상 총알 객체마다 중복해서 image,color 객체를 생성하지 않고 공유하게 됩니다.

<br>
<br>

## 언제 사용하는가?
- 메모리를 절약하거나 최소한으로 사용해야 하는 경우
- 공통적인 인스턴스를 많이 생성하는 로직이 포함된 경우

<br>
<br>

## 단점
- Context(공유 상태)를 매번 복잡한 계산으로 넘겨야 한다면 CPU 과부하가 올 수 있습니다. 
- 코드가 복잡해진다

<br>
<br>

## 주의해야 할 점
Flyweight는 고유한 상태이므로 캐싱해서 사용하므로 항상 불변이여야 합니다.

<br>
<br>

## 예제
- [EX1-문제점](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/flyweight/EX1_Problem.kt)
  - 경량패턴을 적용하지 않았을 때 어떤 문제가 생기는지 알아봅니다.
- [Ex2-경량패턴](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/flyweight/EX2_Solution.kt)
  - 예제1의 문제를 경량패턴을 적용하여 해결하는 방안에 대해 알아봅니다.
- [Factory+Flyweight](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/flyweight/EX3_Factory%2BFlyweight.kt)
  - Factory Method + Flyweight이 조합된 예제에 대해 알아봅니다.    
