package org.example.singleton

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * - thread - safe 하다
 * - 지연 초기화가 가능하다
 */
class ThreadSafeSingleton private constructor() {

    init {
        println("ThreadSafeSingleton init")
    }

    companion object {
        private var instance: ThreadSafeSingleton? = null

        @Synchronized
        fun getInstance(): ThreadSafeSingleton {
            return instance ?: ThreadSafeSingleton().apply {
                instance = this
            }
        }

        fun print() = println("print")
    }
}

fun main() {
    ThreadSafeSingleton.print() // 지연초기화가 가능하다

    val singleton = arrayOfNulls<ThreadSafeSingleton>(10)
    val executorService = Executors.newFixedThreadPool(5)

    for (i in singleton.indices) {
        executorService.submit {
            singleton[i] = ThreadSafeSingleton.getInstance()
        }
    }
    executorService.shutdown()
    executorService.awaitTermination(2, TimeUnit.SECONDS)

    for (s in singleton) {
        println(s.toString())
    }
    // 이전 예제와 달리 init이 1번만 호출되며 모든 인스턴스의 값이 모두 같은 것을 확인할 수 있다
}