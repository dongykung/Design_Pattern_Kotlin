package org.example.builder

// 생성자에 기본 값 설정 가능
data class Mail(
    val title: String = "Empty Title",
    val message: String = "",
    var to: List<String> = emptyList()
)

fun main() {
    // named argument 지원
    val mail = Mail(message = "message", to = listOf("me"))
    println(mail.toString())
}