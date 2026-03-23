package org.example.bridge

interface Character {
    val name: String
    fun move(x: Int, y: Int)
    fun attack(x: Int, y: Int)
}

// 이동 방식: Walk, Fly, Teleport
// 공격 방식: Sword, Bow, Magic

// Walk * Sword
class WalkingSwordman(override val name: String): Character {
    override fun move(x: Int, y: Int) = println("$name: ($x, $y)로 걸어감")
    override fun attack(x: Int, y: Int) = println("$name: ($x, $y) 검으로 공격")
}

// Walk * Bow
class WalkingArcher(override val name: String): Character {
    override fun move(x: Int, y: Int) = println("$name: ($x, $y)로 걸어감")
    override fun attack(x: Int, y: Int) = println("$name: ($x, $y) 활로 공격")
}

// Walk * Magic
class WalkingMage(override val name: String): Character {
    override fun move(x: Int, y: Int) = println("$name: ($x, $y)로 걸어감")
    override fun attack(x: Int, y: Int) = println("$name: ($x, $y) 마법으로 공격")
}

// Fly * Sword
class FlySwordman(override val name: String): Character {
    override fun move(x: Int, y: Int) = println("$name: ($x, $y)로 날아감")
    override fun attack(x: Int, y: Int) = println("$name: ($x, $y) 검으로 공격")
}

// Fly * Bow
class FlyArcher(override val name: String): Character {
    override fun move(x: Int, y: Int) = println("$name: ($x, $y)로 날아감")
    override fun attack(x: Int, y: Int) = println("$name: ($x, $y) 활로 공격")
}

// ...
// 3 * 3 = 9개의 클래스가 필요함 (이동 로직 3개, 공격 로직 3개)
// 기획자가 무기가 2개가 더 추가되었어요 -> 3* 5 = 15 즉 6개의 클래스를 더 만들어야함

fun main() {
    val warrior = WalkingSwordman("가렌")
    warrior.move(10, 20)
    warrior.attack(10, 20)
    println()

    val flyingArcher = FlyArcher("퀸")
    flyingArcher.move(50, 100)
    flyingArcher.attack(50, 100)

    // 이동방식과 무기가 점점 추가되면 클래스들 또한 기하급수적으로 많아지게 된다.
}