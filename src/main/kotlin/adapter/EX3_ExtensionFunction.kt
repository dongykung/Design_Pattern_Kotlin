package org.example.adapter

fun interface KVolt220 {
    fun powerWith220V(volt: Int): String
}

class JapanDevice {
    fun powerWith110V(volt: Int): String {
        require(volt == 110) { "110V만 지원합니다 현재:${volt}V" }
        return "110V 정상 동작"
    }
}

// Adapter Class 생략 후 확장함수로 대체
fun JapanDevice.toVolt220V(): KVolt220 {
    val device = this
    return KVolt220 { volt ->
        require(volt == 220) { "220V 전원만 연결 가능합니다 현재V: $volt"}
        val convertedVolt = volt / 2
        println("어댑터: ${volt}V -> ${convertedVolt}V 변환 완료")
        device.powerWith110V(convertedVolt)
    }
}

fun main() {
    val adapter: KVolt220 = JapanDevice().toVolt220V()
    println(adapter.powerWith220V(220))
}