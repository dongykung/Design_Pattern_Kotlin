# Facade Pattern
라이브러리에 대한, 프레임워크에 대한 또는 다른 클래스들의 복잡한 집합에 대한 단순화된 인터페이스를 제공하는 패턴

서브시스템의 인터페이스 집합에 대한 통합된 상위 수준의 인터페이스를 제공한다

<br>
<img width="980" height="380" alt="facade" src="https://github.com/user-attachments/assets/edd173bf-c7ef-4267-8d14-ca285187e06a" /> <br>
우리가 고객센터에 전화를 걸면 상담사분이 우리의 요구사항을 듣고 쉽게 처리해주는 상황을 예로 들 수 있습니다. <br>

<br>

위 그림처럼 창고, 포장, 배달, 결제 처리 등 이들이 모두 클래스들이라고 해보겠습니다. <br>
클래스 객체를 모두 초기화 후 로직을 호출하는 순서 또한 알고 있어야 합니다. <br>
하지만 상담사가 있으니 우리는 이러한 내부 사정을 신경쓰지 않고 손쉽게 요구사항을 요청합니다.

<br>

이처럼 퍼사드 패턴은 복잡한 서브시스템을 캡슐화 하여 클라이언트(사용자)가 내부의 복잡한 로직을 알 필요 없이 단순하고 통합된 인터페이스를 통해 시스템을 쉽게 사용할 수 있도록 합니다.

<br>
<br>

## 구조
<img width="1120" height="760" alt="facades" src="https://github.com/user-attachments/assets/688fd3df-32df-44ae-90c9-9d2783f26301" /> 

<br>
<br>

### SubSystem class
라이브러리, 또는 다른 클래스들
```kotlin
// SubClass
class VideoDecoder {
    fun decode(fileName: String): ByteArray {
        println("$fileName 비디오 디코딩 중...")
        return byteArrayOf()
    }
}

// SubClass
class AudioDecoder {
    fun decode(fileName: String): ByteArray {
        println("$fileName 음성 디코딩 중...")
        return byteArrayOf()
    }
}

// SubClass
class SubtitleParser {
    fun parse(fileName: String): String {
        println("$fileName subtitles 파싱 중...")
        return "파싱된 $fileName "
    }
}

// SubClass
class VideoRenderer {
    fun render(video: ByteArray, audio: ByteArray, subtitles: String) {
        println("$subtitles 영상 및 음성 렌더링 중")
    }
}
```

<br>
<br>

### Facade
서브시스템을 캡슐화하여 클라이언트가 손쉽게 사용할 수 있도록 하는 인터페이스를 제공한다
```kotlin
// Facade
class MediaPlayerFacade(
    private val videoDecoder: VideoDecoder = VideoDecoder(),
    private val audioDecoder: AudioDecoder = AudioDecoder(),
    private val subtitleParser: SubtitleParser = SubtitleParser(),
    private val renderer: VideoRenderer = VideoRenderer()
) {
    fun play(fileName: String) {
        val video = videoDecoder.decode(fileName)
        val audio = audioDecoder.decode(fileName)
        val subtitles = subtitleParser.parse(fileName)
        renderer.render(video, audio, subtitles)
    }
}
```
만약 MediaPlayerFacade가 없다면 클라이언트가 직접 비디오, 음성, Parser 서브 클래스들을 선언하고 사용해야 한다. <br>
하지만 위 Facade를 통해 우리는 아래와 같이 손쉽게 사용할 수 있다
```kotlin
fun main() {
    val mediaPlayer = MediaPlayerFacade()
    mediaPlayer.play("농구 영상")
}
```
Facade가 없다면?
```kotlin
    // facade가 없다면?
    val videoDecoder = VideoDecoder()
    val audioDecoder = AudioDecoder()
    val subtitleParser = SubtitleParser()
    val videoRenderer = VideoRenderer()
    val fileName = "농구 하이라이트"
    videoRenderer.render(videoDecoder.decode(fileName), audioDecoder.decode(fileName), subtitleParser.parse(fileName)
```

<br>
<br>

### Additinal Facade
퍼사드는 꼭 1개이란 법은 없다. <br>
퍼사드가 또다른 퍼사드를 가지게 하는 재귀적 퍼사드도 가능하다.

