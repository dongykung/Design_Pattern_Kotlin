package org.example.bridge

interface Shape {
    fun draw()
}

// 빨강 원, 사각형
// 파랑 원, 사각형이 있다고 해보자

class RedCircle : Shape {
    override fun draw() = println("RedCircle")
}

class BlueCircle : Shape {
    override fun draw() = println("BlueCircle")
}

class RedRectangle : Shape {
    override fun draw() = println("RedRectangle")
}

class BlueRectangle : Shape {
    override fun draw() = println("BlueRectangle")
}

// 마름모가 추가되고 초로색이 추가된다면??
// 3* 3 = 9 총 5개의 Class가 더 생성되어야 함



