# Decorator 
- 객체에 대한 기능 변경이나 확장이 필요할 때, 상속 대신 객체의 결합(Composition)을 통해 동적으로 기능을 추가하는 구조 패턴
- 기존 객체의 코드를 수정하지 않고, 동일한 인터페이스를 구현하는 래퍼 객체로 감싸서 기능을 동적으로 확장하는 구조 패턴

## 구조
<img width="480" height="520" alt="decorator" src="https://github.com/user-attachments/assets/f11274ce-cf69-425b-b001-b8f46dedaadd" />

### Component
데코레이터와 원본 객체가 공유하는 공통 인터페이스 <br>
클라이언트는 이 인터페이스만 의존하기 때문에 원본인지, 데코레이터인지 구분할 필요가 없다
```kotlin
interface NewsRepository {
    suspend fun getNews(id: Long): News
    suspend fun getNewsList(): List<News>
    suspend fun saveNews(news: News)
}
```
<br>

### ConcreteComponent
실제 핵심 로직을 가진 원본 객체
```kotlin
class RemoteNewsRepository(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getNews(id: Long): News {
        return api.fetchNews(id)
    }

    override suspend fun getNewsList(): List<News> {
        return api.fetchNewsList()
    }

    override suspend fun saveNews(news: News) {
        api.postNews(news)
    }
}
```
<br>

### BaseDecorator
wrappee: Component 필드로 감싸는 대상을 들고 있고, 모든 메서드를 wrappee에 위임하는 기본 구현을 제공합니다
```kotlin
abstract class NewsRepositoryDecorator(
    private val repository: NewsRepository
) : NewsRepository {
    override suspend fun getNews(id: Long): News = repository.getNews(id)
    override suspend fun getNewsList(): List<News> = repository.getNewsList()
    override suspend fun saveNews(news: News) = repository.saveNews(news)
}
```
<br>

### ConcreteDecorators
BaseDecorator를 상속해서 전후로 부가 기능을 추가합니다
```kotlin
class LoggingNewsRepository(
    private val repository: NewsRepository
) : NewsRepositoryDecorator(repository) {
    override suspend fun getNews(id: Long): News {
        println("LoggingNewsRepository: getNews(id=$id) 호출")
        return repository.getNews(id).also {
            println("LoggingNewsRepository: getNews(id=$id) 완료: ${it.title}")
        }
    }
    // 나머지는 위임
}

class CachingNewsRepository(
    private val repository: NewsRepository
) : NewsRepositoryDecorator(repository) {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        return cache.getOrPut(id) { repository.getNews(id) }
    }
    // 나머지는 위임
}
```
<br>
<br>

## 상속의 문제점
서버로부터 뉴스 정보를 가져오는 역할을 하는 클래스가 있다고 가정해보겠습니다.
```kotlin
open class NewsRepository(val api: NewsApi) {
    open suspend fun getNews(id: Long): News = api.fetchNews(id)
    open suspend fun getNewsList(): List<News> = api.fetchNewsList()
    open suspend fun saveNews(news: News) = api.postNews(news)
}
```
이때 개발 팀장님으로부터 요청이 들어왔습니다.<br>

팀장님: "단일 뉴스를 가져올 때 마다 콘솔에 로그로 남겨주세요~" 

<br>

간단하지만 NewsRepository 코드를 수정하면 안된다는 제약 조건이 있다면 어떨가요? <br>
다른 사람들도 NewsRepository를 사용하고 있을 때 갑자기 로그 기록이 찍히게 된다면 `뭐지?` 싶을 겁니다. <br>
이러한 문제를 상속을 통해 해결할 수 있습니다.
```kotlin
class LoggingNewsRepository(api: NewsApi) : NewsRepository(api) {
    override suspend fun getNews(id: Long): News {
        print("LoggingNewsRepository getNews(id=$id) 호출")
        return super.getNews(id)
    }
}
```
이렇게 기존 코드를 수정하지 않고 로그를 기능을 추가했습니다. <br>

<br>

