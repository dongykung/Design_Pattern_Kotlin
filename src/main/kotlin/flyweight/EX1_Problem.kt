package org.example.flyweight

import kotlin.random.Random

enum class ParticleType {
    BULLET, MISSILE,
}

object Memory {
    var usedKB = 0

    fun allocate(kb: Int) {
        usedKB += kb
    }

    fun printUsage() = println("메모리 사용: ${usedKB}KB (${usedKB / 1024}MB)")
}


class Particle(
    var x: Double,
    var y: Double,
    var speed: Double,
    val image: Bitmap,
    val color: Color,
    val type: ParticleType
)


class Bitmap private constructor(
    val width: Int,
    val height: Int,
    val pixels: IntArray
) {
    val sizeInKB: Int = (pixels.size * 4) / 1024

    companion object {
        fun create(width: Int, height: Int): Bitmap {
            val bitmap = Bitmap(width, height, IntArray(width * height))
            Memory.allocate(bitmap.sizeInKB)
            println("Bitmap 생성: ${width}x${height} (${bitmap.sizeInKB}KB)")
            return bitmap
        }
    }
}

class Color(
    val code: String
)

fun main() {

    val particles = mutableListOf<Particle>()
    // 10000개의 총알과 10000개의 미사일을 만든다고 가정

    repeat(10000) {
        particles.add(
            Particle(
                x = Random.nextDouble(),
                y = Random.nextDouble(),
                speed = 10.0,
                image = Bitmap.create(32, 32),
                color = Color("#0xFFFFFF"),
                type = ParticleType.BULLET
            )
        )
    }

    repeat(10000) {
        particles.add(
            Particle(
                x = Random.nextDouble(),
                y = Random.nextDouble(),
                speed = 20.0,
                image = Bitmap.create(64, 64),
                color = Color("#0xFFF9F9"),
                type = ParticleType.MISSILE
            )
        )
    }

    // 메모리 사용량은?
    Memory.printUsage()
}