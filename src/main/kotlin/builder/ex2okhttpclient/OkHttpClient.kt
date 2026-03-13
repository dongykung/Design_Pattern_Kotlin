package org.example.builder.ex2okhttpclient

/**
 * Call Product 추상화를 하였고 추상화의 구현 객체를 생성하는 팩토리가 필요합니다.
 * 그게 바로 OkHttpClient 입니다.
 * 여기서는 FakeCall 객체를 생성하는 팩토리 클래스의 역할을 수행합니다.
 * 팩토리 객체를 단계별로 설정하기 위해 빌더패턴까지 적용된 예제입니다.
 */
open class OkHttpClient(
    private val builder: Builder
) : Call.Factory {
    val interceptors: MutableList<String> = builder.interceptors
    val followRedirects: Boolean = builder.followRedirects
    val followSslRedirects: Boolean = builder.followSslRedirects
    val callTimeout: Int = builder.callTimeout
    val connectTimeout: Int = builder.connectTimeout
    val readTimeout: Int = builder.readTimeout
    val writeTimeout: Int = builder.writeTimeout
    val cacheSize: Long = builder.cacheSize

    constructor() : this(Builder())

    // newBuilder를 통해 새로운 OkHttpClient 객체를 재조립하여 생성할 수 있다
    fun newBuilder(): Builder = Builder(this)

    override fun newCall(request: Request): Call {
        return FakeCall(this, request)
    }

    class Builder() {
        // 빌더의 멤버 변수는 가변이여야 합니다.
        internal var interceptors: MutableList<String> = mutableListOf()
        internal var followRedirects = true
        internal var followSslRedirects = true
        internal var callTimeout = 0
        internal var connectTimeout = 10_000
        internal var readTimeout = 10_000
        internal var writeTimeout = 10_000
        internal var cacheSize: Long = 1024L

        internal constructor(okHttpClient: OkHttpClient) : this() {
            this.interceptors = okHttpClient.interceptors
            this.followRedirects = okHttpClient.followRedirects
            this.followSslRedirects = okHttpClient.followSslRedirects
            this.callTimeout = okHttpClient.callTimeout
            this.connectTimeout = okHttpClient.connectTimeout
            this.readTimeout = okHttpClient.readTimeout
            this.writeTimeout = okHttpClient.writeTimeout
            this.cacheSize = okHttpClient.cacheSize
        }

        fun addInterceptor(interceptor: String) = apply {
            this.interceptors += interceptor
        }

        fun followRedirects(redirects: Boolean) = apply {
            followRedirects = redirects
        }

        fun followSslRedirects(redirects: Boolean) = apply {
            followSslRedirects = redirects
        }

        fun callTimeout(duration: Int) = apply {
            callTimeout = duration
        }

        fun connectTimeout(timeout: Int) = apply {
            connectTimeout = timeout
        }

        fun readTimeout(timeout: Int) = apply {
            readTimeout = timeout
        }

        fun writeTimeout(timeout: Int) = apply {
            writeTimeout = timeout
        }

        fun cacheSize(size: Long) = apply {
            cacheSize = size
        }

        fun build(): OkHttpClient = OkHttpClient(this)
    }
}

fun main() {
    val okHttp = OkHttpClient.Builder()
        .callTimeout(20)
        .writeTimeout(2)
        .build()
    println(okHttp.callTimeout) // 20
    val newOkHttp = okHttp.newBuilder()
        .callTimeout(40)
        .build()
    println(newOkHttp.callTimeout) // newBuilder를 통해 생성하므로 40
    println(okHttp.callTimeout) // 20
}