그런데 다음날 팀장님이 또 찾아와 "getNews 로 뉴스 불러오면 캐시 기능까지 추가해주세요~" 라는 요구사항을 받게되었습니다. <br>
이 또한 위와 같이 해결할 수 있습니다.
```kotlin
class CachingNewsRepository(api: NewsApi) : NewsRepository(api) {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        return cache.getOrPut(id) { super.getNews(id) }
    }
}
```
기존의 코드를 건드리지 않고 캐싱 기능을 수행하는 Repository를 생성하였습니다.

<br> <br>
이번엔 팀장님께서 "로그와 캐싱 둘 다 수행하게 해주세요~" 라고 하시면 어떻게 해야 할가요?
```kotlin
class LoggingCachingNewsRepository(api: NewsApi) : NewsRepository(api) {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        Log.d("NewsRepo", "getNews(id=$id) 호출")       // 로깅 로직 복붙
        return cache.getOrPut(id) { super.getNews(id) }  // 캐싱 로직 복붙
    }
}
```
휴.. 로그와 캐싱 기능을 모두 수행하는 Repository를 만들어 해결했습니다. <br>
계속 이런식으로 처리해야 할까요?
- 재시도 기능 추가해 주세요 -> 클래스 생성
- 로그도 찍고 재시도 기능도 같이 동작되게 해주세요 -> 클래스 생성
- 로그는 찍지말고 캐싱만할고 재시도 처리해주세요 -> 클래스 생성


이렇게 기능이 조합될수록 하나의 클래스에 여러 관심사의 코드가 섞이게 됩니다. <br>
즉, 상속으로 기능을 확장할 때의 근본적인 한계입니다.

<br>
<br>

## Decorator 등장
데코레이터 패턴의 목적은 객체에 새로운 동작을 동적으로 추가하는 것입니다. <br>
위 News 예제를 Decorator 패턴에 맞게 변형해보겠습니다.

```kotlin
interface NewsRepository {
    suspend fun getNews(id: Long): News
    suspend fun getNewsList(): List<News>
    suspend fun saveNews(news: News)
}

class RemoteNewsRepository(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getNews(id: Long): News {
        return api.fetchNews(id)
    }

    override suspend fun getNewsList(): List<News> {
        return api.fetchNewsList()
    }

    override suspend fun saveNews(news: News) {
        api.postNews(news)
    }
}
```
우선 Repository를 interface로 변경하고 기존과 동일한 로직을 RemoteNewsRepository로 구현하였습니다. 

<br>
<br>

Base Decorator 클래스에는 래핑된 객체를 참조하기 위한 필드가 있습니다(필드의 타입은 인터페이스 타입입니다).<br>
그 후 기초 데코레이터는 모든 작업들을 래핑된 객체에 위임합니다.
```kotlin
abstract class NewsRepositoryDecorator(
    private val repository: NewsRepository
) : NewsRepository {
    override suspend fun getNews(id: Long): News = repository.getNews(id)
    override suspend fun getNewsList(): List<News> = repository.getNewsList()
    override suspend fun saveNews(news: News) = repository.saveNews(news)
}
```
모든 작업들을 주입받은 repository 객체에 위임하고 있습니다.

<br>

이제 새로운 기능을 하는 Concrete Decorator들을 만들어보겠습니다.
```kotlin
// 로깅
class LoggingNewsRepository(
    private val repository: NewsRepository
) : NewsRepositoryDecorator(repository) {
    override suspend fun getNews(id: Long): News {
        println("LoggingNewsRepository: getNews(id=$id) 호출")
        return repository.getNews(id).also {
            println("LoggingNewsRepository: getNews(id=$id) 완료: ${it.title}")
        }
    }
    // 나머지는 위임
}

// 캐싱
class CachingNewsRepository(
    private val repository: NewsRepository
) : NewsRepositoryDecorator(repository) {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        return cache.getOrPut(id) { repository.getNews(id) }
    }
    // 나머지는 위임
}
```
로깅과 캐싱을 수행하는 Decorator를 만들어보았습니다. <br>
이제 아까처럼 로깅과 캐싱을 둘 다 수행해야 한다면 어떻게 해야 할까요? 

