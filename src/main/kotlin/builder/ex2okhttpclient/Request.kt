package org.example.builder.ex2okhttpclient

/**
 * Request 객체 - 연결을 요청하는데 필요한 객체
 * 객체를 단계별로 생성할 수 있게 빌더 패턴이 적용되었습니다.
 */
class Request constructor(
    builder: Builder
) {
    val url: String = builder.url
    val method: String = builder.method
    val body: String? = builder.body
    val headers: String = builder.headers

    /**
     * 꼭 빌더패턴을 통해 객체를 생성하지 않고 내부 생성자를 통해서도 가능하다
     * DEFAULT 값을 두어 꼭 필요한 url만 전달하여 Request 객체를 생성할 수도 있다(내부적으로 빌더를 통해 객체 생성)
     * ex: Request(url = "MyUrl")
     */
    constructor(
        url: String,
        headers: String = "headers",
        method: String = "GET",
        body: String? = null,
    ) : this(
        Builder()
            .url(url)
            .headers(headers)
            .method(method)
            .body(body)
    )

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