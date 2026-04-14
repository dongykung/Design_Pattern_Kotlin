package state

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// State
interface AutoVideoPlayerState {
    fun play(player: AutoVideoPlayer)
    fun pause(player: AutoVideoPlayer)
    fun stop(player: AutoVideoPlayer)
}

// 매번 객체 생성을 막기 위해 Singleton -> 객체가 상태를 들고있지 않을 때 가능함
object AutoPlayingState : AutoVideoPlayerState {
    override fun play(player: AutoVideoPlayer) {
        println("이미 Play 상태")
    }

    override fun pause(player: AutoVideoPlayer) {
        player.doPause()
        player.setVideoState(AutoPauseState)
    }

    override fun stop(player: AutoVideoPlayer) {
        player.doStop()
        player.setVideoState(AutoStopPlayingState)
    }
}

object AutoPauseState : AutoVideoPlayerState {
    override fun play(player: AutoVideoPlayer) {
        player.doPlay()
        player.setVideoState(AutoPlayingState)
    }

    override fun pause(player: AutoVideoPlayer) {
        println("이미 Pause인 상태")
    }

    override fun stop(player: AutoVideoPlayer) {
        player.doStop()
        player.setVideoState(AutoStopPlayingState)
    }
}

object AutoStopPlayingState : AutoVideoPlayerState {
    override fun play(player: AutoVideoPlayer) {
        player.doPlay()
        player.setVideoState(AutoPlayingState)
    }

    override fun pause(player: AutoVideoPlayer) {
        println("정지 상태에서는 일시정지할 수 없습니다")
    }

    override fun stop(player: AutoVideoPlayer) {
        println("이미 Stop 상태")
    }
}

// Context
class AutoVideoPlayer {

    private var state: AutoVideoPlayerState = AutoStopPlayingState

    fun play() = state.play(this)

    fun pause() = state.pause(this)

    fun stop() = state.stop(this)

    internal fun setVideoState(videoPlayer: AutoVideoPlayerState) {
        this.state = videoPlayer
    }

    internal fun doPlay() {
        println("영상 재생")
    }

    internal fun doPause() {
        println("영상 일시정지")
    }

    internal fun doStop() {
        println("영상 정지")
    }
}

fun main() {
    val player = AutoVideoPlayer()

    player.play()
    player.play()

    player.stop()
}