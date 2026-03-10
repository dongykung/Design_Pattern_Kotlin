package org.example.abstractfactory

import org.example.singleton.StaticInnerClass

/**
 * 팩토리 클래스는 호출되면 객체를 생성하는 역할을 수행합니다.
 * 그렇기 때문에 메모리 최적화를 위해 각 팩토리 클래스마다 싱글톤을 적용하는 것이 좋습니다.
 */

interface ComputerComponent {
    fun render()
}

interface ComputerButton : ComputerComponent

interface ComputerTextField : ComputerComponent

class WindowButton : ComputerButton {
    override fun render() {
        println("WindowButton render")
    }
}

class MacButton : ComputerButton {
    override fun render() {
        println("MacButton render")
    }
}

class WindowTextField : ComputerTextField {
    override fun render() {
        println("WindowTextField render")
    }
}

class MacTextField : ComputerTextField {
    override fun render() {
        println("MacTextField render")
    }
}

interface ComputerComponentFactory {
    fun createButton() : ComputerButton
    fun createTextField() : ComputerTextField
}


// 싱글톤을 위해 생성자를 private 처리합니다.
class WindowFactory private constructor() : ComputerComponentFactory {

    init {
        println("init WindowFactory")
    }

    companion object {
        val instance: WindowFactory by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = WindowFactory()
    }

    override fun createButton(): ComputerButton {
        return WindowButton()
    }

    override fun createTextField(): ComputerTextField {
        return WindowTextField()
    }
}

// 싱글톤을 위해 생성자를 private 처리합니다.
class MacFactory private constructor() : ComputerComponentFactory {

    init {
        println("init MacFactory")
    }

    companion object {
        val instance: MacFactory by lazy { Holder.INSTANCE }
    }
    private object Holder {
        val INSTANCE = MacFactory()
    }

    override fun createButton(): ComputerButton {
        return MacButton()
    }

    override fun createTextField(): ComputerTextField {
        return MacTextField()
    }
}

fun main() {
    var factory: ComputerComponentFactory = WindowFactory.instance
    var button = factory.createButton()
    var textField = factory.createTextField()

    button.render() // Window Button이 렌더링 됩니다
    textField.render() // Window TextField가 렌더링 됩니다

    println("======== OS Change ========")

    factory = MacFactory.instance
    button = factory.createButton()
    textField = factory.createTextField()

    button.render() // Mac Button이 렌더링 됩니다
    textField.render() // Mac TextField가 렌더링 됩니다
}

/**
 * 만약 생성자를 전달하는 상황이 발생할 경우 Static Inner 방법 말고 Double Check Locking 방법을 사용할 수 있습니다.
 */

class WindowFactory2 private constructor(version: Int) : ComputerComponentFactory {

    @Volatile
    private var instance: WindowFactory2? = null

    fun getInstance(version: Int): WindowFactory2 {
        return instance ?: synchronized(this) {
            instance ?: WindowFactory2(version).also { instance = this }
        }
    }

    override fun createButton(): ComputerButton {
        return WindowButton()
    }

    override fun createTextField(): ComputerTextField {
        return WindowTextField()
    }
}