<br>

상속에서는 LoggingCachingNewsRepository라는 새로운 클래스를 만들어야 했지만, 데코레이터에서는 기존 클래스를 조합하기만 하면 됩니다.

```kotlin
fun main() = runBlocking<Unit> {
    val api: NewsApi = FakeNewsApi()

    val repository = LoggingNewsRepository(
        CachingNewsRepository(
            RemoteNewsRepository(api)
        )
    )

    repository.getNews(1)

    println("====================")

    repository.getNews(1)
}
```
새로운 클래스를 만들지 않고도, 런타임에 원하는 기능을 자유롭게 조합할 수 있습니다.<br>
이것이 상속 대신 객체의 결합을 통해 기능을 확장하는 데코레이터 패턴의 핵심입니다

<br>
<br>

## Decorator 패턴을 구현할 때 필요 요소
- 데코레이션(새로운 동작)을 추가할 대상 객체를 입력으로 받는다.
- 대상 객체에 대한 참조를 계속 유지한다.
- 데코레이터 클래스의 메서드가 호출되면 들고 있는 객체의 동작을 변경할지 / 또는 처리를 위임할지 결정한다
- 대상 객체에서 인터페이스를 추출하거나 해당 클래스가 이미 구현하고 있는 인터페이스를 사용한다

<br>
<br>

## 언제 사용하는가
- 객체를 사용하는 코드를 훼손하지 않으면서 런타임에 객체에 추가 동작을 할당할 수 있어야 하는 경우(기존 객체의 코드를 수정하지 않고 기능을 추가하거나 변경해야 할 때)
- 상속을 사용하여 객체의 행동을 확장하는 것이 어색하거나 불가능할 때
- 객체 책임과 행동이 동적으로 상황에 따라 다양한 기능이 빈번하게 추가/삭제되는 경우

<br>
<br>

## 장점
- 상속(자식 클래스)을 사용하지 않고 기능을 확장할 수 있다
- 런타임에 객체들에서부터 책임들을 추가하거나 제거할 수 있다
- 객체를 여러 데코레이터로 래핑하여 여러 행동들을 합성할 수 있습니다.
- 각 데코레이터 클래스마다 고유의 책임을 가지므로 SRP 준수
- 새로운 기능이 추가되어야 해도 기존 객체를 수정하지 않아도 되므로 OCP 준수
- 추상화에 의존하므로 DIP 준수

<br>
<br>

## 단점
- 데코레이터의 행동이 데코레이터 스택 내의 순서에 의존하지 않는 방식으로 데코레이터를 구현하기가 어렵다(순서 중요)
- 래퍼들의 스택에서 특정 래퍼를 제거하기가 어렵다
- 데코레이터의 객체의 속을 볼 수 없다(속에 들어 있는 객체가 무엇인지 알 수 업다)
<br>
<br>

## Kotlin에서의 BaseDecorator
Kotlin에서는 by 위임을 통해 BaseDecorator를 생략할 수 있습니다.
```kotlin
class LoggingNewsRepositoryBy(
    private val repository: NewsRepository
) : NewsRepository by repository {
    override suspend fun getNews(id: Long): News {
        println("LoggingNewsRepository: getNews(id=$id) 호출")
        return repository.getNews(id).also {
            println("LoggingNewsRepository: getNews(id=$id) 완료: ${it.title}")
        }
    }
    // 나머지는 위임
}

class CachingNewsRepositoryBy(
    private val repository: NewsRepository
) : NewsRepository by repository {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        return cache.getOrPut(id) { repository.getNews(id) }
    }
    // 나머지는 위임
}
```

<br>
<br>

## 예제
- [Ex1-inheritance](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/decorator/Ex1_inheritance.kt)
    - 기능을 변경 확장할 때 상속의 한계점에 대해 알아봅니다
 - [Ex2-Decorator](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/decorator/Ex2_Decorator.kt)
     - 데코레이터 패턴에 대해 알아봅니다.
     - 뉴스를 가져오는 Repository에서 로깅 기능 + 캐싱 기능을 추가하는 단계에 대해 알아봅니다 

