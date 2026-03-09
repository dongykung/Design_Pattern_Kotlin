# Singleton
시스템에 인스턴스를 하나만 만들기 위한 생성 패턴
- 시스템에 인스턴스가 하나만 존재한다
- 시스템의 모든 부분에서 인스턴스에 접근할 수 있다
<img width="687" height="423" alt="image" src="https://github.com/user-attachments/assets/62279316-6ec7-4a20-82b7-febbea87726f" />

<br>
<br>

## 언제 사용할가?
- 메모리 절약을 위해 하나의 인스턴스만 필요할 때 (ex: database 객체)
- 전역 변수들을 더 엄격하게 제어해야 할 때

안드로이드의 Room 공식문서에서도 Database 객체를 인스턴스화할 때 싱글톤 패턴을 권장
<img width="761" height="74" alt="image" src="https://github.com/user-attachments/assets/715ebf5e-2ab6-40b3-86a6-34f1b6aaad0c" />

<br>

## 싱글톤 생성 시 유의사항
- lazy한 인스턴스 생성
  - 프로그램이 시작되자마자 싱글톤 인스턴스가 생성되면 어떨가? 사용하지 않을 수도 있는데 미리 생성해버리면 생성 비용을 버리게 된다. 필요한 첫 순간에 이뤄져야 한다
- Thread Safe한 인스턴스 생성
  - 멀티 스레드 환경에서 여러개의 스레드가 동시에 인스턴스 객체 생성을 요청하게 되면 서로다른 인스턴스를 획득할 수 있다.
- 성능을 저해하지 않아야 한다
  - 많은 스레드가 동시에 싱글톤 객체를 생성하려고 할 때 스레드를 너무 오래 기다리게 하면 성능 정하로 이루어질 수 있다.   



<br>

## 싱글톤의 단점

### S.O.L.I.D 원칙을 위반한다
- 싱글톤 인스턴스가 1개만 생성하기 때문에 여러 책임을 지니게 되는 경우가 많습니다. -> 단일책임원칙(SRP) 위반
- 클래스들 간 결합도가 높아지게 된다. -> 개방-폐쇄 원칙(OCP) 위반
- 추상화에 의존하지 않고 싱글톤 클래스에 의존하게 된다 -> 의존성 역전 원칙(DIP) 위반

### 결합도와 의존성이 높아진다
- 클래스 사이에 결합도와 의존성이 높아진다
- 결합도와 의존성이 높다는 것 = 싱글톤 인스턴스의 코드가 변경되면 이를 참조하는 곳의 코드 또한 변경되어야 한다

### 테스트가 어렵다
- 보통 테스트 시 모의 객체를 사용하지만 싱글톤 객체 사용 시 매번 인스턴스의 상태를 초기화 시켜야 하며 독립적인 단위 테스트가 어렵다

<br>
<br>

## 예제

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
