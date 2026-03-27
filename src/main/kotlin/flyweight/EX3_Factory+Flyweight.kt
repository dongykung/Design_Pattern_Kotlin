package org.example.flyweight

import kotlin.random.Random

interface Item {
    val x: Double
    val y: Double
    val model: ItemModel
}

class ItemModel(
    val name: String,
    val bitmap: Bitmap,
    val color: Color,
)

class Banana(
    override val x: Double,
    override val y: Double,
    override val model: ItemModel
) : Item

class Booster(
    override val x: Double,
    override val y: Double,
    override val model: ItemModel
) : Item

class Wall(
    override val x: Double,
    override val y: Double,
    override val model: ItemModel
) : Item

object ItemModelFactory {
    private val cache = mutableMapOf<String, ItemModel>()


    fun getInstance(name: String, create: () -> ItemModel): ItemModel {
        return cache.getOrPut(name) { create() }
    }
}

interface ItemCreator {
    fun create(
        x: Double = Random.nextDouble(),
        y: Double = Random.nextDouble(),
    ): Item
}

class BananaCreator : ItemCreator {
    override fun create(
        x: Double,
        y: Double,
    ): Item {
        val model = ItemModelFactory.getInstance("Banana") {
            ItemModel("Banana", Bitmap.create(32, 32), Color("0xFFF9F9F9"))
        }
        return Banana(x, y, model)
    }
}

class BoosterCreator : ItemCreator {
    override fun create(
        x: Double,
        y: Double,
    ): Item {
        val model = ItemModelFactory.getInstance("Booster") {
            ItemModel("Booster", Bitmap.create(48, 38), Color("0xFFC9C9C9"))
        }
        return Booster(x, y, model)
    }
}

class WallCreator : ItemCreator {
    override fun create(
        x: Double,
        y: Double,
    ): Item {
        val model = ItemModelFactory.getInstance("Wall") {
            ItemModel("Wall", Bitmap.create(24, 24), Color("0xFFCDCDCD"))
        }
        return Wall(x, y, model)
    }
}

fun main() {

    val bananaCreator: ItemCreator = BananaCreator()
    val boosterCreator: ItemCreator = BoosterCreator()
    val wallCreator: ItemCreator = WallCreator()

    val banana1: Item = bananaCreator.create()
    val banana2: Item = bananaCreator.create(10.0, 20.0)

    val booster1: Item = boosterCreator.create()
    val booster2: Item = boosterCreator.create()

    val wall1: Item = wallCreator.create()
    val wall2: Item = wallCreator.create()
}