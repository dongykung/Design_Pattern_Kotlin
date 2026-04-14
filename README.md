# Kotlin - Design Pattern

> GoF 디자인 패턴을 Kotlin으로 구현하며 학습한 내용을 정리합니다.

참고자료
- [Kotlin Design Patterns](https://product.kyobobook.co.kr/detail/S000208494266)
- [refactoring.guru](https://refactoring.guru/ko)
---

## 🏗️ 생성 패턴 (Creational)
 
### Singleton
> 인스턴스가 오직 하나만 존재하도록 보장하는 패턴
 
| 예제 | 설명 |
|------|------|
| [Object 싱글톤](src/main/kotlin/singleton/Ex1_Object.kt) | `object` 키워드를 사용한 Kotlin 기본 싱글톤 |
| [Eager Initialization](src/main/kotlin/singleton/Ex2_EagerInitialization.kt) | 클래스 로딩 시점에 즉시 초기화 |
| [Lazy Initialization](src/main/kotlin/singleton/Ex3_LazyInitialization.kt) | 싱글톤 객체를 지연 초기화하는 방법 |
| [Thread-Safe](src/main/kotlin/singleton/Ex4_ThreadSafeSingleton.kt) | thread-safe하게 싱글톤을 초기화하는 방법 |
| [Double-Checked Locking](src/main/kotlin/singleton/Ex5_DoubleCheckedLocking.kt) | DCL을 통한 성능 최적화 |
| [Static Inner Class](src/main/kotlin/singleton/Ex6_StaticInnerClass.kt) | Companion object + inner class 방식 |
| [Enum Class](src/main/kotlin/singleton/Ex7_Enum.kt) | Enum을 활용한 싱글톤 생성 |
 
📖 [README →](src/main/kotlin/singleton/README.md)
 
---
 
### Factory Method
> 객체 생성을 서브클래스에 위임하여 생성 로직을 분리하는 패턴
 
| 예제 | 설명 |
|------|------|
| [Simple Factory Method](src/main/kotlin/factorymethod/EX1_SimpleFactoryMethod.kt) | Simple Factory Method 기본 예제 |
| [Factory Method Basic](src/main/kotlin/factorymethod/EX2_FactoryMethodBasic.kt) | GoF Factory Method 기본 구조 |
| [Factory Method Reuse](src/main/kotlin/factorymethod/Ex3_FactoryMethod_Reuse.kt) | 생성된 객체를 재사용하는 풀 예제 |
| [ViewModelProvider.Factory](src/main/kotlin/factorymethod/ex4viewmodelfactory) | Android ViewModel에서의 활용 |
 
📖 [README →](src/main/kotlin/factorymethod/README.md)
 
---
 
### Abstract Factory
> 관련된 객체들의 군(family)을 생성하는 인터페이스를 제공하는 패턴
 
| 예제 | 설명 |
|------|------|
| [Basic](src/main/kotlin/abstractfactory/EX1_AbstractFactoryBasic.kt) | Abstract Factory 기본 구조 |
| [Factory Method의 한계](src/main/kotlin/abstractfactory/EX2_FactoryMethodProblem.kt) | Factory Method로 구현 시 문제점 |
| [+ Singleton](src/main/kotlin/abstractfactory/EX4_AbstractFactory_Singleton.kt) | Abstract Factory + Singleton 조합 |
| [+ Factory Method](src/main/kotlin/abstractfactory/EX5_AbstractFactory%2BFactoryMethod.kt) | Abstract Factory + Factory Method 조합 |
 
📖 [README →](src/main/kotlin/abstractfactory/README.md)
 
---
 
### Builder
> 복잡한 객체의 생성 과정을 단계별로 분리하는 패턴
 
| 예제 | 설명 |
|------|------|
| [Builder Basic](src/main/kotlin/builder/EX1_BuilderBasic.kt) | 빌더 패턴 기본 구조 구현 |
| [OkHttp](src/main/kotlin/builder/EX1_BuilderBasic.kt) | OkHttp 구조를 간소화한 빌더 + 팩토리 예제 |
| [Named Argument](src/main/kotlin/builder/EX3_NamedArgument.kt) | Kotlin 기본 인수 + named argument 활용 |
 
📖 [README →](src/main/kotlin/builder/README.md)
 
---
 
### Prototype
> 기존 객체를 복사하여 새로운 객체를 생성하는 패턴
 
| 예제 | 설명 |
|------|------|
| [Deep Copy](src/main/kotlin/prototype/EX1_DeepCopy.kt) | 깊은 복사를 통한 객체 복제 |
| [Shallow Copy](src/main/kotlin/prototype/Ex2_ShallowCopy.kt) | 얕은 복사의 동작과 주의점 |
 
📖 [README →](src/main/kotlin/prototype/README.md)
 
---
 
## 🧩 구조 패턴 (Structural)
 
### Decorator
> 객체에 동적으로 새로운 기능을 추가하는 패턴
 
| 예제 | 설명 |
|------|------|
| [상속의 한계](src/main/kotlin/decorator/Ex1_inheritance.kt) | 기능 확장 시 상속이 가진 문제점 |
| [Decorator](src/main/kotlin/decorator/Ex2_Decorator.kt) | NewsRepository에 로깅 + 캐싱 기능 추가 |
 
📖 [README →](src/main/kotlin/decorator/README.md)
 
---
 
### Adapter
> 호환되지 않는 인터페이스를 연결해주는 패턴
 
| 예제 | 설명 |
|------|------|
| [Object Adapter](src/main/kotlin/adapter/EX1_ObjectAdapter.kt) | 컴포지션 기반 어댑터 |
| [Class Adapter](src/main/kotlin/adapter/EX2_ClassAdapter.kt) | 상속 기반 어댑터 |
| [Extension Function](src/main/kotlin/adapter/EX3_ExtensionFunction.kt) | 확장 함수로 Adapter Class 생략 |
 
📖 [README →](src/main/kotlin/adapter/README.md)
 
---
 
### Bridge
> 추상화와 구현을 분리하여 독립적으로 확장하는 패턴
 
| 예제 | 설명 |
|------|------|
| [캐릭터 - 문제점](src/main/kotlin/bridge/Ex1_ProblemWithCharacter.kt) | Bridge 적용 전 클래스 폭발 문제 |
| [캐릭터 - 브리지 적용](src/main/kotlin/bridge/Ex1_SolveBridgeWithCharacter.kt) | Bridge 적용 후 구조 개선 |
| [도형 - 문제점](src/main/kotlin/bridge/Ex2_ProblemWithShape.kt) | Shape × Color 조합 문제 |
| [도형 - 브리지 적용](src/main/kotlin/bridge/Ex2_SolveBridgeWithShape.kt) | Bridge로 M×N → M+N 해결 |
 
📖 [README →](src/main/kotlin/bridge/README.md)
 
---
 
### Facade
> 복잡한 하위 시스템에 대한 단순화된 인터페이스를 제공하는 패턴
 
| 예제 | 설명 |
|------|------|
| [Facade Basic](src/main/kotlin/facade/EX1_BasicFacade.kt) | VideoPlay를 예시로 한 퍼사드 패턴 |
| [재귀적 퍼사드](src/main/kotlin/facade/Ex2_RecursionFacade.kt) | 비디오 변환 기능 확장 + 재귀적 퍼사드 |
 
📖 [README →](src/main/kotlin/facade/README.md)
 
---
 
### Flyweight
> 공유를 통해 대량의 객체를 효율적으로 관리하는 패턴
 
| 예제 | 설명 |
|------|------|
| [문제점](src/main/kotlin/flyweight/EX1_Problem.kt) | 경량패턴 미적용 시 메모리 문제 |
| [경량패턴 적용](src/main/kotlin/flyweight/EX2_Solution.kt) | intrinsic state 공유로 해결 |
| [Factory + Flyweight](src/main/kotlin/flyweight/EX3_Factory%2BFlyweight.kt) | Factory Method와 조합 |
 
📖 [README →](src/main/kotlin/flyweight/README.md)
 
---
 
### Proxy
> 다른 객체에 대한 접근을 제어하는 대리 객체를 제공하는 패턴
 
| 예제 | 설명 |
|------|------|
| [기본 프록시](src/main/kotlin/proxy/EX0_Basic.kt) | 프록시 패턴 기본 구조 |
| [가상 프록시](src/main/kotlin/proxy/EX1_VirtualProxy.kt) | 지연 초기화를 통한 리소스 절약 |
| [보호 프록시](src/main/kotlin/proxy/EX2_ProtectionProxy.kt) | 권한에 따른 접근 제어 |
| [로깅 프록시](src/main/kotlin/proxy/EX3_LoggingProxy.kt) | 요청 전후 로그 기록 |
| [캐싱 프록시](src/main/kotlin/proxy/EX4_CachingProxy.kt) | 동일 요청 결과 캐싱 |
 
📖 [README →](src/main/kotlin/proxy/README.md)

---

## 🏗️ 동작 패턴

 
### Strategy
> 실행 중(런타임)에 알고리즘(행위)을 선택하여 객체의 동작을 런타임에 변경할 수 있게하는 행동 패턴
 
| 예제 | 설명 |
|------|------|
| [기본 전략 패턴](src/main/kotlin/strategy/EX1_BasicStrategy.kt) | 기본 전략 패턴 예제 |
| [전략 + 브릿지](src/main/kotlin/strategy/EX2_Strategy+Bridge.kt) | 전략 패턴+브릿지 패턴 |
 
📖 [README →](src/main/kotlin/strategy/README.md)

---

### State
> 객체의 내부 상태가 바뀔 때, 객체의 행동이 함께 바뀌도록 하는 패턴
 
| 예제 | 설명 |
|------|------|
| [상태패턴]([src/main/kotlin/strategy/EX1_BasicStrategy.kt](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/state/EX1_VideoPlayer.kt)) | VideoPlayer State |
 
📖 [README →]([src/main/kotlin/strategy/README.md](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/state/README.md))
