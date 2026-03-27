package org.example.flyweight

import org.example.flyweight.ParticleType.*
import kotlin.random.Random

class ParticleIntrinsic(
    val color: Color,
    val bitmap: Bitmap
)

class GameParticle(
    var x: Double,
    var y: Double,
    var speed: Double,
    val type: ParticleType,
    val intrinsic: ParticleIntrinsic
)

object ParticleTypeFactory {
    private val cache = mutableMapOf<ParticleType, ParticleIntrinsic>()

    fun getInstance(type: ParticleType): ParticleIntrinsic = cache.getOrPut(type) {
        when (type) {
            BULLET -> ParticleIntrinsic(
                color = Color("#0xFFFFFF"),
                bitmap = Bitmap.create(32, 32)
            )

            MISSILE -> ParticleIntrinsic(
                color = Color("#0xFFF9F9"),
                bitmap = Bitmap.create(64, 64)
            )
        }
    }
}

fun main() {
    val particles = mutableListOf<GameParticle>()

    // 10000 개의 총알 생성
    repeat(10000) {
        particles.add(
            GameParticle(
                x = Random.nextDouble(),
                y = Random.nextDouble(),
                speed = 10.0,
                type = BULLET,
                intrinsic = ParticleTypeFactory.getInstance(BULLET)
            )
        )
    }

    // 10000 개의 미사 생성
    repeat(10000) {
        particles.add(
            GameParticle(
                x = Random.nextDouble(),
                y = Random.nextDouble(),
                speed = 20.0,
                type = MISSILE,
                intrinsic = ParticleTypeFactory.getInstance(MISSILE)
            )
        )
    }
    Memory.printUsage()
}