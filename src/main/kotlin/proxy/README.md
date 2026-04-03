# Proxy
프록시는 원본 객체를 대신하여 클라이언트의 요청을 처리하는 객체이다.
즉, 클라이언트는 실제 객체에 직접 접근하는 것이 아니라 프록시(대리자)를 통해 간접적으로 접근하게 된다.

<img width="536" height="177" alt="image" src="https://github.com/user-attachments/assets/49886ba1-3b22-4cdf-bddd-3bef5680a080" />


왜 클라이언트가 객체를 직접 사용하게 두지 않고 대리자를 사용하게 하는걸까? 

- 대상 클래스가 민감한 정보를 가지고 있을 때
- 인스턴스화 하기 무거워 지연 초기화를 해야할 때(해당 클래스에서 제공 안할 때)
- 추가 기능을 넣고 싶은데 원본 클래스를 수정할 수 없는 상황일 때


위와 같이 대리자를 통해 해결할 수 있다.

## 구조
<img width="740" height="740" alt="proxy" src="https://github.com/user-attachments/assets/bf2aa2f7-827b-45aa-961f-b521a2a32451" />

### ServiceInterface
Proxy를 서비스 객체 타입으로 가지게 해주는 인터페이스
```kotlin
interface Database {
    fun query(sql: String): String
}
```

<br>
<br>

### Service
실제 서비스
```kotlin
// Service
class RealDatabase : Database {
    override fun query(sql: String): String {
        println("실제 DB 쿼리 실행 $sql")
        return "$sql 결과"
    }
}

```

<br>
<br>

### Proxy
실제 서비스를 대신 수행할 대리자
```kotlin
class DatabaseProxy(
    private val realService: RealDatabase
) : Database {

    private fun checkAccess(): Boolean {
        println("Access 확인중")
        return true
    }

    override fun query(sql: String): String {
        if (checkAccess()) {
            return realService.query(sql)
        }
        throw SecurityException("엑세스 거부")
    }
}
```

<br>
<br>

### Client
클라이언트는 같은 인터페이스를 통해 프록시 객체를 생성하여 사용
```kotlin
// Client
class Client(private val db: Database) {
    fun doWork() {
        val result = db.query("SELECT * FROM  Kotlin")
        print(result)
    }
}
```

<br>
<br>

## 프록시의 종류
Proxy 패턴은 활용 방법이 매우 다양하다. 같은 프록시 객체라도 로직을 어떻게 구성하느냐에 따라 역할이 달라질 수 있다.
예를 들어, 접근 제어를 위한 보호 프록시, 객체 생성을 지연시키는 가상 프록시, 요청과 응답을 기록하는 로깅 프록시 등으로 활용할 수 있다.

### 가상 프록시
지연 초기화(by lazy)를 사용하는 방식으로 원본 객체에서 이를 지원하지 않아 무거운 객체를 실제로 필요할 때 생성하기 위해 사용합니다.
```kotlin
interface Image {
    fun display()
}

class HighResImage(
    private val path: String // 가상 프록시는 생성에 필요한 정보를 생성자로 받음
) : Image {
    init {
        println("$path 로딩 중 (3초 소요)")
    }
    override fun display() {
        println("$path 표시")
    }
}

class ImageProxy(
    private val path: String
): Image {
    private val real by lazy { HighResImage(path) }

    override fun display() {
        real.display()
    }
}
```

<br>
<br>

### 보호 프록시
권한에 따라 실제 객체 접근을 제한합니다.(접근 권한) <br>
특정 클라이언트만 서비스 객체를 사용할 수 있도록 하는 경우
```kotlin
interface Document {
    fun read(): String
    fun write(content: String)
}

class RealDocument(
    private var content: String
) : Document {
    override fun read(): String {
        return content
    }

    override fun write(content: String) {
        this.content = content
    }
}

interface AccessPolicy {
    fun canRead(): Boolean
    fun canWrite(): Boolean
}

class AdminPolicy : AccessPolicy {
    override fun canRead() = true
    override fun canWrite(): Boolean = true
}

class ViewerPolicy : AccessPolicy {
    override fun canRead() = true
    override fun canWrite() = false
}

class ProtectedDocument(
    private val real: RealDocument,
    private val policy: AccessPolicy
) : Document {
    override fun read(): String {
        if (!policy.canRead()) throw SecurityException("읽기 권한 없음")
        return real.read()
    }

    override fun write(content: String) {
        if (!policy.canWrite()) throw SecurityException("쓰기 권한 없음")
        real.write(content)
    }
}
```

<br>
<br>

