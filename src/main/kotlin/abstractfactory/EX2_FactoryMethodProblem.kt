package org.example.abstractfactory

/**
 * Factory Method로 구현할 시 어떤 문제점이 생기는지 알아봅니다,
 */

interface ImageComponent {
    fun render()
}

interface Image : ImageComponent

interface ImageButton : ImageComponent

class AndroidImage : Image {
    override fun render() {
        println("AndroidImage render")
    }
}

class IOSImage : Image {
    override fun render() {
        println("IOSImage render")
    }
}

class AndroidImageButton : ImageButton {
    override fun render() {
        println("AndroidImageButton render")
    }
}

class IOSImageButton : ImageButton {
    override fun render() {
        println("IOSImageButton render")
    }
}

enum class MobileOSType {
    ANDROID, IOS
}

// 생성자
interface ImageComponentFactoryMethod {
    fun createOperation(type: MobileOSType): ImageComponent
    fun createComponent(type: MobileOSType): ImageComponent
}

/**
 * Factory Method는 Abstract Factory와 달리 하나의 팩토리가 한 종류의 객체만 생성할 수 있습니다.
 */
class ImageFactory : ImageComponentFactoryMethod {
    override fun createOperation(type: MobileOSType): ImageComponent {
        val image: ImageComponent = createComponent(type)
        // image에 대한 추가 설정(ex: bitmap, 크기 지정 등)
        return image
    }

    override fun createComponent(type: MobileOSType): ImageComponent {
        return when (type) {
            MobileOSType.ANDROID -> AndroidImage()
            MobileOSType.IOS -> IOSImage()
            // 만약 새로운 OSType이 생긴다면? 해당 when 문을 수정해야 하므로 OCP 위반
        }
    }
}

/**
 * Factory Method는 Abstract Factory와 달리 하나의 팩토리가 한 종류의 객체만 생성할 수 있습니다.
 */
class ImageButtonFactory : ImageComponentFactoryMethod {
    override fun createOperation(type: MobileOSType): ImageComponent {
        val imageButton = createComponent(type)
        // imageButton에 대한 추가 설정(ex: 활성화, 크기 등)
        return imageButton
    }

    override fun createComponent(type: MobileOSType): ImageComponent {
        return when (type) {
            MobileOSType.ANDROID -> AndroidImageButton()
            MobileOSType.IOS -> IOSImageButton()
            // 만약 새로운 OSType이 생긴다면? 해당 when 문을 수정해야 하므로 OCP 위반
        }
    }
}

fun main() {
    var mobileOS: MobileOSType = MobileOSType.ANDROID
    val imageFactory: ImageComponentFactoryMethod = ImageFactory()

    var image = imageFactory.createOperation(mobileOS)
    image.render() // Android 이미지가 렌더링 됩니다

    val imageButtonFactory: ImageComponentFactoryMethod = ImageButtonFactory()
    var imageButton =  imageButtonFactory.createOperation(mobileOS)
    imageButton.render() // Android 이미지 버튼이 렌러딩 됩니다

    println("======== OS Change ========")

    // 모바일 운영체제가 변경되었습니다.
    mobileOS = MobileOSType.IOS
    image = imageFactory.createOperation(mobileOS)
    imageButton = imageButtonFactory.createOperation(mobileOS)
    image.render() // IOS 이미지가 렌더링 됩니다
    imageButton.render() // IOS 이미지 버튼이 렌더링 됩니다.
}

/**
 * 추상 팩토리가 팩토리 메서드보다 무조건 좋은 건 아닙니다.
 * 추상 팩토리는 제품 군을 묶어 생성해야 할 때 유지보수와 확장성에 있어 더 유리하다라는 것을 기억하자
 */