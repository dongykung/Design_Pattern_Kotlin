package org.example.builder.ex2okhttpclient

import java.io.Closeable


/**
 * Response 객체
 * 네트워크 요청으로 인한 응답 객체
 * 객체를 단계별로 쉽게 생성하기 위해 빌더 패턴이 적용되었습니다
 */
class Response(
    builder: Builder
) : Closeable {

    val request: Request? = builder.request
    val code: Int = builder.code
    val message: String? = builder.message
    val headers: String = builder.headers
    var body: String? = builder.body

    // newBuilder를 통해 새로운 Request를 재조립하여 생성할 수 있따
    fun newBuilder(): Builder = Builder(this)

    // 예제이므로 ResponseBody 대신 body를 가변으로 만들고 null 처리하게 하였습니다
    override fun close() {
        body = null
    }

    class Builder {
        // 빌더의 멤버 변수는 가변이여야 합니다.
        internal var request: Request? = null
        internal var code: Int = -1
        internal var message: String? = null
        internal var headers: String = "Response Header"
        internal var body: String? = "empty"

        constructor() {
            headers = "response headers"
        }

        internal constructor(response: Response) {
            this.request = response.request
            this.code = response.code
            this.message = response.message
            this.headers = response.headers
            this.body = response.body
        }

        fun request(request: Request) = apply {
            this.request = request
        }

        fun code(code: Int) = apply {
            this.code = code
        }

        fun message(msg: String) = apply {
            this.message = msg
        }

        fun headers(headers: String) = apply {
            this.headers = headers
        }

        fun body(body: String) = apply {
            this.body = body
        }

        // build 함수를 통해 Response 객체를 생성한다
        fun build(): Response = Response(this)
    }
}