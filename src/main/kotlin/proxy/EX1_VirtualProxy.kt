package org.example.proxy

interface Image {
    fun display()
}

class HighResImage(
    private val path: String
) : Image {
    init {
        println("$path 로딩 중 (3초 소요)")
    }
    override fun display() {
        println("$path 표시")
    }
}

class ImageProxy(
    private val path: String
): Image {
    private val real by lazy { HighResImage(path) }

    override fun display() {
        real.display()
    }
}

fun main() {
    val img = ImageProxy("kotlin.jpg")

    println("지연 초기화 전")
    img.display()
}