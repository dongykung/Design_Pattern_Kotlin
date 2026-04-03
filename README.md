# Kotlin - Design Pattern

## 생성 패턴

### [Singleton](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/singleton/README.md)
- [Object를 사용한 싱글톤](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/singleton/Ex1_Object.kt)
- [EagerInitialization](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/singleton/Ex2_EagerInitialization.kt)  
- [LazyInitialization](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/singleton/Ex3_LazyInitialization.kt)
  - 싱글톤 객체를 지연 초기화하게 생성하는 방법을 알아봅니다
- [ThreadSafeSingleton](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/singleton/Ex4_ThreadSafeSingleton.kt)
  - thread-safe하게 싱글톤 객체를 초기화하는 방법을 알알봅니다
- [DoubleCheckedLocking](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/singleton/Ex5_DoubleCheckedLocking.kt)
  - Double-Checked-Locking에 대해 알아봅니다
- [StaticInnerClass](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/singleton/Ex6_StaticInnerClass.kt)
  - Kotlin에서 StaticInner 방법을 통한 싱글톤 생성 방법을 알아봅니다
- [Enum Class](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/singleton/Ex7_Enum.kt)
  - Enum Class를 통해 싱글톤을 생성하는 방법을 알아봅니다.
 
  <br>
  <br>

### [Factory Method](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/factorymethod/README.md)
- [SimpleFactoryMethod](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/EX1_SimpleFactoryMethod.kt)
    - SimpleFactoryMethod 예제를 알아봅니다 
- [FactoryMethodBasic](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/EX2_FactoryMethodBasic.kt)
    - 기본적인 Factory method 예제를 알아봅니다 
- [FactoryMethodReuse](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/Ex3_FactoryMethod_Reuse.kt)
    - FactoryMethod를 통해 생성된 객체들을 재사용하는 풀 예제에 대해 알아봅니다
- [Android - ViewModelProvider.Factory](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/factorymethod/ex4viewmodelfactory)
    - Android 개발에서 Factory Method 패턴이 ViewModel에서 어떻게 사용되는지 알아봅니다   

<br>
<br>

### [Abstract Factory](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/abstractfactory/README.md)
- [Basic-AbstractFactory](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/abstractfactory/EX1_AbstractFactoryBasic.kt)
    - Abstract-Factory 기본 구조에 대해 알아봅니다 
- [FactoryMethodProblem](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/abstractfactory/EX2_FactoryMethodProblem.kt) 
    - Factory Method로 이러한 상황을 구현할 시 어떤 문제점이 생기는지 알아봅니다
- [AbstractFactory+Singleton](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/abstractfactory/EX4_AbstractFactory_Singleton.kt)
    - AbstractFactory와 Singleton을 조합하여 사용하는 방법에 대해 알아봅니다
- [AbstractFactory + Factory Method](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/abstractfactory/EX5_AbstractFactory%2BFactoryMethod.kt)
    - AbstractFactory + Factory-Method를 동시에 사용하는 예제에 대해 알아봅니다
 
<br>
<br>

### [Builder](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/builder/README.md)
- [빌더 구조 구현](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX1_BuilderBasic.kt)
    - 위 구조 그림에서 보았던 구조를 직접 구현해 봅니다.
- [OkHttp](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX1_BuilderBasic.kt)
    - OkHttp 구조를 간소화 하여 빌더 + 팩토리 예제에 대해 알아봅니다.
- [Kotlin에서의 Builder 패턴?](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX3_NamedArgument.kt)
    - Kotlin에서 지원하는 기본 인수, named argument로 쉽게 객체 생성을 하는 방법에 대해 알아봅니다.
 
<br>
<br>

### [Prototype](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/prototype/README.md)
- [깊은 복사를 통해 객체를 복사](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/prototype/EX1_DeepCopy.kt)
    - 깊은 복사를 통해 객체를 복사하여 객체를 생성하는 방법에 대해 알아봅니다,
- [얕은 복사를 통한 객체 복사](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/prototype/Ex2_ShallowCopy.kt)     
    - 얕으 복사를 통해 객체를 복사하고 주의해야 할 점에 대해 알아봅니다.
 
<br>
<br>

---

## 구조 패턴

### [Decorator](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/decorator/README.md)
- [Ex1-inheritance](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/decorator/Ex1_inheritance.kt)
    - 기능을 변경 확장할 때 상속의 한계점에 대해 알아봅니다
 - [Ex2-Decorator](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/decorator/Ex2_Decorator.kt)
     - 데코레이터 패턴에 대해 알아봅니다.
     - 뉴스를 가져오는 Repository에서 로깅 기능 + 캐싱 기능을 추가하는 단계에 대해 알아봅니다
  
<br>
<br>

### [Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/README.md)
- [Object-Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX1_ObjectAdapter.kt)
  - Object-Adapter 패턴에 대해 알아봅니다
- [Class-Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX2_ClassAdapter.kt)
  - Class-Adpater 패턴에 대해 알아봅니다
- [Extension-Fuction](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX3_ExtensionFunction.kt)
  - 확장 함수를 사용하여 Adapter Class를 생략하는 방법에 대해 알아봅니다
 

<br>
<br>

### [Bridge](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/README.md)
- [게임 캐릭터 예제 - 문제점](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex1_ProblemWithCharacter.kt)
  - 게임 캐릭터를 예시로 Bridge 패턴 적용 이전에 어떤 문제가 있는지 알아봅니다
- [게임 캐릭터 예제 - 브리지 적용](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex1_SolveBridgeWithCharacter.kt)
  - 예제 1에서 Bridge 패턴 적용 후 어떤 변화가 생기는지에 대해 알아봅니다
- [도형 예제 - 문제점](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex2_ProblemWithShape.kt)
  - Shape과 Color를 예시로 Bridge 패턴 적용 이전에 어떤 문제가 있는지 알아봅니다
- [도형 예제 - 브리지 적용](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/bridge/Ex2_SolveBridgeWithShape.kt)
  - 예제 2에서 Bridge 패턴 적용 후 어떤 변화가 생기는지에 대해 알아봅니다
 
<br>
<br>

### [Facade](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/facade/README.md)
- [Facade - Basic](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/facade/EX1_BasicFacade.kt)
    - VideoPlay를 예시로한 Facade 패턴을 알아봅니다
- [재귀적 퍼사드](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/facade/Ex2_RecursionFacade.kt)
    - 예제1에에서 비디오 변환 기능을 확장하여 재귀적 퍼사드에 대해 알아봅니다
 
<br>
<br>

### [Flyweight](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/flyweight/README.md)
- [EX1-문제점](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/flyweight/EX1_Problem.kt)
  - 경량패턴을 적용하지 않았을 때 어떤 문제가 생기는지 알아봅니다.
- [Ex2-경량패턴](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/flyweight/EX2_Solution.kt)
  - 예제1의 문제를 경량패턴을 적용하여 해결하는 방안에 대해 알아봅니다.
- [Factory+Flyweight](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/flyweight/EX3_Factory%2BFlyweight.kt)
  - Factory Method + Flyweight이 조합된 예제에 대해 알아봅니다.
 
  <br>
  <br>

  ### [Proxy](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/README.md)
- [기본 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX0_Basic.kt)
- [가상 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX1_VirtualProxy.kt)
- [보호 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX2_ProtectionProxy.kt)
- [로깅 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX3_LoggingProxy.kt)
- [캐싱 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX4_CachingProxy.kt)
