package org.example.builder.ex2okhttpclient

import java.io.IOException

/**
 * Call 객체가 Product를 추상화 한것이고 이를 구현하는 것으로 FakeCall을 생성했습니다.
 * 그럼 FakeCall 객체를 생성하는 컨테이너가 있어야 합니다.
 * 누가 그 역할을 할까요? -> ??
 */
class FakeCall(
    val client: OkHttpClient,
    val originalRequest: Request
) : Call, Cloneable {
    override fun request(): Request {
        return originalRequest
    }

    override fun execute(): Response {
        return getResponseWithInterceptorChain()
    }

    override fun enqueue(responseCallback: Callback) {
        responseCallback.onFailure(this, IOException())
        responseCallback.onResponse(this, getResponseWithInterceptorChain())
    }

    internal fun getResponseWithInterceptorChain(): Response {
        val interceptors = mutableListOf<String>()
        interceptors += client.interceptors
        interceptors += "RetryAndFollowUpInterceptor"
        interceptors += "BridgeInterceptor"
        interceptors += "CacheInterceptor"
        interceptors += "ConnectInterceptor"
        interceptors += "CallServerInterceptor"
        // 내부적으로 책임 연쇄 패턴을 사용하여 interceptors들이 차례대로 호출되며 response 반환하게 됩니다.
        // 인터셉터들을 타게되며 캐시설정, authenticator 호출, 실제 서버 호출 등 설정을 순차적으로 하게 됩니다.
        // Builder 패턴을 다루는 예제이니 바로 Response를 반환하도록 하겠습니다.
        val response = "response 응답"
        return Response.Builder()
            .code(200)
            .message("성공적으로 처리되었습니다")
            .body("OkHttp 구조를 참고하여 팩토리 + 빌더 패턴을 간단히 구현한 예제입니다.")
            .request(originalRequest)
            .build()
    }
}