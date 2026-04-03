package org.example.proxy

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