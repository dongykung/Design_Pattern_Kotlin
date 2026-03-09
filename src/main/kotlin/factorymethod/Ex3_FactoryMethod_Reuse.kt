package org.example.factorymethod

// Product
interface Connection {
    val name: String
    var isIdle: Boolean
    fun connect() {
        isIdle = false
        println("$name 연결됨")
    }
    fun disconnect() {
        isIdle = true
        println("$name 연결 해제 -> 유휴 상태")
    }
}

// Product Impl (ConcreteProductA)
class MySqlConnection : Connection {
    override val name: String = "MySQL"
    override var isIdle: Boolean = true
}

// Product Impl (ConcreteProductB)
class PostgresConnection : Connection {
    override val name: String = "PostgresSQL"
    override var isIdle: Boolean = true
}

// Creator
abstract class ConnectionCreator {
    private val pool = mutableListOf<Connection>()

    protected abstract fun newConnection(): Connection

    // someOperation
    fun getConnection(): Connection {
        val free = pool.find { it.isIdle }
        if (free != null) {
            println("기존 연결 재사용")
            free.connect()
            return free
        }
        val conn = newConnection()
        pool.add(conn)
        println("새 연결 생성 pool size: ${pool.size}")
        conn.connect()
        return conn
    }
}

class MySqlConnectionCreator : ConnectionCreator() {
    override fun newConnection(): Connection {
        return MySqlConnection()
    }
}

class PostgresConnectionCreator : ConnectionCreator() {
    override fun newConnection(): Connection {
        return PostgresConnection()
    }
}

fun main() {
    val creator: ConnectionCreator = MySqlConnectionCreator()

    val conn1 = creator.getConnection()

    val conn2 = creator.getConnection()

    conn1.disconnect()

    val conn3 = creator.getConnection()

    println("conn1 === conn3? ${conn1 === conn3}")  // true
}