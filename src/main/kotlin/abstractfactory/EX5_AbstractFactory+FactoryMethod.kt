package org.example.abstractfactory


/**
 * Abstract Factory와 Factory Method가 혼합된 예제에 대해 알아봅니다.
 * 최상위 공장 클래스에 객체 생성에 대한 전처리 / 후처리를 위한 함수를 작성합니다.
 */
interface ComputerComponentFactory2 {
    fun createButton() : ComputerButton
    fun createButtonOperation() : ComputerButton
    fun createTextField() : ComputerTextField
    fun createTextFieldOperation() : ComputerTextField
}

class WindowFactory3 : ComputerComponentFactory2 {
    override fun createButton(): ComputerButton {
        return WindowButton()
    }

    override fun createButtonOperation(): ComputerButton {
        val windowButton = createButton()
        // WindowButton에 대한 전처리 / 후처리
        return windowButton
    }

    override fun createTextField(): ComputerTextField {
        return WindowTextField()
    }

    override fun createTextFieldOperation(): ComputerTextField {
        val windowTextField = createTextField()
        // WindowTextField 대한 전처리 / 후처리
        return windowTextField
    }
}

class MacFactory3 : ComputerComponentFactory2 {
    override fun createButton(): ComputerButton {
        return MacButton()
    }

    override fun createButtonOperation(): ComputerButton {
        val macButton = createButton()
        // WindowButton에 대한 전처리 / 후처리
        return macButton
    }

    override fun createTextField(): ComputerTextField {
        return MacTextField()
    }

    override fun createTextFieldOperation(): ComputerTextField {
        val maxTextField = createTextField()
        // WindowTextField 대한 전처리 / 후처리
        return maxTextField
    }
}

fun main() {
    var factory: ComputerComponentFactory2 = WindowFactory3()
    var button = factory.createButtonOperation()
    var textField = factory.createTextFieldOperation()
    button.render() // WindowButton이 렌더링 됩니다
    textField.render() // WindowTextField가 렌더링 됩니다

    println("======== OS Change ========")
    factory = MacFactory3()

    button = factory.createButtonOperation()
    textField = factory.createTextFieldOperation()

    button.render() // MacButton이 렌더링 됩니다
    textField.render() // MacTextField가 렌더링 됩니다
}