<br>

Media를 플레이하는데 새로운 format도 convert하여 재생할 수 있게 해야하는 새로운 요구사항이 들어왔다.<br>
어떻게 할 수 있을가? 우선 변환하는 서브 클래스들을 생성합니다.
```kotlin
// Converter 전용 서브 시스템
class VideoEncoder {
    fun encode(data: ByteArray, format: String): ByteArray {
        println("Encoding video to $format")
        return byteArrayOf()
    }
}

class AudioEncoder {
    fun encode(data: ByteArray, format: String): ByteArray {
        println("Encoding audio to $format")
        return byteArrayOf()
    }
}

class FormatConverter {
    fun transcode(input: ByteArray, outputFormat: String): ByteArray {
        println("Transcoding to $outputFormat")
        return byteArrayOf()
    }
}
```
그 후 영상을 Convert하는 Facade를 하나 생성합니다.
```kotlin
class MediaConverterFacade(
    private val videoDecoder: VideoDecoder = VideoDecoder(),
    private val audioDecoder: AudioDecoder = AudioDecoder(),
    private val videoEncode: VideoEncoder = VideoEncoder(),
    private val audioEncoder: AudioEncoder = AudioEncoder(),
    private val formatConverter: FormatConverter = FormatConverter()
) {
    fun convert(inputFiles: String, outputFiles: String, format: String) {
        val video = videoDecoder.decode(inputFiles)
        val audio = audioDecoder.decode(inputFiles)
        val convertedVideo = formatConverter.transcode(video, format)
        val encodeVideo = videoEncode.encode(convertedVideo, format)
        val encodedAudio = audioEncoder.encode(audio, format)
        println("Saved: $outputFiles")
    }
}
```
그 후 기존의 MediaPlayerFacade에 방금 위에서 생성한 ConvertFacade를 가지게 하면 됩니다.
```kotlin
class MediaPlayerWithConvertFacade(
    private val videoDecoder: VideoDecoder = VideoDecoder(),
    private val audioDecoder: AudioDecoder = AudioDecoder(),
    private val subtitleParser: SubtitleParser = SubtitleParser(),
    private val renderer: VideoRenderer = VideoRenderer(),
    // 다른 Facade를 서브시스템으로 포함
    private val converter: MediaConverterFacade = MediaConverterFacade()
) {
    fun play(filename: String) {
        val video = videoDecoder.decode(filename)
        val audio = audioDecoder.decode(filename)
        val subtitles = subtitleParser.parse(filename)
        renderer.render(video, audio, subtitles)
    }

    // 다른 Facade에 위임
    fun playWithConvert(inputFile: String, format: String) {
        val tempFile = "temp_converted.$format"
        converter.convert(inputFile, tempFile, format)
        play(tempFile)
    }
}
```

<br>
<br>

## 언제 사용하는가
- 서스시스템이 여러 클래스로 구성되어 있고, 클라이언트가 그 내부를 직접 다루기 복잡할 때
- 서브시스템과 클라이언트 사이의 결합도를 줄이고 싶을 때
- 계층화된 아키텍처에 각 레이어의 진입점을 정의할 때(UseCase, Repository)

<br>
Android를 개발하다보면 UseCase나 Repository에 비즈니스 로직을 캡슐화를 자주 하는데 이 또한 퍼사드로 볼 수 있다.

<br>
<br>

## 장점
- 복잡한 하위 시스템을 별도로 분리하여 클라이언트가 사용하기 쉬워진다
- 서브시스템과 클라이언트 사이의 결합도를 줄일 수 있다
- 지저분한 세부 구현사항들을 감출 수 있다

<br>
<br>

## 단점
- 퍼사드가 신이 될 수 있다(모든 클래스들을 다 가지고 모든 역할을 다 수행함)
- 퍼사드 클래스를 생성해야 하므로 추가적인 코드가 필요하다

<br>
<br>

## 예제
- [Facade - Basic](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/facade/EX1_BasicFacade.kt)
    - VideoPlay를 예시로한 Facade 패턴을 알아봅니다
- [재귀적 퍼사드](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/facade/Ex2_RecursionFacade.kt)
    - 예제1에에서 비디오 변환 기능을 확장하여 재귀적 퍼사드에 대해 알아봅니다  
