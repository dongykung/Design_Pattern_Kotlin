package org.example.adapter

// Client Interface
interface ClassVolt220 {
    fun powerWith220V(volt: Int): String
}

// Service
open class ClassAmericanDevice {
    fun powerWith110V(volt: Int): String {
        require(volt == 110) { "이 기기는 110V만 지원합니다 현재V: $volt" }
        return "110V 로 동작 중"
    }
}

// 클래스 어댑터: 상속 + 구현
class ClassVoltAdapter : ClassAmericanDevice(), ClassVolt220 {
    override fun powerWith220V(volt: Int): String {
        require(volt == 220) { "220V 전원만 연결 가능합니다 현재V: $volt"}
        val convertedVolt = convertTo110V(volt)
        println("어댑터: ${volt}V -> ${convertedVolt}V 변환 완료")
        return powerWith110V(convertedVolt) // 자기 자신의 메서드 (상속)
    }

    private fun convertTo110V(volt: Int): Int = volt / 2
}

fun main() {
    val adapter: ClassVolt220 = ClassVoltAdapter()
    println(adapter.powerWith220V(220))
}