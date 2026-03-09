package org.example.singleton

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * - thread - safe 하다 -> Java1.4 이하 버전을 사용하는 경우 thread-safe 하지 않을 수 있다.
 * - 매번 Synchronized를 걸어야 하나? 에서 비롯됨
 * https://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
 */
class DoubleCheckedLocking private constructor(private val initialCapacity: Int) {

    init {
        println("DoubleCheckedLocking init")
    }

    companion object {
        // CPU 캐시를 사용하지 않고 메인 메모리로부터 값을 읽고 쓰게 한다 하지만 Java1.4 이하 버전을 사용하는 volatile을 사용하더라도 thread-safe 하지 않을 수 있다.
        @Volatile
        private var instance: DoubleCheckedLocking? = null

        fun getInstance(capacity: Int = 100): DoubleCheckedLocking {
            return instance ?: synchronized(this) {
                instance ?: DoubleCheckedLocking(capacity).also { instance = it }
            }
        }

        fun print() = println("print")
    }
}

fun main() {
    DoubleCheckedLocking.print() // 지연초기화가 가능하다

    val singleton = arrayOfNulls<DoubleCheckedLocking>(10)
    val executorService = Executors.newFixedThreadPool(5)

    for (i in singleton.indices) {
        executorService.submit {
            singleton[i] = DoubleCheckedLocking.getInstance()
        }
    }
    executorService.shutdown()
    executorService.awaitTermination(2, TimeUnit.SECONDS)

    for (s in singleton) {
        println(s.toString())
    }
    // 마찬가지로 init이 한번만 호출되며 모든 인스턴스의 값이 모두 같다
}