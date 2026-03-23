package org.example.bridge

interface BShape {
    fun draw()
}

class Circle(
    private val color: Color
) : BShape {
    override fun draw() {
        println("draw Circle")
        color.setColor()
    }
}

class Rectangle(
    private val color: Color
) : BShape {
    override fun draw() {
        println("draw Rectangle")
        color.setColor()
    }
}

class Triangle(
    private val color: Color
) : BShape {
    override fun draw() {
        println("draw Triangle")
        color.setColor()
    }
}

interface Color {
    fun setColor()
}

class Red : Color {
    override fun setColor() = println("Set Red")
}

class Blue : Color {
    override fun setColor() = println("Set Blue")
}

class Green : Color {
    override fun setColor() = println("Set Green")
}

/**
 * 원래는 3*3 총 9개의 Class를 생성하여 했지만
 * Color를 별도의 인터페이스로 분리하고 Shape가 이를 조합(Composition)하여 사용한다
 *
 * 적용 전: 모양 3종 * 색상 3종 = 총 9개의 클래스
 * 적용 후 모양 3종 + 색 3종 = 6개의 클래스로 동일한 9가지 조합 가능
 */

