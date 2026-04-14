# State
객체의 내부 상태가 바뀔 때, 객체의 행동이 함께 바뀌도록 하는 패턴

보통 상태를 선언하다보면 when 문을 통해 상태에 따라 수행되는 로직을 다르게하는 코드를 많이 작성한다. <br>

하지만 상태패턴은 상태 자체를 객체화하여 상태가 행동을 할 수 있도록 위임한다. (상태 변경시 위임 대상이 바뀌고 행동도 달라진다)
<br>
<br>


## 구조


<br>
<br>

### State
상태를 추상화한 인터페이스
```kotlin
// State
interface AutoVideoPlayerState {
    fun play(player: AutoVideoPlayer)
    fun pause(player: AutoVideoPlayer)
    fun stop(player: AutoVideoPlayer)
}
```

<br>
<br>

### Context
State 객체를 합성(composition)하여 가지고 있으며 State 객체에 실행을 위임한다.
```kotlin
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
```

<br>
<br>

### Concrete State
각 상태를 클래스로 표현한 것 <br>

자신의 상태에 대한 역할을 수행한 후 상태가 변경되어야 한다면 Context에 상태 변경을 요청한다.
```kotlin
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
```

<br>
<br>

## 언제 사용하는가?
- 각 상태에 따라 다르게 행동하는 객체가 있을 때, 상태들의 수가 많을 때, 상태별로 코드가 자주 변경될 때
- 각 상태에 따라 when 문이 지저분해질 때
- 런타임에 객체의 상태를 변경해야 할 때

<br>
<br>

## 장점
- 각 상태를 하나의 클래스로 관리할 수 있다.
- 각 클래스는 하나의 상태만을 관리하다 (SRP 준수)
- 새로운 상태가 추가되면 기존 코드 수정없이 추가할 수 있다 (OCP 준수)
- 수많은 when 문 분기 코드를 삭제할 수 있다

<br>
<br>

## 단점
- 각 상태마다 클래스를 생성해야 하므로 작성해야 할 클래스가 많아질 수 있다
- 객체가 별로 없거나 자주 변경되지 않는다면 오버엔지니어링일 수 있다

<br>
<br>

## 다른 패턴과의 차이
- 상태패턴은 전략 패턴의 확장으로 간주할 수 있다.
  - 둘 모두 합성을 기반으로 하지만 전략 패턴은 객체들이 독립적이어서 서로를 인식하지 못하게 한다
  - 상태 패턴은 객체의 상태를 객체화하여 상태 클래스 내부에서 다른 상태로 교체한다.
- 상태 패턴은 객체 생성의 비용 절감을 위해 각 상태 객체가 상태를 가지지 않는는다면 Singleton 으로 유지한다