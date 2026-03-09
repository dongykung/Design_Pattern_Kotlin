package org.example.singleton

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * - thread - safe 하다 (JVM에 의해 초기화가 보장된다)
 * - 값의 고유성을 보장하여 리플렉션으로부터 안전하다
 * - 대신 lazy loading이 불가능하다
 */
enum class EnumSingleton {
    INSTANCE;

    private val values = mutableListOf<Int>()

    init {
        println("EnumSingleton init")
        values.add(1)
        values.add(2)
    }
}

fun main() {

    val singleton = arrayOfNulls<EnumSingleton>(10)
    val executorService = Executors.newFixedThreadPool(5)

    for (i in singleton.indices) {
        executorService.submit {
            singleton[i] = EnumSingleton.INSTANCE
        }
    }
    executorService.shutdown()
    executorService.awaitTermination(2, TimeUnit.SECONDS)

    for (s in singleton) {
        println(s.toString())
    }
    // init은 한 번 호출된다
}