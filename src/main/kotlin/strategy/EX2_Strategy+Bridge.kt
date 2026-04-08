package org.example.strategy

interface SendStrategy {
    fun send(message: String)
}

class KakaoTalkStrategy : SendStrategy {
    override fun send(message: String) {
        println("카카오톡 발송 $message")
    }
}

class EmailStrategy : SendStrategy {
    override fun send(message: String) {
        println("이메일 발송 $message")
    }
}

class SmsStrategy : SendStrategy {
    override fun send(message: String) {
        println("SMS 발송 $message")
    }
}

abstract class Notification(
    protected var sendStrategy: SendStrategy
) {
    fun setStrategy(sendStrategy: SendStrategy) {
        this.sendStrategy = sendStrategy
    }

    abstract fun notifyUser(message: String)
}

class UrgentNotification(strategy: SendStrategy) : Notification(strategy) {
    override fun notifyUser(message: String) {
        println("시스템 관리자에게 알림")
        sendStrategy.send(message)
    }
}

class MarketingNotification(strategy: SendStrategy) : Notification(strategy) {
    override fun notifyUser(message: String) {
        println("고객들에게 알림")
        sendStrategy.send(message)
    }
}

fun main() {
    val kakaoStrategy = KakaoTalkStrategy()

    val urgentNotification = UrgentNotification(kakaoStrategy)
    urgentNotification.notifyUser("관리자 공지사항! ")

    urgentNotification.setStrategy(SmsStrategy())
    urgentNotification.notifyUser("관리자 공지사항! ")

    urgentNotification.setStrategy(EmailStrategy())
    urgentNotification.notifyUser("관리자 공지사항! ")
}
