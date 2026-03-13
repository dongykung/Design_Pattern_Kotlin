package org.example.builder.ex2okhttpclient

// 이전에 팩토리 패턴을 학습했으니 빌더에 적용까지 해보겠습니다.
// Call이 추상화한 객체고 Call Factory가 최상의 공장 클래스(인터페이스)겠죠?
interface Call : Cloneable {

    fun request(): Request

    fun execute(): Response

    fun enqueue(responseCallback: Callback)

    fun interface Factory {
        fun newCall(request: Request): Call
    }
}