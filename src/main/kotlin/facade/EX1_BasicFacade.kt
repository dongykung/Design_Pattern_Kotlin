package org.example.facade

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

fun main() {
    // facade가 없다면?
    val videoDecoder = VideoDecoder()
    val audioDecoder = AudioDecoder()
    val subtitleParser = SubtitleParser()
    val videoRenderer = VideoRenderer()
    val fileName = "농구 하이라이트"
    videoRenderer.render(videoDecoder.decode(fileName), audioDecoder.decode(fileName), subtitleParser.parse(fileName))

    val mediaPlayer = MediaPlayerFacade()
    mediaPlayer.play("농구 영상")
}