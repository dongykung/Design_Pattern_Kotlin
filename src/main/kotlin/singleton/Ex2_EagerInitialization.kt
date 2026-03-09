package org.example.singleton

/**
 * - 바로 초기화하여 사용
 * - Class 로딩 시 초기화
 * - thread - safe 하다
 */
class EagerInitialization private constructor() {
    init {
        println("EagerInitialization init")
    }
    companion object {
        private val instance: EagerInitialization = EagerInitialization()

        fun getInstance(): EagerInitialization = instance

        fun print() = println("print")
    }
}

fun main() {
    // 아직 객체는 필요없고 print 함수만 사용하고 싶었는데 EagerInitialization 클래스가 초기화 되어버림
    // 만약 EagerInitialization 클래스가 무거운 객체였다면 -> 리소스 낭비
    EagerInitialization.print()
}