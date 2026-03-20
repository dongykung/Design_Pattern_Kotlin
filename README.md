# Kotlin - Design Pattern

## 생성 패턴

### [Singleton](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/singleton)
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

### [Factory Method](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/factorymethod)
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

### [Abstract Factory](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/abstractfactory)
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

### [Builder](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/builder)
- [빌더 구조 구현](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX1_BuilderBasic.kt)
    - 위 구조 그림에서 보았던 구조를 직접 구현해 봅니다.
- [OkHttp](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX1_BuilderBasic.kt)
    - OkHttp 구조를 간소화 하여 빌더 + 팩토리 예제에 대해 알아봅니다.
- [Kotlin에서의 Builder 패턴?](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX3_NamedArgument.kt)
    - Kotlin에서 지원하는 기본 인수, named argument로 쉽게 객체 생성을 하는 방법에 대해 알아봅니다.
 
<br>
<br>

### [Prototype](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/prototype)
- [깊은 복사를 통해 객체를 복사](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/prototype/EX1_DeepCopy.kt)
    - 깊은 복사를 통해 객체를 복사하여 객체를 생성하는 방법에 대해 알아봅니다,
- [얕은 복사를 통한 객체 복사](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/prototype/Ex2_ShallowCopy.kt)     
    - 얕으 복사를 통해 객체를 복사하고 주의해야 할 점에 대해 알아봅니다.
 
<br>
<br>

---

## 구조 패턴

### [Decorator](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/decorator)
- [Ex1-inheritance](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/decorator/Ex1_inheritance.kt)
    - 기능을 변경 확장할 때 상속의 한계점에 대해 알아봅니다
 - [Ex2-Decorator](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/decorator/Ex2_Decorator.kt)
     - 데코레이터 패턴에 대해 알아봅니다.
     - 뉴스를 가져오는 Repository에서 로깅 기능 + 캐싱 기능을 추가하는 단계에 대해 알아봅니다
  
<br>
<br>

### [Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/tree/master/src/main/kotlin/adapter)
- [Object-Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX1_ObjectAdapter.kt)
  - Object-Adapter 패턴에 대해 알아봅니다
- [Class-Adapter](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX2_ClassAdapter.kt)
  - Class-Adpater 패턴에 대해 알아봅니다
- [Extension-Fuction](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/adapter/EX3_ExtensionFunction.kt)
  - 확장 함수를 사용하여 Adapter Class를 생략하는 방법에 대해 알아봅니다 
