package org.example.facade

/**
 * Facade는 꼭 1개라는 법은 없다
 * 퍼사드가 다른 퍼사드를 서브 시스템처럼 감싸는 구조로도 가능하다
 * 예제 1에서 Media를 play 하는 MediaPlayerFacade를 만들었다.
 * 새록운 요구사항으로 새로운 format의 영상도 지원이 가능해야 해서 Convert 기능이 필요하다면??
 */

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

// Facade
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

fun main() {
    val player = MediaPlayerWithConvertFacade()

    player.playWithConvert("movie.mkv", "mp4")
}