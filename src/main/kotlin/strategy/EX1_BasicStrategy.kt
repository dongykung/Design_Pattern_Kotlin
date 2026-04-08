package org.example.strategy

interface PaymentStrategy {
    fun pay(amount: Int)
}

class KakaoPayStrategy : PaymentStrategy {
    override fun pay(amount: Int) {
        println("카카오페이 $amount 결제")
    }
}

class ApplePayStrategy : PaymentStrategy {
    override fun pay(amount: Int) {
        println("애플페이 $amount 결제")
    }
}

class CreditCardStrategy : PaymentStrategy {
    override fun pay(amount: Int) {
        println("신용카드 $amount 결제")
    }
}

class MusinsaShoppingCart(
    private var payment: PaymentStrategy
) {
    fun setPaymentStrategy(paymentStrategy: PaymentStrategy) {
        // 검증 및 로깅 로직..
        this.payment = paymentStrategy
    }

    fun checkout(amount: Int) {
        payment.pay(amount)
    }
}

fun main() {
    val kakaoPayment = KakaoPayStrategy()
    val sp = MusinsaShoppingCart(kakaoPayment)
    sp.checkout(100000)

    sp.setPaymentStrategy(ApplePayStrategy())
    sp.checkout(500000)

    sp.setPaymentStrategy(CreditCardStrategy())
    sp.checkout(2000)
}
