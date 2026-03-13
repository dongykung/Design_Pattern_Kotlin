package org.example.builder.ex2okhttpclient

import java.io.IOException

fun main() {
    // OkHttpClient 객체를 생성합니다 (안드로이드에서는 hilt로 싱글톤으로 주로 생성합니다)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor("loggingInterceptor")
        .addInterceptor("tokenInterceptor")
        .callTimeout(5)
        .writeTimeout(5)
        .build()

    // request 객체를 생성합니다.
    val request = Request.Builder()
        .url("https://github.com/dongykung/Design_Pattern_Kotlin")
        .method("GET")
        .body("데이터 주세요")
        .build()

    // 이제 네트워크 요청을 해보겠습니다.
    // execute로 호출했으니 동기 입니다.
    // use 를 통해 body를 null로 처리합니다.
    println("=========execute=========")
    okHttpClient.newCall(request).execute().use { response ->
        println("${response.body}")
    }

    println("\n=========enqueue=========")
    okHttpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("onFailure called")
        }
        override fun onResponse(
            call: Call,
            response: Response
        ) {
            println("onResponse: ${response.body}")
        }
    })

}