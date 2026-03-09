package org.example.singleton

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * - thread - safe 하지 않다
 * - 지연 초기화가 가능하다
 */
class LazyInitialization private constructor() {

    init {
        println("LazyInitialization init")
    }

    companion object {
        private var instance: LazyInitialization? = null

        fun getInstance(): LazyInitialization {
            return instance ?: LazyInitialization().apply {
                instance = this
            }
        }

        fun print() = println("print")
    }
}

fun main() {
    // 클래스 객체가 더 이상 먼저 초기화되지 않습니다.
    LazyInitialization.print()

    val singleton = arrayOfNulls<LazyInitialization>(10)
    val executorService = Executors.newFixedThreadPool(5)

    for (i in singleton.indices) {
        executorService.submit {
            singleton[i] = LazyInitialization.getInstance()
        }
    }
    executorService.shutdown()
    executorService.awaitTermination(2, TimeUnit.SECONDS)

    for (s in singleton) {
        println(s.toString())
    }
    // 모든 LazyInitialization의 값이 동일하지 않은 것을 확인 할 수 있습니다 -> thread safe 하지 않다
    // init 또한 1번만 호출되지 않음
}