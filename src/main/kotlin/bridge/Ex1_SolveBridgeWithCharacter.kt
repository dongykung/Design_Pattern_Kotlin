package org.example.bridge

// 이동방식
interface MovementStyle {
    fun move(name: String, x: Int, y: Int)
}

class Walk : MovementStyle {
    override fun move(name: String, x: Int, y: Int) = println("$name: ($x, $y)로 걸어감")
}

class Fly : MovementStyle {
    override fun move(name: String, x: Int, y: Int) = println("$name: ($x, $y)로 날아감")
}

class Teleport : MovementStyle {
    override fun move(name: String, x: Int, y: Int) = println("$name: ($x, $y)로 텔레포트함")
}

// 공격방식
interface AttackStyle {
    fun attack(name: String, x: Int, y: Int)
}

class Sword : AttackStyle {
    override fun attack(name: String, x: Int, y: Int) = println("$name: ($x, $y) 검으로 공격")
}

class Bow : AttackStyle {
    override fun attack(name: String, x: Int, y: Int) = println("$name: ($x, $y) 활로 공격")
}

class Magic : AttackStyle {
    override fun attack(name: String, x: Int, y: Int) = println("$name: ($x, $y) 마법으로 공격")
}

interface GameCharacter {
    val name: String
    fun move(x: Int, y: Int)
    fun attack(x: Int, y: Int)
}

class GameCharacterImpl(
    override val name: String,
    private val movementStyle: MovementStyle,
    private val attackStyle: AttackStyle
) : GameCharacter {
    override fun move(x: Int, y: Int) {
        movementStyle.move(name, x, y)
    }

    override fun attack(x: Int, y: Int) {
        attackStyle.attack(name, x, y)
    }
}

fun main() {
    val garen: GameCharacter = GameCharacterImpl("가렌", Walk(), Sword())
    garen.move(10, 20)
    garen.attack(10, 20)

    println()

    val queen: GameCharacter = GameCharacterImpl("퀸", Fly(), Bow())
    queen.move(50, 20)
    queen.attack(50, 20)
}