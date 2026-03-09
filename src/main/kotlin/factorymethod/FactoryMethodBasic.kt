package org.example.factorymethod

// Product
interface Pizza {
    val name: String
    fun prepare()
    fun box()
}

// Product Impl (ConcreteProductA)
class MeatPizza : Pizza {
    override val name: String = "MeatPizza"
    override fun prepare() = println("$name 준비중")
    override fun box() = println("$name 포장 완료")
}

// Product Impl (ConcreteProductB)
class SeafoodPizza : Pizza {
    override val name: String = "SeafoodPizza"
    override fun prepare() = println("$name 준비중")
    override fun box() = println("$name 포장 완료")
}

// Creator
interface PizzaStore {
    // createProduct
    fun createPizza(): Pizza

    // some operation
    fun order(): Pizza {
        val pizza = createPizza()
        pizza.prepare()
        pizza.box()
        return pizza
    }
}

// ConcreteCreatorA
class SeoulPizzaStore : PizzaStore {
    override fun createPizza(): Pizza {
        return MeatPizza()
    }
}

// ConcreteCreatorB
class NewYorkPizzaStore : PizzaStore {
    override fun createPizza(): Pizza {
        return SeafoodPizza()
    }
}

fun main() {
    val store: PizzaStore = SeoulPizzaStore()
    store.order()

    val store2: PizzaStore = NewYorkPizzaStore()
    store2.order()
}