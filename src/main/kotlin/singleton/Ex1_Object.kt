package org.example.singleton

/**
 * The initialization of an object declaration is thread-safe and done on first access.
 * [Object declarations and expressions](https://kotlinlang.org/docs/object-declarations.html#object-declarations-overview)
 * - thread safe 하며 최초 접근 시에 초기화 됨
 */
object Ex1_Singleton {

    private val initializedAt = System.currentTimeMillis()

    init {
        println("Ex1_Singleton init: $initializedAt")
    }

    const val NAME = "Bob"

    fun doWork() {
        println("doWork - Ex1_Singleton init: $initializedAt")
    }

}

fun main() {
    // const의 경우 inline 되어 초기화가 수행되지 않습니다.
    println(Ex1_Singleton.NAME)
    Ex1_Singleton.doWork()
}