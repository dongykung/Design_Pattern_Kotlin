package org.example.factorymethod

enum class AnimalType {
    CAT, DOG
}

// Product
interface Animal {
    val name: String
    fun sound(): String
}

// Product Impl (ConcreteProductA)
class Cat : Animal {
    override val name: String = "Cat"
    override fun sound(): String {
        return "meow"
    }
}

// Product Impl (ConcreteProductB)
class Dog : Animal {
    override val name: String = "Dog"
    override fun sound(): String {
        return "wal"
    }
}

class AnimalFactory {
    fun createAnimal(type: AnimalType): Animal {
        return when (type) {
            AnimalType.DOG -> Dog()
            AnimalType.CAT -> Cat()
        }
    }
}

fun main() {
    val factory = AnimalFactory()

    val cat = factory.createAnimal(AnimalType.CAT)
    println(cat.sound())

    val dog = factory.createAnimal(AnimalType.DOG)
    println(dog.sound())
}