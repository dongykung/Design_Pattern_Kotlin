package org.example.adapter

// Client Interface
fun interface Volt220 {
    fun powerWith220V(volt: Int): String
}

// Service (호환 안 되는 기존 클래스)
class AmericanDevice {
    fun powerWith110V(volt: Int): String {
        require(volt == 110) { "이 기기는 110V만 지원합니다 현재V: $volt"}
        return "110V로 동작 중"
    }
}

// Adapter
class VoltAdapter(
    private val adaptee: AmericanDevice // 합성 (adaptee를 필드로 가짐)
) : Volt220 {
    override fun powerWith220V(volt: Int): String {
        require(volt == 220) { "220V 전원만 연결 가능합니다 현재V: $volt"}
        val convertedVolt = convertTo110V(volt)
        println("어댑터: ${volt}V -> ${convertedVolt}V 변환 완료")
        return adaptee.powerWith110V(convertedVolt) // 위임함
    }

    private fun convertTo110V(volt: Int): Int {
        return volt / 2
    }
}

fun main() {
    val americanDevice = AmericanDevice()
    val adapter: Volt220 = VoltAdapter(americanDevice)
    println(adapter.powerWith220V(220))
}
