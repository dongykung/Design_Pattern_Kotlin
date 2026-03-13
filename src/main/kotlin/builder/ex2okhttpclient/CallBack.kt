package org.example.builder.ex2okhttpclient

import java.io.IOException

/**
 * OkHttpClient 에서 동기 / 비동기 메서드가 있습니다
 * 해당 인터페이스는 비동기 CallBack을 구현하기 위한 interface 입니다
 */
interface Callback {

    fun onFailure(
        call: Call,
        e: IOException,
    )

    @Throws(IOException::class)
    fun onResponse(
        call: Call,
        response: Response,
    )
}