package org.example.singleton

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * - object를 사용하기 때문에 thread-safe 보장이 된다
 * - lazy 키워드를 통해 지연 초기화
 * - 생성자에 파라미터가 필요한경우 전달할 수 없다
 */
class StaticInnerClass private constructor() {

    init {
        println("StaticInnerClass init")
    }

    companion object {
        // lazy 키워드으 구현체인 SynchronizedLazyImpl도 내부적으로 Double-Check 방식을 사용한다
        val instance: StaticInnerClass by lazy { Holder.INSTANCE }
        fun print() = println("print")
    }

    private object Holder {
        val INSTANCE = StaticInnerClass()
    }
}

fun main() {
    StaticInnerClass.print() // 지연초기화가 가능하다

    val singleton = arrayOfNulls<StaticInnerClass>(10)
    val executorService = Executors.newFixedThreadPool(5)

    for (i in singleton.indices) {
        executorService.submit {
            singleton[i] = StaticInnerClass.instance
        }
    }
    executorService.shutdown()
    executorService.awaitTermination(2, TimeUnit.SECONDS)

    for (s in singleton) {
        println(s.toString())
    }
    // init이 한번만 호출되며 각 인스턴스의 값 또한 모두 같다
}