### 로깅 프록시
실제 객체 호출 전후로 로그를 남깁니다.
```kotlin
interface Api {
    fun fetch(url: String): String
}

class RealApi : Api {
    override fun fetch(url: String): String {
        return "서버 데이터"
    }
}

class LoggingApi(
    private val real: Api
) : Api {
    override fun fetch(url: String): String {
        println("[${System.currentTimeMillis()}] 요청: $url")
        val result = real.fetch(url)
        println("[${System.currentTimeMillis()}] 응답: ${result.toString()}")
        return result
    }
}
```

<br>
<br>

### 캐싱 프록시
동일한 요청에 대해 캐시된 결과를 반환합니다.
```kotlin
interface WeatherService {
    fun getWeather(city: String): String
}

class RealWeatherService : WeatherService {
    override fun getWeather(city: String): String {
        println("API 호출 중")
        return "$city 추움"
    }
}

class CachingWeatherService(
    private val real: WeatherService
): WeatherService {
    private val cache = mutableMapOf<String, String>()

    override fun getWeather(city: String): String {
        return cache.getOrPut(city) {
            real.getWeather(city)
        }
    }
}
```

<br>
<br>

### 원격 프록시
원격 서버의 객체를 로컬에 있는 것처럼 사용합니다. <br>
- 즉, 프록시 클래스는 로컬에 있고, 대상 객체는 원격 서버에 존재하는 경우
- 프록시 객체는 네트워크를 통해 클라이언트의 요청을 전달하여 네트워크와 관련된 불필요한 작업들을 처리하고 결과값만 반환
- 클라 입장에서 프록시를 통해 객체를 이용하기 때문에 원격이든 로컬이든 신경 쓸 필요가 없고 프록시가 진짜 객체와 통신을 대리한다.

<br>
<br>

## 언제 사용하는가
- 가상 프록시: 무거운 객체를 지연 초기화를 해야 할 때
- 보호 프록시: 접근 제어를 통해 특정 클라이언트만 서비스 객체를 사용하도록 하고 싶을 때
- 원격 프록시: 서비스 객체가 원격 서버에 있는 경우
- 로깅 프록시: 서비스 객체에 대한 로그를 유지하려 할 때
- 캐싱 프록시: 클라이언트의 요청들의 결과를 캐시하고 수명주기를 관리하려 할 때

<br>
<br>

## 장점
- 클라이언트가 알지 못하는 상태에서 서비스 객체를 제어가 가능하다
- 클라이언트들는 객체를 신경쓰지 않고 서비스 객체의 수명 주기를 관리할 수 있다
- 프록시는 서비스 객체가 준비되지 않았거나 사용할 수 없는 경우에도 작동한다
- 기존 서비스 코드를 수정하지 않고 프록시를 통해 새 기능을 도입할 수 있다 -> OCP 준수

<br>
<br>

## 단점
- 새로운 클래스들을 도입해야 하기 때문에 코드가 복잡해질 수 있다
- 서비스의 응답이 늦어질 수 있다

<br>
<br>

## 다른 패턴과의 관계

### 퍼사드 vs 프로시
퍼사드는 복잡한 객체, 시스템을 보호하고 자체적으로 초기화하지만 프록시는 자신의
서비스 객체와 같은 인터페이스를 가지므로 이들은 서로 상호 교환이 가능합니다.

### 데코레이터 vs 프록시
로깅, 캐싱 프록시를 보면 데코레이터와 정말 유사하고(그냥 같다) 실제로 정말 헷갈린다. 두 패턴 모두 한 객체가 일부 작업을 다른 객체에 위임해야 하는 합성 원칙을 기반으로 한다.


이 두 패턴의 차이점은 프로시는 일반적으로 자체적으로 자신의 서비스 객체의 수명 주기를 관리하는 반면
데코레이터의 합성은 항상 클라이언트에 의해 제어된다.

프록시는 내부에서 알아서 관리된다(클라가 모름)
```kotlin
val db: Database = DatabaseProxy() 
// 내부에서 로깅되는지, 캐싱되고있는지 모름 지연초기화되는지도 모름
```

데코레이터는 클라이언트가 합성을 제어한다.
```kotlin
val db = LoggingDecorator(
    CachingDecorator(
        RealDatabase()
    )
)
// 어떤 기능을 어떤 순서로 확장할지 클라가 알고 직접 결정
```
즉, 클라이언트가 기 기능의 존재를 알아야 하면 데코레이터, 몰라도 되면 프록시라고 볼 수 있습니다.

<br>
<br>

## 예제
- [기본 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX0_Basic.kt)
- [가상 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX1_VirtualProxy.kt)
- [보호 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX2_ProtectionProxy.kt)
- [로깅 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX3_LoggingProxy.kt)
- [캐싱 프록시](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/proxy/EX4_CachingProxy.kt)

