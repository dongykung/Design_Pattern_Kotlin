# Builder 패턴
복잡한 객체들을 단계별로 생성할 수 있도록 하는 생성 패턴

빌더 패턴은 자신의 클래스에서 객체 생성 코드를 추출하여 builders 라는 별도의 객체들로 이동하도록 제안합니다.
<img width="920" height="1160" alt="structure-2x (1)" src="https://github.com/user-attachments/assets/a55ef54f-20af-4b15-a283-3d8b46f1e0b1" />

위 구조에 대한 코드 구현은 [Ex1_BuilderBasic](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX1_BuilderBasic.kt)에서 확인하실 수 있습니다.


별도의 Builder를 클래스를 생성하고 순차적으로 값을 입력받고 build 함수를 통해 인스턴스를 반환하는 패턴으로 간단한 구조를 살펴보겠습니다.

```kotlin
class Request constructor(
    builder: Builder
) {
    val url: String = builder.url
    val method: String = builder.method
    val body: String? = builder.body
    val headers: String = builder.headers

    // newBuilder를 통해 새로운 Request를 재조립하여 생성할 수 있따
    fun newBuilder(): Builder = Builder(this)

    class Builder {
        // 빌더의 멤버 변수는 가변이여야 합니다.
        internal var url: String = ""
        internal var method: String
        internal var headers: String = ""
        internal var body: String? = null

        constructor() {
            this.method = "GET"
            this.headers = "headers"
        }

        internal constructor(request: Request) {
            this.url = request.url
            this.method = request.method
            this.body = request.body
            this.headers = request.headers
        }

        fun url(url: String) = apply {
            this.url = url
        }

        fun method(method: String) = apply {
            this.method = method
        }

        fun headers(headers: String) = apply {
            this.headers = headers
        }

        fun body(body: String?) = apply {
            this.body = body
        }

        fun build(): Request = Request(this)
    }
}
```
이처럼 빌더 패턴은 복잡한 객체를 보다 쉽게 만들 수 있습니다.
하지만 Kotlin에서는 생성자와 함수의 매개변수에 기본값을 설정할 수 있으며 named argument 기능을 제공하여 손쉽게 객체 생성이 가능합니다.

```kotlin
data class Mail(
    var title: String = "",
    var message: String = "",
    var to: List<String> = emptyList()
)

fun main() {
    val mail = Mail(message = "message", to = listOf("me"))
}
```
위 코드처럼 객체를 빌더 패턴을 사용하지 않고도 손쉽게 생성할 수 있기 때문에 Kotlin에서는 Builder 패턴을 특별한 상황이 아니라면 사용할 일이 없다고 생각합니다.

<br>

## 그럼 언제사용하는가
- 메서드 체이닝으로 하나씩 쌓아가는 패턴은을 표현하고 싶을 때,  사람마다 취향이 다르겠지만 어떤 속성을 누적시킬 때 빌더 패턴이 더 깔끔해 보일 수 있다고 생각합니다.
```kotlin
val client = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor)
    .addInterceptor(authInterceptor)
    .build()
==============================================================
val client = OkHttpClient(
    interceptors = listOf(loggingInterceptor, authInterceptor),
    connectTimeout = Duration.ofSeconds(10)
)
```
- 불변 객체를 만들지만, 구성 과정은 가변적이어야 할 때
- build() 시점에 검증/변환이 필요할 때 - 하지만 init 코드로도 가능함

<br>

## 단점
- 해당 객체에 대한 빌더 클래스를 만들어야 하기 때문에 코드의 복잡성이 올라갑니다.


<br>

## 예제
- [빌더 구조 구현](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX1_BuilderBasic.kt)
    - 위 구조 그림에서 보았던 구조를 직접 구현해 봅니다.
- [OkHttp](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX1_BuilderBasic.kt)
    - OkHttp 구조를 간소화 하여 빌더 + 팩토리 예제에 대해 알아봅니다.
- [Kotlin에서의 Builder 패턴?](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/builder/EX3_NamedArgument.kt)
    - Kotlin에서 지원하는 기본 인수, named argument로 쉽게 객체 생성을 하는 방법에 대해 알아봅니다.     
