package org.example.abstractfactory

interface Component {
    fun render()
}

// Abstract ProductA
interface Button : Component

// Abstract ProductB
interface CheckBox : Component

// Concrete ProductA1
class AndroidButton : Button {
    override fun render() {
        println("AndroidButton Render")
    }
}

// Concrete ProductA2
class IOSButton : Button {
    override fun render() {
        println("IOSButton Render")
    }
}

// Concrete ProductB1
class AndroidCheckBox : CheckBox {
    override fun render() {
        println("AndroidCheckBox Render")
    }
}

// Concrete ProductB2
class IOSCheckBox : CheckBox {
    override fun render() {
        println("IOSCheckBox Render")
    }
}

// Abstract Factory
interface MobileComponentFactory {
    fun createButton(): Button
    fun createCheckBox(): CheckBox
}

// ConcreteFactory1
class AndroidComponentFactory : MobileComponentFactory {
    override fun createButton(): Button {
        return AndroidButton()
    }

    override fun createCheckBox(): CheckBox {
        return AndroidCheckBox()
    }
}

// ConcreteFactory2
class IOSComponentFactory : MobileComponentFactory {
    override fun createButton(): Button {
        return IOSButton()
    }

    override fun createCheckBox(): CheckBox {
        return IOSCheckBox()
    }
}

enum class OSType {
    ANDROID, IOS
}

fun main() {
    var os: OSType = OSType.ANDROID

    var componentFactory: MobileComponentFactory = when (os) {
        OSType.ANDROID -> AndroidComponentFactory()
        OSType.IOS -> IOSComponentFactory()
    }

    var button: Button = componentFactory.createButton()
    var checkBox: CheckBox = componentFactory.createCheckBox()

    button.render() // Android 이미지가 렌더링 됩니다.
    checkBox.render() // Android CheckBox가 렌러딩 됩니다.

    println("======== OS Change ========")

    os = OSType.IOS
    componentFactory = when (os) {
        OSType.ANDROID -> AndroidComponentFactory()
        OSType.IOS -> IOSComponentFactory()
    }

    button = componentFactory.createButton()
    checkBox = componentFactory.createCheckBox()

    button.render() // IOS 버튼이 렌더링 됩니다.
    checkBox.render() // IOS CheckBox가 렌더링 됩니다.
}